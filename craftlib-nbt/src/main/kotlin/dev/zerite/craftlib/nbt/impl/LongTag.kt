package dev.zerite.craftlib.nbt.impl

import dev.zerite.craftlib.nbt.NBTTag
import dev.zerite.craftlib.nbt.TagIO
import java.io.DataInput
import java.io.DataOutput

/**
 * NBT tag representing a long.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class LongTag(var value: Long) : NBTTag {
    companion object : TagIO<LongTag> {
        override fun read(input: DataInput) = LongTag(input.readLong())
    }

    override val id = 4
    override fun write(output: DataOutput) = output.writeLong(value)
    override fun toString() = value.toString()
}
