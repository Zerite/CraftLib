package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Magic constant for determining the friendly fire state of a team.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class MagicTeamFriendlyFire(name: String) : RegistryEntry(name) {
    companion object {
        val OFF = MagicTeamFriendlyFire("Off")
        val ON = MagicTeamFriendlyFire("On")
        val SEE_FRIENDLY_INVISIBLES = MagicTeamFriendlyFire("See Friendly Invisibles")
    }
}

/**
 * Easy accessor for the team friendly fire registry.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
object TeamFriendlyFire : IMinecraftRegistry<MagicTeamFriendlyFire> by MagicRegistry.teamFriendlyFire
