package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Contains the mappings for a vanilla dimension based on the IDs sent.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
open class MagicDimension(name: String) : RegistryEntry(name) {

    companion object {
        val NETHER = MagicDimension("NETHER")
        val OVERWORLD = MagicDimension("OVERWORLD")
        val END = MagicDimension("END")
    }

}

/**
 * Easy accessor for the difficulty enum in the registry.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
object Dimension : IMinecraftRegistry<MagicDimension> by MagicRegistry.dimension