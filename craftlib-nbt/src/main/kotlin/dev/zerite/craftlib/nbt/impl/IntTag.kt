package dev.zerite.craftlib.nbt.impl

import dev.zerite.craftlib.nbt.NBTTag
import dev.zerite.craftlib.nbt.TagIO
import java.io.DataInput
import java.io.DataOutput

/**
 * NBT tag for storing an integer value.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class IntTag(var value: Int) : NBTTag {
    companion object : TagIO<IntTag> {
        override fun read(input: DataInput) = IntTag(input.readInt())
    }

    override val id = 3
    override fun write(output: DataOutput) = output.writeInt(value)
    override fun toString() = value.toString()
}
