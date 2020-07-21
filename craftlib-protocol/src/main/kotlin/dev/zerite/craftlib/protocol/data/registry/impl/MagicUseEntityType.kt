package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Controls how the use entity packet works.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
open class MagicUseEntityType(name: String) : RegistryEntry(name) {

    companion object : IMinecraftRegistry<MagicUseEntityType> by LazyRegistryDelegate({ MagicRegistry.useEntityType }) {
        @JvmField
        val INTERACT = MagicUseEntityType("Interact")

        @JvmField
        val ATTACK = MagicUseEntityType("Attack")

        @JvmField
        val INTERACT_AT = MagicUseEntityType("Interact At")
    }

}
