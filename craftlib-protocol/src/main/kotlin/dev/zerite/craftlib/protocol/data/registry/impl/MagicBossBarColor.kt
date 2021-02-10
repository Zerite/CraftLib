package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Determines the color of the boss bar.
 *
 * @author Koding
 * @since  0.2.0
 */
open class MagicBossBarColor(name: String) : RegistryEntry(name) {

    companion object : IMinecraftRegistry<MagicBossBarColor> by LazyRegistryDelegate({ MagicRegistry.bossBarColor }) {
        @JvmField
        val PINK = MagicBossBarColor("Pink")

        @JvmField
        val BLUE = MagicBossBarColor("Blue")

        @JvmField
        val RED = MagicBossBarColor("Red")

        @JvmField
        val GREEN = MagicBossBarColor("Green")

        @JvmField
        val YELLOW = MagicBossBarColor("Yellow")

        @JvmField
        val PURPLE = MagicBossBarColor("Purple")

        @JvmField
        val WHITE = MagicBossBarColor("White")
    }

}