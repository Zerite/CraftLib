@file:JvmName("NBTUtil")

package dev.zerite.craftlib.nbt

import dev.zerite.craftlib.nbt.impl.*
import java.io.InputStream
import java.io.OutputStream

/**
 * Builds a compound tag.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("UNUSED")
class CompoundTagBuilder {
    /**
     * The tag which is associated with this builder.
     */
    private val tag = CompoundTag()

    /**
     * Adds a new tag to the compound with the given name.
     *
     * @param  value          The tag's value.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    infix fun String.to(value: Any) {
        tag[this] = value.asTag
    }

    /**
     * Builds the tag and returns it.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun build() = tag
}

@Suppress("UNUSED")
class ListTagBuilder<T : NBTTag>(private val clazz: Class<T>) {
    /**
     * The tag which is associated with this builder.
     */
    private val tag = ListTag<T>()

    /**
     * Adds a value to the list.
     *
     * @param  value            The value to add to the list.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @Suppress("UNUSED")
    fun add(value: Any) {
        tag += clazz.cast(value.asTag)
    }

    /**
     * Adds a value to this list.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun Any.unaryPlus() = add(this)

    /**
     * Returns the list tag.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun build() = tag
}

/**
 * Creates a new NBT tag compound with the builder.
 *
 * @param  build       The builder function for this tag.
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("UNUSED")
fun compound(build: CompoundTagBuilder.() -> Unit) = CompoundTagBuilder().apply(build).build()

/**
 * Creates a list using the builder.
 *
 * @param  build        The builder function for this tag.
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("UNUSED")
inline fun <reified T : NBTTag> list(build: ListTagBuilder<T>.() -> Unit) =
    ListTagBuilder(T::class.java).apply(build).build()

/**
 * Creates a named NBT tag with the given name.
 *
 * @param  name        The name of the tag.
 * @param  tag         The tag's value.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("UNUSED")
fun <T : NBTTag> named(name: String, tag: T) = NamedTag(name, tag)

/**
 * Creates a NBT tag with the given object.
 *
 * @param  obj         The object to create a tag with.
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
inline fun <reified T : NBTTag> tag(obj: Any) = tag(obj, T::class.java)

/**
 * Creates a new NBT tag with the object.
 *
 * @param  obj          The object to create a tag with.
 * @param  clazz        The tag's type.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
fun <T : NBTTag> tag(obj: Any, clazz: Class<T>): T = clazz.cast(obj.asTag)

/**
 * Converts an object into a NBT tag.
 */
@get:Throws(IllegalStateException::class)
@get:JvmName("asTag")
@Suppress("USELESS_CAST")
val Any?.asTag: NBTTag
    get() = when (this) {
        is NBTTag -> this
        is Byte -> ByteTag(this)
        is Short -> ShortTag(this)
        is Int -> IntTag(this)
        is Long -> LongTag(this)
        is Float -> FloatTag(this)
        is Double -> DoubleTag(this)
        is ByteArray -> ByteArrayTag(this)
        is String -> StringTag(this)
        is List<*> -> ListTag((this as List<*>).map { it.asTag }.toMutableList())
        is Map<*, *> -> CompoundTag(LinkedHashMap((this as Map<*, *>).map { (key, value) ->
            (key?.toString() ?: error("Key cannot be null")) to value.asTag
        }.toMap()))
        is IntArray -> IntArrayTag(this)
        is LongArray -> LongArrayTag(this)
        else -> error("Don't know how to convert $this into a NBT tag")
    }

/**
 * Writes a NBT tag to the provided output stream.
 *
 * @param  stream       The stream to write to.
 * @param  compressed   Whether the tag we are writing should be compressed.
 *
 * @author Koding
 * @since  0.1.3
 */
suspend fun NBTTag.write(stream: OutputStream, compressed: Boolean = false) =
    if (compressed) NBTIO.writeCompressed(this, stream)
    else NBTIO.write(this, stream)

/**
 * Reads a NBT tag from this input stream.
 *
 * @param  compressed     Whether this tag we are reading is compressed.
 * @author Koding
 * @since  0.1.3
 */
suspend fun InputStream.readTag(compressed: Boolean = false) =
    if (compressed) NBTIO.readCompressed(this)
    else NBTIO.read(this)

/**
 * Converts a NBT tag into a named NBT tag.
 *
 * @author Koding
 * @since  0.1.3
 */
fun <T : NBTTag> T.named(name: String) = named(name, this)
