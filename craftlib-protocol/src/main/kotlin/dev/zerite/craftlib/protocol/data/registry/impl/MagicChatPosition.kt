package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Defines where chat messages will be displayed.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
open class MagicChatPosition(name: String) : RegistryEntry(name) {

    companion object : IMinecraftRegistry<MagicChatPosition> by LazyRegistryDelegate({ MagicRegistry.chatPosition }) {
        val CHAT = MagicChatPosition("Chat")
        val SYSTEM_MESSAGE = MagicChatPosition("System Message")
        val ACTION_BAR = MagicChatPosition("Action Bar")
    }

}
