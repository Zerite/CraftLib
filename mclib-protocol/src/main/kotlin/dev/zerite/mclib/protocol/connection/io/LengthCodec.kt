package dev.zerite.mclib.protocol.connection.io

import dev.zerite.mclib.protocol.connection.NettyConnection
import dev.zerite.mclib.protocol.wrap
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageCodec
import io.netty.handler.codec.CorruptedFrameException

/**
 * Prefixes all packets with their appropriate length.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class LengthCodec(private val connection: NettyConnection) : ByteToMessageCodec<ByteBuf>() {

    /**
     * Encodes the packet by prefixing the raw bytes with the length
     * and then writing the remaining packet data.
     *
     * @param  ctx       The channel context.
     * @param  msg       The message we are writing.
     * @param  out       The out buffer which we are writing to.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    override fun encode(ctx: ChannelHandlerContext, msg: ByteBuf, out: ByteBuf) {
        val buffer = out.wrap(connection)

        buffer.writeVarInt(msg.readableBytes())
        buffer.writeBytes(msg)
    }

    /**
     * Decode a packet by reading a set of bytes from the buffer, building
     * a VarInt which is then used to read the remaining bytes as a packet.
     *
     * @param  ctx     The channel context.
     * @param  buf     The buffer we're reading this packet from.
     * @param  out     The output for what we read.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
        val buffer = buf.wrap(connection)
        buf.markReaderIndex()
        val lengthBuffer = ByteArray(5)

        repeat(lengthBuffer.size) { i ->
            if (!buffer.isReadable) {
                buffer.resetReaderIndex()
                return
            }

            lengthBuffer[i] = buf.readByte()

            // Check if we have all bytes in the length varint
            if (lengthBuffer[i] >= 0) {
                val wrappedLength = lengthBuffer.wrap(connection)

                val length = wrappedLength.readVarInt().takeIf { it > 0 }
                    ?: throw CorruptedFrameException("Empty packet")

                // Check if we have all packet bytes
                if (length >= buffer.readableBytes) {
                    buffer.resetReaderIndex()
                } else {
                    // Create a new buffer containing only this packet and not any others which may come after it
                    out.add(buffer.slice(buffer.readerIndex, length).retain())
                    // Remove the packet from the original buffer
                    buffer.skipBytes(length)
                }

                return
            }
        }
    }
}