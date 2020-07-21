package dev.zerite.craftlib.nbt

import dev.zerite.craftlib.nbt.impl.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.withContext
import java.io.*
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream

/**
 * Provides IO operations for reading and writing NBT tags.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("UNUSED", "BlockingMethodInNonBlockingContext")
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
    suspend fun read(input: InputStream) = read(DataInputStream(input) as DataInput)

    /**
     * Reads a NBT tag compound from the input stream and returns
     * a future.
     *
     * @param  input        The input stream to read from.
     * @author Koding
     * @since  0.1.2
     */
    @JvmStatic
    @Throws(IOException::class)
    fun readFuture(input: InputStream) = GlobalScope.future { read(input) }

    /**
     * Reads a NBT tag compound from the data input.
     *
     * @param  input        The input to read data from.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    suspend fun read(input: DataInput): NamedTag<CompoundTag> =
        withContext(Dispatchers.IO) {
            assert(input.readByte().toInt() == 10) { "Root tag must be a compound tag" }
            NamedTag(input.readUTF(), readTag<CompoundTag>(10, input))
        }

    /**
     * Reads a NBT tag compound from the data input and returns a
     * future.
     *
     * @param  input        The input to read data from.
     * @author Koding
     * @since  0.1.2
     */
    @JvmStatic
    @Throws(IOException::class)
    fun readFuture(input: DataInput) = GlobalScope.future { read(input) }

    /**
     * Reads a compressed input stream as a NBT tag compound.
     *
     * @param  input        The compressed data source.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    suspend fun readCompressed(input: InputStream) = read(BufferedInputStream(GZIPInputStream(input)))

    /**
     * Reads a compressed input stream as a NBT tag compound and
     * returns a future.
     *
     * @param  input        The compressed data source.
     * @author Koding
     * @since  0.1.2
     */
    @JvmStatic
    @Throws(IOException::class)
    fun readCompressedFuture(input: InputStream) = GlobalScope.future { readCompressed(input) }

    /**
     * Writes a NBT tag into the buffer.
     *
     * @param  tag          The tag to write into the buffer.
     * @param  output       The output source for where we are writing to.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    suspend fun write(tag: NBTTag, output: DataOutput) =
        withContext(Dispatchers.IO) {
            output.writeByte(tag.id)
            tag.write(output)
        }

    /**
     * Writes a NBT tag into the buffer and returns a future.
     *
     * @param  tag          The tag to write into the buffer.
     * @param  output       The output source for where we are writing to.
     *
     * @author Koding
     * @since  0.1.2
     */
    @JvmStatic
    @Throws(IOException::class)
    fun writeFuture(tag: NBTTag, output: DataOutput) = GlobalScope.future { write(tag, output) }

    /**
     * Writes a NBT tag into the output stream.
     *
     * @param  tag          The tag to write into the stream.
     * @param  output       The output stream for where we are writing to.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    suspend fun write(tag: NBTTag, output: OutputStream) =
        write(tag, DataOutputStream(output) as DataOutput)

    /**
     * Writes a NBT tag into the output stream and returns a future.
     *
     * @param  tag          The tag to write into the stream.
     * @param  output       The output stream for where we are writing to.
     *
     * @author Koding
     * @since  0.1.2
     */
    @JvmStatic
    @Throws(IOException::class)
    fun writeFuture(tag: NBTTag, output: OutputStream) = GlobalScope.future { write(tag, output) }

    /**
     * Writes a NBT tag into the output stream using GZIP compression.
     *
     * @param  tag          The tag to write into the stream.
     * @param  output       The output stream for where we are writing to.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    suspend fun writeCompressed(tag: NBTTag, output: OutputStream) =
        output.use {
            withContext(Dispatchers.IO) {
                GZIPOutputStream(it).use {
                    write(tag, it)
                }
            }
        }

    /**
     * Writes a NBT tag into the output stream using GZIP compression and
     * returns a future.
     *
     * @param  tag          The tag to write into the stream.
     * @param  output       The output stream for where we are writing to.
     *
     * @author Koding
     * @since  0.1.2
     */
    @JvmStatic
    @Throws(IOException::class)
    fun writeCompressedFuture(tag: NBTTag, output: OutputStream) =
        GlobalScope.future { writeCompressed(tag, output) }


    /**
     * Reads a NBT tag from the provided input.
     *
     * @param  type       The ID of the tag to read.
     * @param  input      The data input source to read the tag from.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @JvmStatic
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
    @JvmStatic
    fun <T : Any> readTag(type: Int, input: DataInput, clazz: Class<T>): T =
        clazz.cast(readers[type]?.read(input)) ?: error("Invalid tag found ($type)")

}
