package dev.zerite.craftlib.nbt.impl

import dev.zerite.craftlib.nbt.NBTTag
import java.io.DataOutput

/**
 * Contains a single tag which has a name and a sub-tag
 * to write.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class NamedTag<T : NBTTag>(val name: String, val tag: T) : NBTTag {
    override val id = tag.id
    override fun toString() = "${tag.javaClass.simpleName}('$name'): $tag"

    override fun write(output: DataOutput) {
        output.writeUTF(name)
        tag.write(output)
    }
}
