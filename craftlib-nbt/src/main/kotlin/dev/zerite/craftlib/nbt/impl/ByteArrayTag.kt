package dev.zerite.craftlib.nbt.impl

import dev.zerite.craftlib.nbt.NBTTag
import dev.zerite.craftlib.nbt.TagIO
import java.io.DataInput
import java.io.DataOutput

/**
 * Stores a byte array into a NBT tag.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ByteArrayTag(var value: ByteArray) : NBTTag {
    companion object : TagIO<ByteArrayTag> {
        @JvmStatic
        override fun read(input: DataInput) =
            ByteArrayTag(ByteArray(input.readInt()).apply { input.readFully(this) })
    }

    /**
     * Returns the size of the array.
     */
    @Suppress("UNUSED")
    val size
        get() = value.size

    /**
     * Returns the value in the array at the provided index.
     *
     * @param  i           The index to get the value at.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun get(i: Int) = value[i]

    /**
     * Sets a valid in the array at the given index.
     *
     * @param  i            The index to set the value at.
     * @param  value        The value to set.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun set(i: Int, value: Byte) {
        this.value[i] = value
    }

    override fun write(output: DataOutput) {
        output.writeInt(value.size)
        output.write(value)
    }

    override val id = 7
    override fun toString() = value.contentToString()
    override fun hashCode() = value.contentHashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ByteArrayTag

        if (!value.contentEquals(other.value)) return false

        return true
    }
}
