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
        val SPEED = MagicPotionEffect("Speed", true)
        val SLOWNESS = MagicPotionEffect("Slowness", false)
        val HASTE = MagicPotionEffect("Haste", true)
        val MINING_FATIGUE = MagicPotionEffect("Mining Fatigue", false)
        val STRENGTH = MagicPotionEffect("Strength", true)
        val INSTANT_HEALTH = MagicPotionEffect("Instant Health", true)
        val INSTANT_DAMAGE = MagicPotionEffect("Instant Damage", false)
        val JUMP_BOOST = MagicPotionEffect("Jump Boost", true)
        val NAUSEA = MagicPotionEffect("Nausea", false)
        val REGENERATION = MagicPotionEffect("Regeneration", true)
        val RESISTANCE = MagicPotionEffect("Resistance", true)
        val FIRE_RESISTANCE = MagicPotionEffect("Fire Resistance", true)
        val WATER_BREATHING = MagicPotionEffect("Water Breathing", true)
        val INVISIBILITY = MagicPotionEffect("Invisibility", true)
        val BLINDNESS = MagicPotionEffect("Blindness", false)
        val NIGHT_VISION = MagicPotionEffect("Night Vision", true)
        val HUNGER = MagicPotionEffect("Hunger", false)
        val WEAKNESS = MagicPotionEffect("Weakness", false)
        val POISON = MagicPotionEffect("Poison", false)
        val WITHER = MagicPotionEffect("Wither", false)
        val HEALTH_BOOST = MagicPotionEffect("Health Boost", true)
        val ABSORPTION = MagicPotionEffect("Absorption", true)
        val SATURATION = MagicPotionEffect("Saturation", true)
    }
}
