package dev.zerite.mclib.protocol.util.delegate

import dev.zerite.mclib.protocol.data.MappedEnum
import dev.zerite.mclib.protocol.data.get
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

/**
 * Basic delegate class which simply returns the result of the provided
 * reading and writing functions.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class MappedEnumDelegate<T>(
    private val read: () -> T,
    private val write: (T) -> Unit
) : ReadWriteProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>) = read()
    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) = write(value)
}

/**
 * Creates a mapping delegate which modifies the provided property
 * and sets it to the ID of the selected enum.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
inline fun <reified T> mapEnum(
    property: KMutableProperty0<Int>,
    enum: MappedEnum.Companion<T>,
    default: T
): MappedEnumDelegate<T> where T : Enum<T>, T : MappedEnum =
    MappedEnumDelegate(
        { enum[property.get()] ?: default },
        { property.set(it.id) }
    )