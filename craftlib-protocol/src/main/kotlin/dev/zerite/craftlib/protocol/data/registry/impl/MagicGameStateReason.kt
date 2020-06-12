package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Stores the game state mappings which indicate to the client
 * actions which should be executed such as controlling rain and
 * showing the credits.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
open class MagicGameStateReason(name: String) : RegistryEntry(name) {
    @Suppress("UNUSED")
    companion object {
        val INVALID_BED = MagicGameStateReason("INVALID_BED")
        val BEGIN_RAINING = MagicGameStateReason("BEGIN_RAINING")
        val END_RAINING = MagicGameStateReason("END_RAINING")
        val CHANGE_GAME_MODE = MagicGameStateReason("CHANGE_GAME_MODE")
        val ENTER_CREDITS = MagicGameStateReason("ENTER_CREDITS")
        val DEMO_MESSAGES = MagicGameStateReason("DEMO_MESSAGES")
        val BOW_HIT_SOUND = MagicGameStateReason("BOW_HIT_SOUND")
        val FADE_VALUE = MagicGameStateReason("FADE_VALUE")
        val FADE_TIME = MagicGameStateReason("FADE_TIME")
    }
}

/**
 * Alias for the game state reason field in the magic registry.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
object GameStateReason : IMinecraftRegistry<MagicGameStateReason> by MagicRegistry.gameStateReason