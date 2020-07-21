package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Stores the magic values which correlate to the teams packet mode enum.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
open class MagicTeamMode(name: String) : RegistryEntry(name) {
    companion object : IMinecraftRegistry<MagicTeamMode> by LazyRegistryDelegate({ MagicRegistry.teamMode }) {
        @JvmField
        val CREATE_TEAM = MagicTeamMode("Create Team")

        @JvmField
        val REMOVE_TEAM = MagicTeamMode("Remove Team")

        @JvmField
        val UPDATE_INFO = MagicTeamMode("Update Info")

        @JvmField
        val ADD_PLAYERS = MagicTeamMode("Add Players")

        @JvmField
        val REMOVE_PLAYERS = MagicTeamMode("Remove Players")
    }
}
