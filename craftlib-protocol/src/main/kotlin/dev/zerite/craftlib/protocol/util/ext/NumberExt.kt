@file:JvmName("NumberUtil")

package dev.zerite.craftlib.protocol.util.ext

import java.util.*

private const val uuidMagic = 0xB00B_CAFE_BABEL

/**
 * Clears a bit from this int and returns it.
 *
 * @param  value       The value to clear.
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
fun Int.clearBit(value: Int) = this and value.inv()

/**
 * Sets a bit to this int and returns it.
 *
 * @param  value       The value to set.
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
fun Int.setBit(value: Int) = this or value

/**
 * Converts a number into a UUID, used to retain some compatibility
 * with older versions.
 *
 * @author Koding
 * @since  0.2.0
 */
fun Number.toLegacyUUID() = UUID(toLong(), uuidMagic)

/**
 * Reads from the most significant bits
 *
 * @author Koding
 * @since  0.2.0
 */
val UUID.legacy: Long?
    get() = mostSignificantBits.takeIf { leastSignificantBits == uuidMagic }