@file:JvmName("ForgeCompatUtil")
package dev.zerite.craftlib.protocol.compat.forge

import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.util.get

/**
 * Key which indexes the Forge compatibility flag.
 */
const val FORGE_COMPAT_KEY = "forge_compat:enabled"

/**
 * Extension variable for the Netty connection to easily check if
 * Forge support is enabled.
 */
var NettyConnection.forge: Boolean
    get() = this[FORGE_COMPAT_KEY] ?: false
    set(value) {
        this[FORGE_COMPAT_KEY] = value
    }
