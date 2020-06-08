package dev.zerite.craftlib.nbt

import dev.zerite.craftlib.nbt.impl.*
import java.io.*
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

/**
 * Provides IO operations for reading and writing NBT tags.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("UNUSED")
object NBTIO {

    /**
     * Map of all the available NBT tag readers paired with
     * their tag ID.
     */
    private val readers = mapOf(
        1 to ByteTag,
        2 to ShortTag,
        3 to IntTag,
        4 to LongTag,
        5 to FloatTag,
        6 to DoubleTag,
        7 to ByteArrayTag,
        8 to StringTag,
        9 to ListTag,
        10 to CompoundTag,
        11 to IntArrayTag,
        12 to LongArrayTag
    )

    /**
     * Reads a NBT tag compound from the input stream.
     *
     * @param  input        The input stream to read from.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun read(input: InputStream) = read(DataInputStream(input) as DataInput)

    /**
     * Reads a NBT tag compound from the data input.
     *
     * @param  input        The input to read data from.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun read(input: DataInput): NamedTag<NBTTag> {
        assert(input.readByte().toInt() == 10) { "Root tag must be a compound tag" }
        return NamedTag(input.readUTF(), readTag(10, input))
    }

    /**
     * Reads a compressed input stream as a NBT tag compound.
     *
     * @param  input        The compressed data source.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun readCompressed(input: InputStream) = read(BufferedInputStream(GZIPInputStream(input)))

    /**
     * Writes a NBT tag into the buffer.
     *
     * @param  tag          The tag to write into the buffer.
     * @param  output       The output source for where we are writing to.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun write(tag: NBTTag, output: DataOutput) {
        output.writeByte(tag.id)
        tag.write(output)
    }

    /**
     * Writes a NBT tag into the output stream.
     *
     * @param  tag          The tag to write into the stream.
     * @param  output       The output stream for where we are writing to.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun write(tag: NBTTag, output: OutputStream) = write(tag, DataOutputStream(output) as DataOutput)

    /**
     * Writes a NBT tag into the output stream using GZIP compression.
     *
     * @param  tag          The tag to write into the stream.
     * @param  output       The output stream for where we are writing to.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun writeCompressed(tag: NBTTag, output: OutputStream) =
        GZIPOutputStream(output).let {
            write(tag, it)
            it.finish()
        }

    /**
     * Reads a NBT tag from the provided input.
     *
     * @param  type       The ID of the tag to read.
     * @param  input      The data input source to read the tag from.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @Suppress("UNUSED")
    inline fun <reified T : Any> readTag(type: Int, input: DataInput) =
        readTag(type, input, T::class.java)

    /**
     * Reads a NBT tag from the provided data input.
     *
     * @param  type       The tag's ID.
     * @param  input      The data source which we are reading from.
     * @param  clazz      The tag's type.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun <T : Any> readTag(type: Int, input: DataInput, clazz: Class<T>): T =
        clazz.cast(readers[type]?.read(input)) ?: error("Invalid tag found ($type)")

}
