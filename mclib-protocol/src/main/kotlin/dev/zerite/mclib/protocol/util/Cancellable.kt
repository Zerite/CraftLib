package dev.zerite.mclib.protocol.util

/**
 * Simple utility for easily storing whether a function invocation
 * should be cancelled.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
open class Cancellable(var cancelled: Boolean = false) {

    /**
     * Cancel this response.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @Suppress("UNUSED")
    fun cancel() {
        // Set cancelled to true
        cancelled = true
    }

}