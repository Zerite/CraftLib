package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Stores all the valid mob types.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
open class MagicMobType(
    name: String,
    @Suppress("UNUSED") val boundingXZ: Double,
    @Suppress("UNUSED") val boundingY: Double
) : RegistryEntry(name) {

    companion object : IMinecraftRegistry<MagicMobType> by LazyRegistryDelegate({ MagicRegistry.mobType }) {
        @JvmField
        val MOB = MagicMobType("Mob", 0.0, 0.0)

        @JvmField
        val MONSTER = MagicMobType("Monster", 0.0, 0.0)

        @JvmField
        val CREEPER = MagicMobType("Creeper", 0.6, 1.8)

        @JvmField
        val SKELETON = MagicMobType("Skeleton", 0.6, 1.8)

        @JvmField
        val SPIDER = MagicMobType("Spider", 1.4, 0.9)

        @JvmField
        val GIANT_ZOMBIE = MagicMobType("Giant Zombie", 3.6, 10.8)

        @JvmField
        val ZOMBIE = MagicMobType("Zombie", 0.6, 1.8)

        @JvmField
        val SLIME = MagicMobType("Slime", 0.6, 0.6)

        @JvmField
        val GHAST = MagicMobType("Ghast", 4.0, 4.0)

        @JvmField
        val ZOMBIE_PIGMAN = MagicMobType("Zombie Pigman", 0.6, 1.8)

        @JvmField
        val ENDERMAN = MagicMobType("Enderman", 0.0, 0.0)

        @JvmField
        val CAVE_SPIDER = MagicMobType("Cave Spider", 0.0, 0.0)

        @JvmField
        val SILVERFISH = MagicMobType("Silverfish", 0.0, 0.0)

        @JvmField
        val BLAZE = MagicMobType("Blaze", 0.0, 0.0)

        @JvmField
        val MAGMA_CUBE = MagicMobType("Magma Cube", 0.6, 0.6)

        @JvmField
        val ENDER_DRAGON = MagicMobType("Ender Dragon", 0.0, 0.0)

        @JvmField
        val WITHER = MagicMobType("Wither", 0.0, 0.0)

        @JvmField
        val BAT = MagicMobType("Bat", 0.0, 0.0)

        @JvmField
        val WITCH = MagicMobType("Witch", 0.0, 0.0)

        @JvmField
        val ENDERMITE = MagicMobType("Endermite", 0.4, 0.3)

        @JvmField
        val GUARDIAN = MagicMobType("Guardian", 0.85, 0.85)

        @JvmField
        val PIG = MagicMobType("Pig", 0.9, 0.9)

        @JvmField
        val SHEEP = MagicMobType("Sheep", 0.6, 1.3)

        @JvmField
        val COW = MagicMobType("Cow", 0.9, 1.3)

        @JvmField
        val CHICKEN = MagicMobType("Chicken", 0.3, 0.4)

        @JvmField
        val SQUID = MagicMobType("Squid", 0.95, 0.95)

        @JvmField
        val WOLF = MagicMobType("Wolf", 0.6, 1.8)

        @JvmField
        val MOOSHROOM = MagicMobType("Mooshroom", 0.0, 0.0)

        @JvmField
        val SNOWMAN = MagicMobType("Snowman", 0.0, 0.0)

        @JvmField
        val OCELOT = MagicMobType("Ocelot", 0.0, 0.0)

        @JvmField
        val IRON_GOLEM = MagicMobType("Iron Golem", 0.0, 0.0)

        @JvmField
        val HORSE = MagicMobType("Horse", 0.0, 0.0)

        @JvmField
        val RABBIT = MagicMobType("Rabbit", 0.6, 0.7)

        @JvmField
        val VILLAGER = MagicMobType("Villager", 0.0, 0.0)

        @JvmField
        @Suppress("UNUSED")
        val PLAYER = MagicMobType("Player", 0.0, 0.0)
    }

}
