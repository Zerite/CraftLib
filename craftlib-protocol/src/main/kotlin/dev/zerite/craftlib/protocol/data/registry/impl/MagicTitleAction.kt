package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Defines the action which the title packet will execute.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
class MagicTitleAction(name: String) : RegistryEntry(name) {

    companion object : IMinecraftRegistry<MagicTitleAction> by LazyRegistryDelegate({ MagicRegistry.titleAction }) {
        val TITLE = MagicTitleAction("Title")
        val SUBTITLE = MagicTitleAction("Subtitle")
        val TIMES = MagicTitleAction("Times")
        val CLEAR = MagicTitleAction("Clear")
        val RESET = MagicTitleAction("Reset")
    }

}
