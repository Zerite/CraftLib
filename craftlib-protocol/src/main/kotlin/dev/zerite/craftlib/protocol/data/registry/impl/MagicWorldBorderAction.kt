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
open class MagicWorldBorderAction(name: String) : RegistryEntry(name) {

    companion object :
        IMinecraftRegistry<MagicWorldBorderAction> by LazyRegistryDelegate({ MagicRegistry.worldBorderAction }) {
        @JvmField
        val SET_SIZE = MagicWorldBorderAction("Set Size")

        @JvmField
        val LERP_SIZE = MagicWorldBorderAction("Lerp Size")

        @JvmField
        val SET_CENTER = MagicWorldBorderAction("Set Center")

        @JvmField
        val INITIALIZE = MagicWorldBorderAction("Initialize")

        @JvmField
        val SET_WARNING_TIME = MagicWorldBorderAction("Set Warning Time")

        @JvmField
        val SET_WARNING_BLOCKS = MagicWorldBorderAction("Set Warning Blocks")
    }

}
