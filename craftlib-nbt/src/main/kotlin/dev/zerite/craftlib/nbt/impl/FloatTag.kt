package dev.zerite.craftlib.nbt.impl

import dev.zerite.craftlib.nbt.NBTTag
import dev.zerite.craftlib.nbt.TagIO
import java.io.DataInput
import java.io.DataOutput

/**
 * Represents a float value in a NBT tag.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class FloatTag(var value: Float) : NBTTag {
    companion object : TagIO<FloatTag> {
        @JvmStatic
        override fun read(input: DataInput) = FloatTag(input.readFloat())
    }

    override val id = 5
    override fun write(output: DataOutput) = output.writeFloat(value)
    override fun toString() = value.toString()
}
