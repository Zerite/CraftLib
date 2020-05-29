package dev.zerite.mclib.protocol

import dev.zerite.mclib.protocol.connection.NettyConnection
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

/**
 * Wraps a byte buffer and adds additional methods to easily read
 * packet data.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("UNUSED")
class ProtocolBuffer(@Suppress("UNUSED") val buf: ByteBuf, val connection: NettyConnection) {

    /**
     * True if this buffer can be read from.
     */
    val isReadable: Boolean
        get() = buf.isReadable

    /**
     * Returns the amount of bytes remaining to be read.
     */
    val readableBytes: Int
        get() = buf.readableBytes()

    /**
     * Returns the readers saved index.
     */
    val readerIndex: Int
        get() = buf.readerIndex()

    /**
     * Read a VarInt from the buffer.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun readVarInt(): Int {
        // Store the base storage values
        var amount = 0
        var result = 0
        var read: Int

        // Loop whilst we should read
        do {
            // Read the next byte
            read = readByte().toInt()

            // Read the value
            val value = read and 0b01111111

            // Modify the result
            result = result or (value shl (7 * amount++))

            // Check if the amount is too large
            if (amount > 5) error("VarInt has too many bytes (only 5 allowed)")
        } while ((read and 0b10000000) != 0)

        // Return the result
        return result
    }

    /**
     * Writes a VarInt into the buffer.
     *
     * @param  value      The value which should be written.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun writeVarInt(value: Int) {
        // Store the current value
        var current = value

        // Loop whilst we still have a remaining value to write
        do {
            // Get the current masked value
            var temp = current and 0b01111111

            // Set the new current value
            current = current ushr 7

            // Check if we need to write any more values
            if (current != 0) {
                // Modify the temp variable
                temp = temp or 0b10000000
            }

            // Write the masked value as a byte
            writeByte(temp)
        } while (current != 0)
    }

    /**
     * Reads a byte array from the buffer.
     *
     * @param  length    Lambda for providing the length for the byte array.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @Suppress("UNUSED")
    fun readByteArray(length: () -> Int = { readVarInt() }) =
        ByteArray(length()).apply { readBytes(this) }

    /**
     * Writes a byte array to the buffer.
     *
     * @param  bytes    The raw bytes to write to the buffer.
     * @param  length   Lambda for writing the length of the array to the buffer.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @Suppress("UNUSED")
    inline fun writeByteArray(bytes: ByteArray, length: ProtocolBuffer.(Int) -> Unit = { writeVarInt(it) }) {
        // Write the length
        length(bytes.size)

        // Write the bytes
        writeBytes(bytes)
    }

    /**
     * Writes a string into the buffer.
     *
     * @param  value      The string to write.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun writeString(value: String) = writeByteArray(value.toByteArray())

    /**
     * Reads a string from the buffer.
     *
     * @return The string which was read from the buffer.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun readString() = readByteArray().toString(Charsets.UTF_8)

    /**
     * Reads a single byte from the buffer.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @Suppress("UNUSED")
    fun readByte() = buf.readByte()

    /**
     * Write a single byte to the buffer.
     *
     * @param  value    The value to write.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @Suppress("UNUSED")
    fun writeByte(value: Int): ByteBuf = buf.writeByte(value)

    /**
     * Read bytes from the buffer with the given length.
     *
     * @param  length    The length of the byte array to read.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @Suppress("UNUSED")
    fun readBytes(length: Int): ByteBuf = buf.readBytes(length)

    /**
     * Read bytes from the buffer using the length of the provided
     * byte array.
     *
     * @param  bytes     The byte array to read into.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @Suppress("UNUSED")
    fun readBytes(bytes: ByteArray): ByteBuf = buf.readBytes(bytes)

    /**
     * Write a byte array into the buffer.
     *
     * @param  bytes     The raw bytes to write.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @Suppress("UNUSED")
    fun writeBytes(bytes: ByteArray): ByteBuf = buf.writeBytes(bytes)

    /**
     * Reads an unsigned short from the buffer and returns it.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun readUnsignedShort() = buf.readUnsignedShort()

    /**
     * Writes a short to the buffer.
     *
     * @param  value     The value to write.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun writeShort(value: Int): ByteBuf = buf.writeShort(value)

    /**
     * Concatenates the provided byte buffer onto the end of
     * this buffer.
     *
     * @param  buffer       The buffer to write.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun writeBytes(buffer: ByteBuf): ByteBuf = buf.writeBytes(buffer)

    /**
     * Marks the reader index so that it can be reset back to later.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun markReaderIndex(): ByteBuf = buf.markReaderIndex()

    /**
     * Resets the reader index back to the previously saved value.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun resetReaderIndex(): ByteBuf = buf.resetReaderIndex()

    /**
     * Returns a select portion of the buffer from the given start
     * position for a length.
     *
     * @param  start      The new buffer's start position.
     * @param  length     The length to read for this new buffer.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun slice(start: Int, length: Int): ByteBuf = buf.slice(start, length)

    /**
     * Makes the reader skip over a provided length of bytes.
     *
     * @param  length     The length of bytes to skip.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun skipBytes(length: Int): ByteBuf = buf.skipBytes(length)

    /**
     * Reads a portion of this buffer into the provided bytes array.
     *
     * @param  bytes     The byte array to read into.
     * @param  start     The starting index to begin reading from.
     * @param  length    The length of bytes we are reading.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun readBytes(bytes: ByteArray, start: Int, length: Int): ByteBuf = buf.readBytes(bytes, start, length)
}

/**
 * Wrap a byte buffer into a wrapped version whilst including
 * the connection reference.
 *
 * @param  connection     The connection reference.
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
fun ByteBuf.wrap(connection: NettyConnection) = ProtocolBuffer(this, connection)

/**
 * Wrap a byte array into a wrapped version whilst including
 * the connection reference.
 *
 * @param  connection     The connection reference.
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
fun ByteArray.wrap(connection: NettyConnection) =
    ProtocolBuffer(Unpooled.wrappedBuffer(this), connection)