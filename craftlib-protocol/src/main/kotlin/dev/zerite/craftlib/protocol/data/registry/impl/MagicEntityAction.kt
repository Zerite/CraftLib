package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Sent by the entity action packet to update the player.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class MagicEntityAction(name: String) : RegistryEntry(name) {
    companion object : IMinecraftRegistry<MagicEntityAction> by LazyRegistryDelegate({ MagicRegistry.entityAction }) {
        val CROUCH = MagicEntityAction("Crouch")
        val UNCROUCH = MagicEntityAction("Uncrouch")
        val LEAVE_BED = MagicEntityAction("Leave Bed")
        val START_SPRINTING = MagicEntityAction("Start Sprinting")
        val STOP_SPRINTING = MagicEntityAction("Stop Sprinting")
    }
}
