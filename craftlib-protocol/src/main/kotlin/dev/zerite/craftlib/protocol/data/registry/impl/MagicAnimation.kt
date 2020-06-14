package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Stores values for the server animation packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class MagicAnimation(name: String) : RegistryEntry(name) {

    companion object {
        val SWING_ARM = MagicAnimation("SWING_ARM")
        val DAMAGE_ANIMATION = MagicAnimation("DAMAGE_ANIMATION")
        val LEAVE_BED = MagicAnimation("LEAVE_BED")
        val EAT_FOOD = MagicAnimation("EAT_FOOD")
        val CRITICAL_EFFECT = MagicAnimation("CRITICAL_EFFECT")
        val MAGIC_CRITICAL_EFFECT = MagicAnimation("MAGIC_CRITICAL_EFFECT")
        val CROUCH = MagicAnimation("CROUCH")
        val UNCROUCH = MagicAnimation("UNCROUCH")
    }

}

/**
 * Easy accessor for the animation registry.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
object Animation : IMinecraftRegistry<MagicAnimation> by MagicRegistry.animation