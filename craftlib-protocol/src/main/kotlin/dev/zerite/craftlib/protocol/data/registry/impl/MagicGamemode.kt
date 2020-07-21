package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Contains the vanilla game modes which are sent during the join
 * game packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
open class MagicGamemode(name: String) : RegistryEntry(name) {

    companion object : IMinecraftRegistry<MagicGamemode> by LazyRegistryDelegate({ MagicRegistry.gamemode }) {
        @JvmField
        val SURVIVAL = MagicGamemode("Survival")

        @JvmField
        val CREATIVE = MagicGamemode("Creative")

        @JvmField
        val ADVENTURE = MagicGamemode("Adventure")

        @JvmField
        val SPECTATOR = MagicGamemode("Spectator")
    }

}
