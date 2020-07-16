package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Contains the vanilla difficulty values which are mapped by their
 * protocol ID.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
open class MagicDifficulty(name: String) : RegistryEntry(name) {

    companion object : IMinecraftRegistry<MagicDifficulty> by LazyRegistryDelegate({ MagicRegistry.difficulty }) {
        val PEACEFUL = MagicDifficulty("Peaceful")
        val EASY = MagicDifficulty("Easy")
        val NORMAL = MagicDifficulty("Normal")
        val HARD = MagicDifficulty("Hard")
    }

}
