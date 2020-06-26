package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Stores the action mappings for the update block entity packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class MagicBlockEntityUpdateAction(name: String) : RegistryEntry(name) {

    companion object {
        val SET_SPAWNER_MOB = MagicBlockEntityUpdateAction("Set Spawner Mob")
    }

}

/**
 * Simple static accessor for the block entity update action magic value.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
object BlockEntityUpdateAction :
    IMinecraftRegistry<MagicBlockEntityUpdateAction> by MagicRegistry.blockEntityUpdateAction