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
        val ENTITY_HURT = MagicEntityStatus("Entity Damage")
        val ENTITY_DEAD = MagicEntityStatus("Entity Death")
        val WOLF_TAMING = MagicEntityStatus("Wolf Taming")
        val WOLF_TAMED = MagicEntityStatus("Wolf Tamed")
        val WOLF_SHAKING_WATER = MagicEntityStatus("Wolf Shaking Water")
        val EATING_ACCEPTED = MagicEntityStatus("Eating Success")
        val SHEEP_EATING_GRASS = MagicEntityStatus("Sheep Eating Grass")
        val IRON_GOLEM_ROSE = MagicEntityStatus("Iron Golem Giving Rose")
        val VILLAGER_HEARTS = MagicEntityStatus("Villager Hearts")
        val VILLAGER_ANGRY = MagicEntityStatus("Villager Angry")
        val VILLAGER_HAPPY = MagicEntityStatus("Villager Happy")
        val WITCH_MAGIC = MagicEntityStatus("Witch Magic Particles")
        val CONVERT_ZOMBIE_VILLAGER = MagicEntityStatus("Convert Zombie Villager")
        val FIREWORK_EXPLODE = MagicEntityStatus("Explode Firework")
    }

}
