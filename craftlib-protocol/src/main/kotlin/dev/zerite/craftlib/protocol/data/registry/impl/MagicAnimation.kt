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
        @JvmField
        val SWING_ARM = MagicAnimation("Swing Arm")

        @JvmField
        val DAMAGE_ANIMATION = MagicAnimation("Damage Animation")

        @JvmField
        val LEAVE_BED = MagicAnimation("Leave Bed")

        @JvmField
        val EAT_FOOD = MagicAnimation("Eat Food")

        @JvmField
        val CRITICAL_EFFECT = MagicAnimation("Critical Effect")

        @JvmField
        val MAGIC_CRITICAL_EFFECT = MagicAnimation("Magic Critical Effect")

        @JvmField
        val CROUCH = MagicAnimation("Crouch")

        @JvmField
        val UNCROUCH = MagicAnimation("Un-crouch")
    }

}
