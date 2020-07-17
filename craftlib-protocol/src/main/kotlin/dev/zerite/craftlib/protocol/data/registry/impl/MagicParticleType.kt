package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Stores the various particle types which can be displayed.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
open class MagicParticleType(name: String, val argumentCount: Int = 0) : RegistryEntry(name) {

    companion object : IMinecraftRegistry<MagicParticleType> by LazyRegistryDelegate({ MagicRegistry.particleType }) {
        val EXPLOSION_NORMAL = MagicParticleType("Normal Explosion")
        val EXPLOSION_LARGE = MagicParticleType("Large Explosion")
        val EXPLOSION_HUGE = MagicParticleType("Huge Explosion")
        val FIREWORKS_SPARK = MagicParticleType("Firework Spark")
        val WATER_BUBBLE = MagicParticleType("Water Bubble")
        val WATER_SPLASH = MagicParticleType("Water Splash")
        val WATER_WAKE = MagicParticleType("Water Wake")
        val SUSPENDED = MagicParticleType("Suspended")
        val SUSPENDED_DEPTH = MagicParticleType("Suspended Depth")
        val CRITICAL = MagicParticleType("Critical")
        val CRITICAL_MAGIC = MagicParticleType("Magic Critical")
        val SMOKE_NORMAL = MagicParticleType("Smoke")
        val SMOKE_LARGE = MagicParticleType("Large Smoke")
        val SPELL = MagicParticleType("Spell")
        val SPELL_INSTANT = MagicParticleType("Spell Instant")
        val SPELL_MOB = MagicParticleType("Spell Mob")
        val SPELL_MOB_AMBIENT = MagicParticleType("Spell Mob Ambient")
        val SPELL_WITCH = MagicParticleType("Spell Witch")
        val DRIP_WATER = MagicParticleType("Water Drop")
        val DRIP_LAVA = MagicParticleType("Lava Drop")
        val VILLAGER_ANGRY = MagicParticleType("Angry Villager")
        val VILLAGER_HAPPY = MagicParticleType("Happy Villager")
        val TOWN_AURA = MagicParticleType("Town Aura")
        val NOTE = MagicParticleType("Note")
        val PORTAL = MagicParticleType("Portal")
        val ENCHANTMENT_TABLE = MagicParticleType("Enchantment Table")
        val FLAME = MagicParticleType("Flame")
        val LAVA = MagicParticleType("Lava")
        val FOOTSTEP = MagicParticleType("Footstep")
        val CLOUD = MagicParticleType("Cloud")
        val REDSTONE = MagicParticleType("Redstone")
        val SNOWBALL = MagicParticleType("Snowball")
        val SNOW_SHOVEL = MagicParticleType("Snow Shovel")
        val SLIME = MagicParticleType("Slime")
        val HEART = MagicParticleType("Heart")
        val BARRIER = MagicParticleType("Barrier")
        val ITEM_CRACK = MagicParticleType("Item Crack", 2)
        val BLOCK_CRACK = MagicParticleType("Block Crack", 1)
        val BLOCK_DUST = MagicParticleType("Block Dust", 1)
        val WATER_DROP = MagicParticleType("Water Drop")
        val ITEM_TAKE = MagicParticleType("Item Take")
        val MOB_APPEARANCE = MagicParticleType("Mob Appearance")
    }

}
