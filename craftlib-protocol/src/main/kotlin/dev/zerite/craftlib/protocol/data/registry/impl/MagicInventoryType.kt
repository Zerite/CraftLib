package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Contains magic constants for the different types of inventory windows
 * which can be opened.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class MagicInventoryType(name: String) : RegistryEntry(name) {
    @Suppress("UNUSED")
    companion object {
        val CHEST = MagicInventoryType("Chest")
        val WORKBENCH = MagicInventoryType("Crafting Table")
        val FURNACE = MagicInventoryType("Furnace")
        val DISPENSER = MagicInventoryType("Dispenser")
        val ENCHANTMENT_TABLE = MagicInventoryType("Enchantment Table")
        val BREWING_STAND = MagicInventoryType("Brewing Stand")
        val VILLAGER = MagicInventoryType("Villager")
        val BEACON = MagicInventoryType("Beacon")
        val ANVIL = MagicInventoryType("Anvil")
        val HOPPER = MagicInventoryType("Hopper")
        val DROPPER = MagicInventoryType("Dropper")
        val HORSE = MagicInventoryType("Horse")
    }
}

/**
 * Easy accessor for the inventory type registry.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
object InventoryType : IMinecraftRegistry<MagicInventoryType> by MagicRegistry.inventoryType