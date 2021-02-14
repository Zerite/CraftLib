package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Stores the valid entity status values for the packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
open class MagicEntityStatus(name: String) : RegistryEntry(name) {

    @Suppress("UNUSED")
    companion object : IMinecraftRegistry<MagicEntityStatus> by LazyRegistryDelegate({ MagicRegistry.entityStatus }) {
        @JvmField
        val ENTITY_HURT = MagicEntityStatus("Entity Damage")

        @JvmField
        val ENTITY_DEAD = MagicEntityStatus("Entity Death")

        @JvmField
        val WOLF_TAMING = MagicEntityStatus("Wolf Taming")

        @JvmField
        val WOLF_TAMED = MagicEntityStatus("Wolf Tamed")

        @JvmField
        val WOLF_SHAKING_WATER = MagicEntityStatus("Wolf Shaking Water")

        @JvmField
        val EATING_ACCEPTED = MagicEntityStatus("Eating Success")

        @JvmField
        val SHEEP_EATING_GRASS = MagicEntityStatus("Sheep Eating Grass")

        @JvmField
        val IRON_GOLEM_ROSE = MagicEntityStatus("Iron Golem Giving Rose")

        @JvmField
        val VILLAGER_HEARTS = MagicEntityStatus("Villager Hearts")

        @JvmField
        val VILLAGER_ANGRY = MagicEntityStatus("Villager Angry")

        @JvmField
        val VILLAGER_HAPPY = MagicEntityStatus("Villager Happy")

        @JvmField
        val WITCH_MAGIC = MagicEntityStatus("Witch Magic Particles")

        @JvmField
        val CONVERT_ZOMBIE_VILLAGER = MagicEntityStatus("Convert Zombie Villager")

        @JvmField
        val FIREWORK_EXPLODE = MagicEntityStatus("Explode Firework")

        @JvmField
        val OP_PERMISSION_LEVEL_0 = MagicEntityStatus("OP Permission Level 0")

        @JvmField
        val OP_PERMISSION_LEVEL_1 = MagicEntityStatus("OP Permission Level 1")

        @JvmField
        val OP_PERMISSION_LEVEL_2 = MagicEntityStatus("OP Permission Level 2")

        @JvmField
        val OP_PERMISSION_LEVEL_3 = MagicEntityStatus("OP Permission Level 3")

        @JvmField
        val OP_PERMISSION_LEVEL_4 = MagicEntityStatus("OP Permission Level 4")
    }

}
