package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Stores the action mappings for the update block entity packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
open class MagicBlockEntityUpdateAction(name: String) : RegistryEntry(name) {

    companion object :
        IMinecraftRegistry<MagicBlockEntityUpdateAction> by LazyRegistryDelegate({ MagicRegistry.blockEntityUpdateAction }) {
        @JvmField
        val SET_SPAWNER_MOB = MagicBlockEntityUpdateAction("Set Spawner Mob")

        @JvmField
        val SET_COMMAND_BLOCK_TEXT = MagicBlockEntityUpdateAction("Set Command Block Text")

        @JvmField
        val SET_BEACON_DATA = MagicBlockEntityUpdateAction("Set Beacon Data")

        @JvmField
        val SET_MOB_HEAD = MagicBlockEntityUpdateAction("Set Mob Head")

        @JvmField
        val SET_FLOWER_POT = MagicBlockEntityUpdateAction("Set Flower Pot")

        @JvmField
        val SET_BANNER = MagicBlockEntityUpdateAction("Set Banner")
    }

}
