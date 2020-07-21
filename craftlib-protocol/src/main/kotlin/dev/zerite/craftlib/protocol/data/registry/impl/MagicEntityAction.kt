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
open class MagicEntityAction(name: String) : RegistryEntry(name) {
    companion object : IMinecraftRegistry<MagicEntityAction> by LazyRegistryDelegate({ MagicRegistry.entityAction }) {
        @JvmField
        val CROUCH = MagicEntityAction("Crouch")

        @JvmField
        val UNCROUCH = MagicEntityAction("Uncrouch")

        @JvmField
        val LEAVE_BED = MagicEntityAction("Leave Bed")

        @JvmField
        val START_SPRINTING = MagicEntityAction("Start Sprinting")

        @JvmField
        val STOP_SPRINTING = MagicEntityAction("Stop Sprinting")

        @JvmField
        val HORSE_JUMP = MagicEntityAction("Horse Jump")

        @JvmField
        val OPEN_INVENTORY = MagicEntityAction("Open Inventory")
    }
}
