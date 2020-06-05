package dev.zerite.mclib.protocol

import dev.zerite.mclib.chat.component.BaseChatComponent
import dev.zerite.mclib.chat.component.chatComponent
import dev.zerite.mclib.chat.component.json
import dev.zerite.mclib.protocol.connection.NettyConnection
import dev.zerite.mclib.protocol.util.ext.toUuid
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import java.util.*

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
        var amount = 0
        var result = 0
        var read: Int

        do {
            read = readByte().toInt()

            // Discard most significant bit as it's not part of the value
            val value = read and 0b01111111

            // Create space for this part of the value and add it
            result = result or (value shl (7 * amount++))

            if (amount > 5) error("VarInt has too many bytes (only 5 allowed)")
        } while ((read and 0b10000000) != 0) // If the most significant bit is set we have to read another byte

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
        var current = value

        do {
            // Get the value we're supposed to write
            var temp = current and 0b01111111

            // Discard the part of the value we're writing now
            current = current ushr 7

            // Set the most significant bit if we need to write any more bytes
            if (current != 0) {
                temp = temp or 0b10000000
            }

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
    fun readByteArray(length: ProtocolBuffer.() -> Int = { readVarInt() }) =
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
        length(bytes.size)
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

    /**
     * Marks the index of the writer so we can reset
     * back to it later.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun markWriterIndex(): ByteBuf = buf.markWriterIndex()

    /**
     * Resets the writer back to the last marked index.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun resetWriterIndex(): ByteBuf = buf.resetWriterIndex()

    /**
     * Converts the buffer into an array and returns null
     * if this buffer doesn't support this conversion.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun array() = if (buf.hasArray()) buf.array() else null

    /**
     * Releases the byte buffer from memory.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun release() = buf.release()

    /**
     * Reads a string from the buffer and parses the JSON into a
     * chat component object.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun readChat() = readString().chatComponent

    /**
     * Writes a chat component to the buffer by converting it
     * into its JSON representation.
     *
     * @param  value        The component to write.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun writeChat(value: BaseChatComponent) = writeString(value.json)

    /**
     * Reads a long from the buffer and returns it.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @Suppress("UNUSED")
    fun readLong() = buf.readLong()

    /**
     * Writes a long into the buffer from the provided value.
     *
     * @param  value        The value we're writing.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @Suppress("UNUSED")
    fun writeLong(value: Long): ByteBuf = buf.writeLong(value)

    /**
     * Reads a UUID from the buffer in a specific mode specified by
     * the provided mode.
     *
     * @param  mode         The mode to use to read this UUID.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun readUUID(mode: UUIDMode = UUIDMode.STRING) = when (mode) {
        UUIDMode.DASHES -> readString().toUuid(dashes = true)
        UUIDMode.STRING -> readString().toUuid()
        UUIDMode.RAW -> UUID(readLong(), readLong())
    }

    /**
     * Writes a UUID to the buffer with the given mode.
     *
     * @param  value       The UUID to write.
     * @param  mode        The mode of writing to use for this UUID.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun writeUUID(value: UUID, mode: UUIDMode = UUIDMode.STRING): Any =
        when (mode) {
            UUIDMode.DASHES -> writeString(value.toString().replace("-", ""))
            UUIDMode.STRING -> writeString(value.toString())
            UUIDMode.RAW -> {
                writeLong(value.mostSignificantBits)
                writeLong(value.leastSignificantBits)
            }
        }

    /**
     * Reads a short from the buffer and returns it.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun readShort() = buf.readShort()

    /**
     * Reads an int from the buffer.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun readInt() = buf.readInt()

    /**
     * Writes an int to the buffer.
     *
     * @param  value      The int value to write.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun writeInt(value: Int): ByteBuf = buf.writeInt(value)

    /**
     * Reads an unsigned byte from the buffer and returns it.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun readUnsignedByte() = buf.readUnsignedByte()

    /**
     * Reads a float from the buffer and returns it.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun readFloat() = buf.readFloat()

    /**
     * Writes the float value to the buffer.
     *
     * @param  value       The float value to write.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun writeFloat(value: Float): ByteBuf = buf.writeFloat(value)

    /**
     * Reads an array from the buffer using the provided process method.
     *
     * @param  length      Reads the length from the buffer.
     * @param  process     Reads a value from the array for each item.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    inline fun <reified T> readArray(
        length: ProtocolBuffer.() -> Int = { readVarInt() },
        process: ProtocolBuffer.() -> T
    ) = (0 until length()).map { process() }.toTypedArray()

    /**
     * Writes an array to the buffer using the provided process method.
     *
     * @param  items       The items to write into the array.
     * @param  length      Writes the length to the buffer.
     * @param  process     Writes a value from the array into the buffer.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    inline fun <reified T> writeArray(
        items: Array<T>,
        length: ProtocolBuffer.(Int) -> Unit = { writeVarInt(it) },
        process: ProtocolBuffer.(T) -> Unit
    ) {
        this.length(items.size)
        items.forEach { this.process(it) }
    }

    /**
     * Reads a boolean value from the buffer and returns it.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun readBoolean() = buf.readBoolean()

    /**
     * Writes a boolean value into the buffer.
     *
     * @param  value      The value to write.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun writeBoolean(value: Boolean): ByteBuf = buf.writeBoolean(value)

    /**
     * Reads a double from the buffer and returns it.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun readDouble() = buf.readDouble()

    /**
     * Writes a double into the buffer.
     *
     * @param  value    The double value to write.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun writeDouble(value: Double): ByteBuf = buf.writeDouble(value)

    /**
     * Set of modes which the UUIDs should be written using.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    enum class UUIDMode {
        DASHES,
        STRING,
        RAW
    }
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