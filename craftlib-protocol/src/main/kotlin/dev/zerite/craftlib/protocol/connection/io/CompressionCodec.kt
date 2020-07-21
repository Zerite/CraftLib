package dev.zerite.craftlib.protocol.connection.io

import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.wrap
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageCodec
import io.netty.handler.codec.DecoderException
import java.util.zip.Deflater
import java.util.zip.Inflater

/**
 * Handles compression through the protocol, including reading and writing
 * packets which use this standard.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
class CompressionCodec(private val connection: NettyConnection, var threshold: Int) : ByteToMessageCodec<ByteBuf>() {

    companion object {
        /**
         * The maximum size of compressed packets that the protocol can support.
         */
        const val MAX_COMPRESSED_SIZE = 2097152
    }

    /**
     * The deflater we use to encode packets using compression.
     */
    private val deflater = Deflater()

    /**
     * The inflater we use to decode packets using compression.
     */
    private val inflater = Inflater()

    /**
     * The buffer we use to store deflated bytes.
     */
    private val buffer = ByteArray(8192)

    /**
     * Encodes the packet buffer into the provided output with compression applied.
     *
     * @param  ctx        The channel's context.
     * @param  input      The input packet buffer which needs to be compressed.
     * @param  out        The output packet which has been compressed.
     *
     * @author Koding
     * @since  0.1.1-SNAPSHOT
     */
    override fun encode(ctx: ChannelHandlerContext, input: ByteBuf, out: ByteBuf) {
        val readable = input.readableBytes()
        val buf = out.wrap(connection)

        if (readable < threshold) {
            buf.writeVarInt(0)
            buf.writeBytes(input)
        } else {
            val bytes = ByteArray(readable)
            input.readBytes(bytes)
            buf.writeVarInt(bytes.size)

            deflater.setInput(bytes, 0, readable)
            deflater.finish()

            while (!deflater.finished()) {
                val index = deflater.deflate(buffer)
                buf.writeBytes(buffer, 0, index)
            }

            deflater.reset()
        }
    }

    /**
     * Reads in the compressed packet from the server and decodes it into
     * the non compressed format.
     *
     * @param  ctx        The channel context.
     * @param  input      The compressed packet which we've received.
     * @param  out        The output array which we include the uncompressed values into.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    override fun decode(ctx: ChannelHandlerContext, input: ByteBuf, out: MutableList<Any>) {
        val buf = input.wrap(connection)
        if (buf.readableBytes != 0) {
            val size = buf.readVarInt()
            if (size == 0) {
                out.add(buf.readBytes(buf.readableBytes))
            } else {
                if (size < threshold)
                    throw DecoderException("Badly compressed packet: Size ($size) is below server threshold ($threshold)")
                if (size > MAX_COMPRESSED_SIZE)
                    throw DecoderException("Badly compressed packet: Size ($size) is above protocol maximum ($MAX_COMPRESSED_SIZE)")

                val bytes = ByteArray(buf.readableBytes)
                buf.readBytes(bytes)
                inflater.setInput(bytes)

                val inflated = ByteArray(size)
                inflater.inflate(inflated)

                out.add(Unpooled.wrappedBuffer(inflated))
                inflater.reset()
            }
        }
    }
}
