package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
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
    companion object :
        IMinecraftRegistry<MagicGameStateReason> by LazyRegistryDelegate({ MagicRegistry.gameStateReason }) {
        @JvmField
        val INVALID_BED = MagicGameStateReason("Invalid Bed Location")

        @JvmField
        val BEGIN_RAINING = MagicGameStateReason("Start Raining")

        @JvmField
        val END_RAINING = MagicGameStateReason("Stop Raining")

        @JvmField
        val CHANGE_GAME_MODE = MagicGameStateReason("Change Gamemode")

        @JvmField
        val ENTER_CREDITS = MagicGameStateReason("Enter Credits")

        @JvmField
        val DEMO_MESSAGES = MagicGameStateReason("Show Demo")

        @JvmField
        val BOW_HIT_SOUND = MagicGameStateReason("Bow Hit Sound")

        @JvmField
        val FADE_VALUE = MagicGameStateReason("Fade Value")

        @JvmField
        val FADE_TIME = MagicGameStateReason("Fade Time")
    }
}
