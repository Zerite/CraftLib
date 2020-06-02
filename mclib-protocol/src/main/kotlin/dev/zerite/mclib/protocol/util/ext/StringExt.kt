package dev.zerite.mclib.protocol.util.ext

import java.util.*

/**
 * Simple regular expression to add dashes into a UUID.
 */
private val dashesRegex = "(.{8})(.{4})(.{4})(.{4})(.{12})".toRegex()

/**
 * Converts the string into a UUID and optionally adds
 * dashes into the string if necessary.
 *
 * @param  dashes        Whether we should be adding dashes into the string.
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
fun String.toUuid(dashes: Boolean = false): UUID =
    UUID.fromString(
        if (dashes) replace(dashesRegex, "\$1-\$2-\$3-\$4-\$5")
        else this
    )