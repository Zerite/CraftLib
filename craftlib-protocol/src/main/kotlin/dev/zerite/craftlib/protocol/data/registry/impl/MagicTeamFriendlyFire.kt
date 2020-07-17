package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Magic constant for determining the friendly fire state of a team.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
open class MagicTeamFriendlyFire(name: String) : RegistryEntry(name) {
    companion object :
        IMinecraftRegistry<MagicTeamFriendlyFire> by LazyRegistryDelegate({ MagicRegistry.teamFriendlyFire }) {
        val OFF = MagicTeamFriendlyFire("Off")
        val ON = MagicTeamFriendlyFire("On")
        val SEE_FRIENDLY_INVISIBLES = MagicTeamFriendlyFire("See Friendly Invisibles")
    }
}
