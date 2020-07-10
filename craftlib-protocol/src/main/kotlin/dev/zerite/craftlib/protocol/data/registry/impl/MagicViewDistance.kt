package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * All the options a client can set for view distance.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class MagicViewDistance(name: String) : RegistryEntry(name) {
    companion object {
        val FAR = MagicViewDistance("Far")
        val NORMAL = MagicViewDistance("Normal")
        val SHORT = MagicViewDistance("Short")
        val TINY = MagicViewDistance("Tiny")
    }
}

/**
 * Easy accessor for the view distance magic value.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
object ViewDistance : IMinecraftRegistry<MagicViewDistance> by MagicRegistry.viewDistance
