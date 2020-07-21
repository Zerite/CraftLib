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
open class MagicCombatEvent(name: String) : RegistryEntry(name) {

    companion object : IMinecraftRegistry<MagicCombatEvent> by LazyRegistryDelegate({ MagicRegistry.combatEvent }) {
        @JvmField
        val ENTER_COMBAT = MagicCombatEvent("Enter Combat")

        @JvmField
        val END_COMBAT = MagicCombatEvent("End Combat")

        @JvmField
        val ENTITY_DEAD = MagicCombatEvent("Entity Dead")
    }

}
