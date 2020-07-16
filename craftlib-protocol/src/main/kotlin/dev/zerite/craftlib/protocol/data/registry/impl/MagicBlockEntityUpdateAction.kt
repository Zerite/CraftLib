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
class MagicBlockEntityUpdateAction(name: String) : RegistryEntry(name) {

    companion object :
        IMinecraftRegistry<MagicBlockEntityUpdateAction> by LazyRegistryDelegate({ MagicRegistry.blockEntityUpdateAction }) {
        val SET_SPAWNER_MOB = MagicBlockEntityUpdateAction("Set Spawner Mob")
    }

}
