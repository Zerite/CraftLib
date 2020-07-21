package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Contains magic constants for the different types of inventory windows
 * which can be opened.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
open class MagicInventoryType(name: String) : RegistryEntry(name) {
    @Suppress("UNUSED")
    companion object : IMinecraftRegistry<MagicInventoryType> by LazyRegistryDelegate({ MagicRegistry.inventoryType }) {
        @JvmField
        val CHEST = MagicInventoryType("Chest")

        @JvmField
        val WORKBENCH = MagicInventoryType("Crafting Table")

        @JvmField
        val FURNACE = MagicInventoryType("Furnace")

        @JvmField
        val DISPENSER = MagicInventoryType("Dispenser")

        @JvmField
        val ENCHANTMENT_TABLE = MagicInventoryType("Enchantment Table")

        @JvmField
        val BREWING_STAND = MagicInventoryType("Brewing Stand")

        @JvmField
        val VILLAGER = MagicInventoryType("Villager")

        @JvmField
        val BEACON = MagicInventoryType("Beacon")

        @JvmField
        val ANVIL = MagicInventoryType("Anvil")

        @JvmField
        val HOPPER = MagicInventoryType("Hopper")

        @JvmField
        val DROPPER = MagicInventoryType("Dropper")

        @JvmField
        val HORSE = MagicInventoryType("Horse")
    }
}
