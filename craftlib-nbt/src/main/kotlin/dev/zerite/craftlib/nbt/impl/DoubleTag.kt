package dev.zerite.craftlib.nbt.impl

import dev.zerite.craftlib.nbt.NBTTag
import dev.zerite.craftlib.nbt.TagIO
import java.io.DataInput
import java.io.DataOutput

/**
 * Tag which represents a double value.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class DoubleTag(var value: Double) : NBTTag {
    companion object : TagIO<DoubleTag> {
        @JvmStatic
        override fun read(input: DataInput) = DoubleTag(input.readDouble())
    }

    override val id = 6
    override fun write(output: DataOutput) = output.writeDouble(value)
    override fun toString() = value.toString()
}
