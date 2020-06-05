package dev.zerite.craftlib.protocol.util.delegate

import dev.zerite.craftlib.protocol.util.ext.clearBit
import dev.zerite.craftlib.protocol.util.ext.setBit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty

/**
 * Property delegate which acts as a boolean, allowing for an int field
 * to be modified with a mask based on the state.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class BitBooleanDelegate(private val field: KMutableProperty0<Int>, private val mask: Int) :
    ReadWriteProperty<Any, Boolean> {
    override fun getValue(thisRef: Any, property: KProperty<*>) =
        (field.get() and mask) == mask

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) =
        field.get().let { field.set(if (value) it.setBit(mask) else it.clearBit(mask)) }
}

/**
 * Creates a property delegate for a field using the given mask.
 *
 * @param  field      The field which we are modifying.
 * @Param  mask       The bit mask we're going to change in the int.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
fun bitBoolean(field: KMutableProperty0<Int>, mask: Int) = BitBooleanDelegate(field, mask)
