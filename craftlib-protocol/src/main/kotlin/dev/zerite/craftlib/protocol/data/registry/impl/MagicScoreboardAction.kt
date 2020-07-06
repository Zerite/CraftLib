package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Stores the actions which can be done to a scoreboard in order to create,
 * remove or update it.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class MagicScoreboardAction(name: String) : RegistryEntry(name) {
    companion object {
        val CREATE_SCOREBOARD = MagicScoreboardAction("Create Scoreboard")
        val REMOVE_SCOREBOARD = MagicScoreboardAction("Remove Scoreboard")
        val UPDATE_TEXT = MagicScoreboardAction("Update Display Text")
    }
}

/**
 * Easy accessor for the scoreboard action magic value.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
object ScoreboardAction : IMinecraftRegistry<MagicScoreboardAction> by MagicRegistry.scoreboardAction