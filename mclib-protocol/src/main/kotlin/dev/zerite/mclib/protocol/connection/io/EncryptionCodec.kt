package dev.zerite.mclib.protocol.connection.io

import dev.zerite.mclib.protocol.ProtocolBuffer
import dev.zerite.mclib.protocol.connection.NettyConnection
import dev.zerite.mclib.protocol.util.Crypto
import dev.zerite.mclib.protocol.wrap
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.ByteToMessageCodec
import java.security.Key
import javax.crypto.Cipher

/**
 * Message codec for handling encryption between the client and server.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class EncryptionCodec(private val connection: NettyConnection, key: Key) :
    ByteToMessageCodec<ByteBuf>() {

    /**
     * Stores the ciphers used to encrypt and decrypt packets with the key.
     */
    private val encryptionCipher = Crypto[Cipher.ENCRYPT_MODE, key]
    private val decryptionCipher = Crypto[Cipher.DECRYPT_MODE, key]

    /**
     * Stores the buffers used for processing encryption in packets.
     */
    private var heapInput = ByteArray(0)
    private var heapOutput = ByteArray(0)

    /**
     * Encodes the packet buffer into a new output which is properly
     * encrypted using the provided cipher.
     *
     * @param  ctx        The channel's context.
     * @param  buf        The input packet buffer which needs to be encrypted.
     * @param  out        The output packet which has been encrypted.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    override fun encode(ctx: ChannelHandlerContext, buf: ByteBuf, out: ByteBuf) {
        // Wrap the buffer
        val buffer = buf.wrap(connection)

        // Get the size
        val size = buffer.readableBytes

        // Convert the bytes to a buffer
        val bytes = buffer.toBytes()

        // Get the output size
        val outSize = encryptionCipher.getOutputSize(size)

        // Check if the size is large enough
        if (heapOutput.size < outSize) {
            // Resize the output buffer
            heapOutput = ByteArray(outSize)
        }

        // Write the bytes
        out.writeBytes(heapOutput, 0, encryptionCipher.update(bytes, 0, size, heapOutput))
    }

    /**
     * Reads in the encrypted packet from the server and decrypts it into a
     * proper byte buffer, which is then put into the output to be passed through the pipeline.
     *
     * @param  ctx        The channel context.
     * @param  buf        The encrypted packet which we've received.
     * @param  out        The output array which we include the decoded values into.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    override fun decode(ctx: ChannelHandlerContext, buf: ByteBuf, out: MutableList<Any>) {
        // Wrap the buffer
        val buffer = buf.wrap(connection)

        // Find the readable bytes
        val size = buffer.readableBytes

        // Convert the buffer into bytes
        val bytes = buffer.toBytes()

        // Allocate a new buffer for the output
        val output = ctx.alloc().heapBuffer(decryptionCipher.getOutputSize(size))

        // Set the writer index
        output.writerIndex(decryptionCipher.update(bytes, 0, size, output.array(), output.arrayOffset()))

        // Add to the output
        out.add(output)
    }

    /**
     * Converts the protocol buffer into a byte array.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    private fun ProtocolBuffer.toBytes(): ByteArray {
        // Get the amount of bytes we can read
        val readable = readableBytes

        // Check if the heap is too large
        if (heapInput.size < readable) {
            // Create a new input
            heapInput = ByteArray(readable)
        }

        // Read the bytes
        readBytes(heapInput, 0, readable)

        // Return the heap input
        return heapInput
    }
}