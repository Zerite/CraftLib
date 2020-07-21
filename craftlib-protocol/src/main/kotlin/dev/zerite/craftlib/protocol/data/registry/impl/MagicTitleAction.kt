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
open class MagicTitleAction(name: String) : RegistryEntry(name) {

    companion object : IMinecraftRegistry<MagicTitleAction> by LazyRegistryDelegate({ MagicRegistry.titleAction }) {
        @JvmField
        val TITLE = MagicTitleAction("Title")

        @JvmField
        val SUBTITLE = MagicTitleAction("Subtitle")

        @JvmField
        val TIMES = MagicTitleAction("Times")

        @JvmField
        val CLEAR = MagicTitleAction("Clear")

        @JvmField
        val RESET = MagicTitleAction("Reset")
    }

}
