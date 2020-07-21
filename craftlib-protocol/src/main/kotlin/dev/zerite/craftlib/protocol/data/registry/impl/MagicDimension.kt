package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Contains the mappings for a vanilla dimension based on the IDs sent.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
open class MagicDimension(name: String) : RegistryEntry(name) {

    companion object : IMinecraftRegistry<MagicDimension> by LazyRegistryDelegate({ MagicRegistry.dimension }) {
        @JvmField
        val NETHER = MagicDimension("The Nether")

        @JvmField
        val OVERWORLD = MagicDimension("Overworld")

        @JvmField
        val END = MagicDimension("The End")
    }

}
