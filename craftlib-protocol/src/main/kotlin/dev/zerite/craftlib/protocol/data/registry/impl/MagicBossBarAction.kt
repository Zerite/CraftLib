package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Determines how the boss bar packet will be interpreted.
 *
 * @author Koding
 * @since  0.2.0
 */
open class MagicBossBarAction(name: String) : RegistryEntry(name) {

    companion object : IMinecraftRegistry<MagicBossBarAction> by LazyRegistryDelegate({ MagicRegistry.bossBarAction }) {
        @JvmField
        val ADD = MagicBossBarAction("Add")

        @JvmField
        val REMOVE = MagicBossBarAction("Remove")

        @JvmField
        val UPDATE_HEALTH = MagicBossBarAction("Update Health")

        @JvmField
        val UPDATE_TITLE = MagicBossBarAction("Update Title")

        @JvmField
        val UPDATE_STYLE = MagicBossBarAction("Update Style")

        @JvmField
        val UPDATE_FLAGS = MagicBossBarAction("Update Flags")
    }

}