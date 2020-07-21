@file:JvmName("NumberUtil")
package dev.zerite.craftlib.protocol.util.ext

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
