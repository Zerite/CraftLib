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
open class MagicClientAnimation(name: String) : RegistryEntry(name) {

    companion object :
        IMinecraftRegistry<MagicClientAnimation> by LazyRegistryDelegate({ MagicRegistry.clientAnimation }) {
        @JvmField
        val NO_ANIMATION = MagicClientAnimation("No Animation")

        @JvmField
        val SWING_ARM = MagicClientAnimation("Swing Arm")

        @JvmField
        val DAMAGE_ANIMATION = MagicClientAnimation("Damage Animation")

        @JvmField
        val LEAVE_BED = MagicClientAnimation("Leave Bed")

        @JvmField
        val EAT_FOOD = MagicClientAnimation("Eat Food")

        @JvmField
        val CRITICAL_EFFECT = MagicClientAnimation("Critical Effect")

        @JvmField
        val MAGIC_CRITICAL_EFFECT = MagicClientAnimation("Magic Critical Effect")

        @JvmField
        val CROUCH = MagicClientAnimation("Crouch")

        @JvmField
        val UNCROUCH = MagicClientAnimation("Un-crouch")
    }

}
