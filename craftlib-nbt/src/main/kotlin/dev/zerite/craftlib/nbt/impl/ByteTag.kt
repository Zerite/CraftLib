package dev.zerite.craftlib.nbt.impl

import dev.zerite.craftlib.nbt.NBTTag
import dev.zerite.craftlib.nbt.TagIO
import java.io.DataInput
import java.io.DataOutput

/**
 * Represents a single byte in a NBT tag.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ByteTag(var value: Byte) : NBTTag {
    companion object : TagIO<ByteTag> {
        @JvmStatic
        override fun read(input: DataInput) = ByteTag(input.readByte())
    }

    override val id = 1
    override fun write(output: DataOutput) = output.writeByte(value.toInt())
    override fun toString() = value.toString()
}
