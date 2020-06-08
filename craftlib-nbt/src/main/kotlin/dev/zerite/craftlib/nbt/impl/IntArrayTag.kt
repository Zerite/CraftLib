package dev.zerite.craftlib.nbt.impl

import dev.zerite.craftlib.nbt.NBTTag
import dev.zerite.craftlib.nbt.TagIO
import java.io.DataInput
import java.io.DataOutput

/**
 * Contains a list of integer values encoded into an NBT tag.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("UNUSED")
data class IntArrayTag(var value: IntArray) : NBTTag {
    companion object : TagIO<IntArrayTag> {
        override fun read(input: DataInput) = IntArrayTag(IntArray(input.readInt()) { input.readInt() })
    }

    override val id = 11

    /**
     * Returns the size of this array.
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
    operator fun set(i: Int, value: Int) {
        this.value[i] = value
    }

    override fun write(output: DataOutput) {
        output.writeInt(size)
        value.forEach { output.writeInt(it) }
    }

    /**
     * Converts the array into an string representation.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    override fun toString() = "[$size integers]"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IntArrayTag

        if (!value.contentEquals(other.value)) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode() = value.contentHashCode()
}
