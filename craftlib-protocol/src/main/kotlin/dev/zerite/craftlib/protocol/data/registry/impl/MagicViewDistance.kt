package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * All the options a client can set for view distance.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
open class MagicViewDistance(name: String) : RegistryEntry(name) {
    companion object : IMinecraftRegistry<MagicViewDistance> by LazyRegistryDelegate({ MagicRegistry.viewDistance }) {
        @JvmField
        val FAR = MagicViewDistance("Far")

        @JvmField
        val NORMAL = MagicViewDistance("Normal")

        @JvmField
        val SHORT = MagicViewDistance("Short")

        @JvmField
        val TINY = MagicViewDistance("Tiny")
    }
}
