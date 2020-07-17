package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Stores the possible actions which can be sent in the
 * client status packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
open class MagicClientStatus(name: String) : RegistryEntry(name) {
    companion object : IMinecraftRegistry<MagicClientStatus> by LazyRegistryDelegate({ MagicRegistry.clientStatus }) {
        val PERFORM_RESPAWN = MagicClientStatus("Perform Respawn")
        val REQUEST_STATS = MagicClientStatus("Request Statistics")
        val OPEN_INVENTORY_ACHIEVEMENT = MagicClientStatus("Open Achievements Inventory")
    }
}
