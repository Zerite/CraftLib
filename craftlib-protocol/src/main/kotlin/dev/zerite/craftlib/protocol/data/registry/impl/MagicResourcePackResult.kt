package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Indicates the result of the resource pack being applied.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
open class MagicResourcePackResult(name: String) : RegistryEntry(name) {

    companion object :
        IMinecraftRegistry<MagicResourcePackResult> by LazyRegistryDelegate({ MagicRegistry.resourcePackResult }) {
        val LOADED = MagicResourcePackResult("Loaded")
        val DECLINED = MagicResourcePackResult("Declined")
        val FAILED_DOWNLOAD = MagicResourcePackResult("Failed Download")
        val ACCEPTED = MagicResourcePackResult("Accepted")
    }

}
