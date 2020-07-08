package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Stores the magic values which correlate to the teams packet mode enum.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class MagicTeamMode(name: String) : RegistryEntry(name) {
    companion object {
        val CREATE_TEAM = MagicTeamMode("Create Team")
        val REMOVE_TEAM = MagicTeamMode("Remove Team")
        val UPDATE_INFO = MagicTeamMode("Update Info")
        val ADD_PLAYERS = MagicTeamMode("Add Players")
        val REMOVE_PLAYERS = MagicTeamMode("Remove Players")
    }
}

/**
 * Easy accessor for the team mode magic value.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
object TeamMode : IMinecraftRegistry<MagicTeamMode> by MagicRegistry.teamMode
