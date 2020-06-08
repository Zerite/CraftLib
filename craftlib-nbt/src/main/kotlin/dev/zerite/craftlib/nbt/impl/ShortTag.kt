package dev.zerite.craftlib.nbt.impl

import dev.zerite.craftlib.nbt.NBTTag
import dev.zerite.craftlib.nbt.TagIO
import java.io.DataInput
import java.io.DataOutput

/**
 * Representation of a short value in a NBT tag.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ShortTag(var value: Short) : NBTTag {
    companion object : TagIO<ShortTag> {
        override fun read(input: DataInput) = ShortTag(input.readShort())
    }

    override val id = 2
    override fun write(output: DataOutput) = output.writeShort(value.toInt())
    override fun toString() = value.toString()
}
