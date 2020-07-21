package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Stores the values for potion effects which can be applied
 * to an entity.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
open class MagicPotionEffect(override val name: String, val positive: Boolean) : RegistryEntry(name) {
    companion object : IMinecraftRegistry<MagicPotionEffect> by LazyRegistryDelegate({ MagicRegistry.potionEffect }) {
        @JvmField
        val SPEED = MagicPotionEffect("Speed", true)

        @JvmField
        val SLOWNESS = MagicPotionEffect("Slowness", false)

        @JvmField
        val HASTE = MagicPotionEffect("Haste", true)

        @JvmField
        val MINING_FATIGUE = MagicPotionEffect("Mining Fatigue", false)

        @JvmField
        val STRENGTH = MagicPotionEffect("Strength", true)

        @JvmField
        val INSTANT_HEALTH = MagicPotionEffect("Instant Health", true)

        @JvmField
        val INSTANT_DAMAGE = MagicPotionEffect("Instant Damage", false)

        @JvmField
        val JUMP_BOOST = MagicPotionEffect("Jump Boost", true)

        @JvmField
        val NAUSEA = MagicPotionEffect("Nausea", false)

        @JvmField
        val REGENERATION = MagicPotionEffect("Regeneration", true)

        @JvmField
        val RESISTANCE = MagicPotionEffect("Resistance", true)

        @JvmField
        val FIRE_RESISTANCE = MagicPotionEffect("Fire Resistance", true)

        @JvmField
        val WATER_BREATHING = MagicPotionEffect("Water Breathing", true)

        @JvmField
        val INVISIBILITY = MagicPotionEffect("Invisibility", true)

        @JvmField
        val BLINDNESS = MagicPotionEffect("Blindness", false)

        @JvmField
        val NIGHT_VISION = MagicPotionEffect("Night Vision", true)

        @JvmField
        val HUNGER = MagicPotionEffect("Hunger", false)

        @JvmField
        val WEAKNESS = MagicPotionEffect("Weakness", false)

        @JvmField
        val POISON = MagicPotionEffect("Poison", false)

        @JvmField
        val WITHER = MagicPotionEffect("Wither", false)

        @JvmField
        val HEALTH_BOOST = MagicPotionEffect("Health Boost", true)

        @JvmField
        val ABSORPTION = MagicPotionEffect("Absorption", true)

        @JvmField
        val SATURATION = MagicPotionEffect("Saturation", true)
    }
}
