package dev.zerite.craftlib.nbt.impl

import dev.zerite.craftlib.nbt.NBTTag
import dev.zerite.craftlib.nbt.TagIO
import java.io.DataInput
import java.io.DataOutput

/**
 * NBT tag representing a string.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class StringTag(var value: String) : NBTTag {
    companion object : TagIO<StringTag> {
        override fun read(input: DataInput) = StringTag(input.readUTF())
    }

    override val id = 8
    override fun write(output: DataOutput) = output.writeUTF(value)
    override fun toString() = "'$value'"
}
