@file:JvmName("CoroutineUtil")

package dev.zerite.craftlib.commons.util

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future

/**
 * Converts a suspend block into a {@code Future} object.
 * Note: This is only truly useful in Java.
 *
 * @param  block       The block to convert into a future.
 * @author Koding
 * @since  0.2.0
 */
@Suppress("UNUSED")
fun <T : Any> asFuture(block: suspend () -> T) =
    GlobalScope.future { block() }