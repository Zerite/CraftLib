package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Vanilla entries for valid entity properties.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
open class MagicEntityProperty(
    name: String,
    @Suppress("UNUSED") val default: Double,
    @Suppress("UNUSED") val min: Double,
    @Suppress("UNUSED") val max: Double
) : RegistryEntry(name) {

    companion object :
        IMinecraftRegistry<MagicEntityProperty> by LazyRegistryDelegate({ MagicRegistry.entityProperty }) {
        val GENERIC_MAX_HEALTH = MagicEntityProperty("Max Health", 20.0, 0.0, Double.MAX_VALUE)
        val GENERIC_FOLLOW_RANGE = MagicEntityProperty("Follow Range", 32.0, 0.0, 2048.0)
        val GENERIC_KNOCKBACK_RESISTANCE = MagicEntityProperty("Knockback Resistance", 0.0, 0.0, 1.0)
        val GENERIC_MOVEMENT_SPEED = MagicEntityProperty("Movement Speed", 0.699999988079071, 0.0, Double.MAX_VALUE)
        val GENERIC_ATTACK_DAMAGE = MagicEntityProperty("Generic Attack Damage", 2.0, 0.0, Double.MAX_VALUE)
        val HORSE_JUMP_STRENGTH = MagicEntityProperty("Horse Jump Strength", 0.7, 0.0, 2.0)
        val ZOMBIE_SPAWN_REINFORCEMENTS = MagicEntityProperty("Spawn Reinforcements Chance", 0.0, 0.0, 1.0)
    }

}
