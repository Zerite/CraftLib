package dev.zerite.craftlib.nbt.impl

import dev.zerite.craftlib.nbt.NBTIO
import dev.zerite.craftlib.nbt.NBTTag
import dev.zerite.craftlib.nbt.TagIO
import java.io.DataInput
import java.io.DataOutput

/**
 * NBT tag for storing a list of values.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("UNUSED")
data class ListTag<T : NBTTag>(val value: MutableList<T> = arrayListOf()) : NBTTag {
    companion object : TagIO<ListTag<NBTTag>> {
        override fun read(input: DataInput): ListTag<NBTTag> {
            val type = input.readByte().toInt()
            val size = input.readInt()
            val value = arrayListOf<NBTTag>()
            if (size != 0) repeat(size) { value.add(NBTIO.readTag(type, input)) }
            return ListTag(value)
        }
    }

    override val id = 9

    /**
     * Returns the size of this list.
     */
    @Suppress("UNUSED")
    val size get() = value.size

    /**
     * Creates a new list tag with the value appended to it and
     * returns it.
     *
     * @param  value       The value to add to the list.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun plus(value: T) = ListTag(ArrayList(this.value)).apply { this += value }

    /**
     * Adds an element to this tag.
     *
     * @param  value       The value to add to the list.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun plusAssign(value: T) {
        this.value.add(value)
    }

    /**
     * Returns whether or not the value is included in
     * the backing list of this tag.
     *
     * @param  value        The value to check for.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun contains(value: T) = this.value.contains(value)

    /**
     * Gets a value from the given index in this list, or returns
     * null if no value could be found.
     *
     * @param  i             The index to fetch from the list.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun get(i: Int) = value[i]

    /**
     * Returns a string representation of the list's contents.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    override fun toString() = StringBuilder().apply {
        append("$size entries\n{\n")
        value.forEach { append("  ${it.javaClass.simpleName}(None): ${it.toString().replace("\n", "\n  ")}\n") }
        append("}")
    }.toString()

    override fun write(output: DataOutput) {
        val type = value.takeIf { it.isNotEmpty() }?.get(0)?.id ?: 0
        output.writeByte(type)
        output.writeInt(value.size)

        value.forEach {
            if (it.id != type) error("Invalid list tag type")
            it.write(output)
        }
    }
}
