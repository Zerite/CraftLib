package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Stores the status value from the player digging packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
open class MagicPlayerDiggingStatus(name: String) : RegistryEntry(name) {
    companion object :
        IMinecraftRegistry<MagicPlayerDiggingStatus> by LazyRegistryDelegate({ MagicRegistry.playerDiggingStatus }) {
        val STARTED_DIGGING = MagicPlayerDiggingStatus("Started Digging")
        val CANCELLED_DIGGING = MagicPlayerDiggingStatus("Cancelled Digging")
        val FINISHED_DIGGING = MagicPlayerDiggingStatus("Finished Digging")
        val DROP_ITEM_STACK = MagicPlayerDiggingStatus("Drop Item Stack")
        val DROP_ITEM = MagicPlayerDiggingStatus("Drop Item")
        val SHOOT_ARROW = MagicPlayerDiggingStatus("Shoot Arrow")
    }
}
