package dev.zerite.craftlib.nbt.impl

import dev.zerite.craftlib.nbt.NBTIO
import dev.zerite.craftlib.nbt.NBTTag
import dev.zerite.craftlib.nbt.TagIO
import java.io.DataInput
import java.io.DataOutput

/**
 * Stores a map of tag names to NBT tags.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("UNUSED")
data class CompoundTag(val value: MutableMap<String, NBTTag> = mutableMapOf()) : NBTTag {
    companion object : TagIO<CompoundTag> {
        override fun read(input: DataInput): CompoundTag {
            var type = input.readByte().toInt()
            val tag = CompoundTag()
            while (type != 0) {
                tag[input.readUTF()] = NBTIO.readTag(type, input)
                type = input.readByte().toInt()
            }
            return tag
        }
    }

    override val id = 10

    /**
     * Returns the size of this tag.
     */
    @Suppress("UNUSED")
    val size get() = value.size

    /**
     * Appends the values of one compound tag to this one and
     * returns a new tag with the result.
     *
     * @param  other         The other tag which we are adding to.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun plus(other: CompoundTag) = CompoundTag(HashMap(this.value)).apply { this += other }

    /**
     * Adds all the values of the other compound tag into this one.
     *
     * @param  other         The other tag to pull values from.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun plusAssign(other: CompoundTag) {
        value.putAll(other.value)
    }

    /**
     * Returns true if this compound tag contains the given key.
     *
     * @param  key           The key to look for.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun contains(key: String) = get<NBTTag>(key) != null

    /**
     * Returns true if this compound tag contains the given value.
     *
     * @param  value          The value to look for.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun contains(value: NBTTag) = this.value.containsValue(value)

    /**
     * Gets a tag from this compound with the given key.
     *
     * @param  key             The key to lookup.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    inline operator fun <reified T : NBTTag> get(key: String): T? = value[key]?.let { T::class.java.cast(it) }

    /**
     * Gets a tag from this compound or returns a default value.
     *
     * @param  key             The key to lookup.
     * @param  default         The default value to fallback to.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    inline operator fun <reified  T : NBTTag> get(key: String, default: T): T = get(key) ?: default

    /**
     * Adds a value into the tag.
     *
     * @param  key             The key to associate with this value.
     * @param  value           The value to set.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun set(key: String, value: NBTTag) {
        this.value[key] = value
    }

    /**
     * Builds a string which represents the compound's values.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    override fun toString() = StringBuilder().apply {
        append("$size entries\n{\n")
        value.entries.forEach { (name, tag) ->
            append("  ${tag.javaClass.simpleName}('$name'): ${tag.toString().replace("\n", "\n  ")}\n")
        }
        append("}")
    }.toString()

    override fun write(output: DataOutput) {
        value.forEach { (name, tag) ->
            output.write(tag.id)
            output.writeUTF(name)
            tag.write(output)
        }
        output.write(0)
    }
}
