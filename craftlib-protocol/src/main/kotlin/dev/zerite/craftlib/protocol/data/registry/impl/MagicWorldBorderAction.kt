package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Controls the functionality of the world border packet.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
class MagicWorldBorderAction(name: String) : RegistryEntry(name) {

    companion object :
        IMinecraftRegistry<MagicWorldBorderAction> by LazyRegistryDelegate({ MagicRegistry.worldBorderAction }) {
        val SET_SIZE = MagicWorldBorderAction("Set Size")
        val LERP_SIZE = MagicWorldBorderAction("Lerp Size")
        val SET_CENTER = MagicWorldBorderAction("Set Center")
        val INITIALIZE = MagicWorldBorderAction("Initialize")
        val SET_WARNING_TIME = MagicWorldBorderAction("Set Warning Time")
        val SET_WARNING_BLOCKS = MagicWorldBorderAction("Set Warning Blocks")
    }

}
