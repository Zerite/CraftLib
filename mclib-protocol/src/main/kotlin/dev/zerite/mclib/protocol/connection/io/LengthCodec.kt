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
        // Wrap the buffer
        val buffer = out.wrap(connection)

        // Write the length prefix
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
        // Wrap the buffer
        val buffer = buf.wrap(connection)

        // Mark the reader index so we can reset back to it later
        buf.markReaderIndex()

        // Create a buffer to store the length in
        val lengthBuffer = ByteArray(5)

        // Loop through every byte we could read in the buffer
        repeat(lengthBuffer.size) { i ->
            // Check if we can't read anymore
            if (!buffer.isReadable) {
                // Reset back to the original reader index and ignore it
                buffer.resetReaderIndex()
                return
            }

            // Read the next byte
            lengthBuffer[i] = buf.readByte()

            // Check if we've reached the end of this VarInt
            if (lengthBuffer[i] >= 0) {
                // Create a buffer for the length bytes
                val wrappedLength = lengthBuffer.wrap(connection)

                // Read the buffer as a VarInt
                val length = wrappedLength.readVarInt().takeIf { it > 0 }
                    ?: throw CorruptedFrameException("Empty packet")

                // Check if the length is larger than what we can read
                if (length >= buffer.readableBytes) {
                    // Reset the index
                    buffer.resetReaderIndex()
                } else {
                    // Read the output
                    out.add(buffer.slice(buffer.readerIndex, length).retain())

                    // Skip over the length bytes
                    buffer.skipBytes(length)
                }

                // Break out of the loop
                return
            }
        }
    }
}