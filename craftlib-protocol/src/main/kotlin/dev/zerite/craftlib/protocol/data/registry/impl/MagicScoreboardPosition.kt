package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Contains all the possible scoreboard display slots where they can
 * be displayed on the screen.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
open class MagicScoreboardPosition(name: String) : RegistryEntry(name) {
    companion object :
        IMinecraftRegistry<MagicScoreboardPosition> by LazyRegistryDelegate({ MagicRegistry.scoreboardPosition }) {
        @JvmField
        val LIST = MagicScoreboardPosition("Player List")

        @JvmField
        val SIDEBAR = MagicScoreboardPosition("Sidebar")

        @JvmField
        val BELOW_NAME = MagicScoreboardPosition("Below Name")
    }
}
