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
        @JvmField
        val EXPLOSION_NORMAL = MagicParticleType("Normal Explosion")

        @JvmField
        val EXPLOSION_LARGE = MagicParticleType("Large Explosion")

        @JvmField
        val EXPLOSION_HUGE = MagicParticleType("Huge Explosion")

        @JvmField
        val FIREWORKS_SPARK = MagicParticleType("Firework Spark")

        @JvmField
        val WATER_BUBBLE = MagicParticleType("Water Bubble")

        @JvmField
        val WATER_SPLASH = MagicParticleType("Water Splash")

        @JvmField
        val WATER_WAKE = MagicParticleType("Water Wake")

        @JvmField
        val SUSPENDED = MagicParticleType("Suspended")

        @JvmField
        val SUSPENDED_DEPTH = MagicParticleType("Suspended Depth")

        @JvmField
        val CRITICAL = MagicParticleType("Critical")

        @JvmField
        val CRITICAL_MAGIC = MagicParticleType("Magic Critical")

        @JvmField
        val SMOKE_NORMAL = MagicParticleType("Smoke")

        @JvmField
        val SMOKE_LARGE = MagicParticleType("Large Smoke")

        @JvmField
        val SPELL = MagicParticleType("Spell")

        @JvmField
        val SPELL_INSTANT = MagicParticleType("Spell Instant")

        @JvmField
        val SPELL_MOB = MagicParticleType("Spell Mob")

        @JvmField
        val SPELL_MOB_AMBIENT = MagicParticleType("Spell Mob Ambient")

        @JvmField
        val SPELL_WITCH = MagicParticleType("Spell Witch")

        @JvmField
        val DRIP_WATER = MagicParticleType("Water Drop")

        @JvmField
        val DRIP_LAVA = MagicParticleType("Lava Drop")

        @JvmField
        val VILLAGER_ANGRY = MagicParticleType("Angry Villager")

        @JvmField
        val VILLAGER_HAPPY = MagicParticleType("Happy Villager")

        @JvmField
        val TOWN_AURA = MagicParticleType("Town Aura")

        @JvmField
        val NOTE = MagicParticleType("Note")

        @JvmField
        val PORTAL = MagicParticleType("Portal")

        @JvmField
        val ENCHANTMENT_TABLE = MagicParticleType("Enchantment Table")

        @JvmField
        val FLAME = MagicParticleType("Flame")

        @JvmField
        val LAVA = MagicParticleType("Lava")

        @JvmField
        val FOOTSTEP = MagicParticleType("Footstep")

        @JvmField
        val CLOUD = MagicParticleType("Cloud")

        @JvmField
        val REDSTONE = MagicParticleType("Redstone")

        @JvmField
        val SNOWBALL = MagicParticleType("Snowball")

        @JvmField
        val SNOW_SHOVEL = MagicParticleType("Snow Shovel")

        @JvmField
        val SLIME = MagicParticleType("Slime")

        @JvmField
        val HEART = MagicParticleType("Heart")

        @JvmField
        val BARRIER = MagicParticleType("Barrier")

        @JvmField
        val ITEM_CRACK = MagicParticleType("Item Crack", 2)

        @JvmField
        val BLOCK_CRACK = MagicParticleType("Block Crack", 1)

        @JvmField
        val BLOCK_DUST = MagicParticleType("Block Dust", 1)

        @JvmField
        val WATER_DROP = MagicParticleType("Water Drop")

        @JvmField
        val ITEM_TAKE = MagicParticleType("Item Take")

        @JvmField
        val MOB_APPEARANCE = MagicParticleType("Mob Appearance")
    }

}
