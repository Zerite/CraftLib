package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Stores values for the server animation packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
open class MagicAnimation(name: String) : RegistryEntry(name) {

    companion object : IMinecraftRegistry<MagicAnimation> by LazyRegistryDelegate({ MagicRegistry.animation }) {
        val SWING_ARM = MagicAnimation("Swing Arm")
        val DAMAGE_ANIMATION = MagicAnimation("Damage Animation")
        val LEAVE_BED = MagicAnimation("Leave Bed")
        val EAT_FOOD = MagicAnimation("Eat Food")
        val CRITICAL_EFFECT = MagicAnimation("Critical Effect")
        val MAGIC_CRITICAL_EFFECT = MagicAnimation("Magic Critical Effect")
        val CROUCH = MagicAnimation("Crouch")
        val UNCROUCH = MagicAnimation("Un-crouch")
    }

}
