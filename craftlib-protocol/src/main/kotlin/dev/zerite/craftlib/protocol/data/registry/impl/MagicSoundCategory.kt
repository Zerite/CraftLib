package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Used in the named sound packet to allow the client to lower the
 * volumes of specific sounds.
 *
 * @author Koding
 * @since  0.2.0
 */
open class MagicSoundCategory(name: String) : RegistryEntry(name) {
    companion object :
        IMinecraftRegistry<MagicSoundCategory> by LazyRegistryDelegate({ MagicRegistry.soundCategory }) {
        @JvmField
        val MASTER = MagicSoundCategory("Master")

        @JvmField
        val MUSIC = MagicSoundCategory("Music")

        @JvmField
        val RECORDS = MagicSoundCategory("Records")

        @JvmField
        val WEATHER = MagicSoundCategory("Weather")

        @JvmField
        val BLOCKS = MagicSoundCategory("Blocks")

        @JvmField
        val HOSTILE = MagicSoundCategory("Hostile")

        @JvmField
        val NEUTRAL = MagicSoundCategory("Neutral")

        @JvmField
        val PLAYERS = MagicSoundCategory("Players")

        @JvmField
        val AMBIENT = MagicSoundCategory("Ambient")

        @JvmField
        val VOICE = MagicSoundCategory("Voice")
    }
}
