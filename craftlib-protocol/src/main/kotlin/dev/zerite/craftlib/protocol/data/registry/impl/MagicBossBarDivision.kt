package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Determines the amount of notches in the boss bar.
 *
 * @author Koding
 * @since  0.2.0
 */
open class MagicBossBarDivision(name: String) : RegistryEntry(name) {

    companion object : IMinecraftRegistry<MagicBossBarDivision> by LazyRegistryDelegate({ MagicRegistry.bossBarDivision }) {
        @JvmField
        val NO_DIVISION = MagicBossBarDivision("No Division")

        @JvmField
        val SIX_NOTCHES = MagicBossBarDivision("Six Notches")

        @JvmField
        val TEN_NOTCHES = MagicBossBarDivision("Ten Notches")

        @JvmField
        val TWELVE_NOTCHES = MagicBossBarDivision("Twelve Notches")

        @JvmField
        val TWENTY_NOTCHES = MagicBossBarDivision("Twenty Notches")
    }

}