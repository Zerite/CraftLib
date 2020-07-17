package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Sent to the client to update their combat status.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
class MagicCombatEvent(name: String) : RegistryEntry(name) {

    companion object : IMinecraftRegistry<MagicCombatEvent> by LazyRegistryDelegate({ MagicRegistry.combatEvent }) {
        val ENTER_COMBAT = MagicCombatEvent("Enter Combat")
        val END_COMBAT = MagicCombatEvent("End Combat")
        val ENTITY_DEAD = MagicCombatEvent("Entity Dead")
    }

}
