package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
import dev.zerite.craftlib.protocol.data.registry.LazyRegistryDelegate
import dev.zerite.craftlib.protocol.data.registry.MagicRegistry
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry

/**
 * Stores the object type values for the spawn object packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class MagicObject(override val name: String, val boundingXZ: Double, val boundingY: Double) : RegistryEntry(name) {

    @Suppress("UNUSED")
    companion object : IMinecraftRegistry<MagicObject> by LazyRegistryDelegate({ MagicRegistry.objects }) {
        val BOAT = MagicObject("Boat", 1.5, 0.6)
        val ITEM_STACK = MagicObject("Dropped Item", 0.25, 0.25)
        val MINECART = MagicObject("Minecart", 0.98, 0.7)
        val MINECART_STORAGE = MagicObject("Storage Minecart", 0.98, 0.7)
        val MINECART_POWERED = MagicObject("Powered Minecart", 0.98, 0.7)
        val ACTIVATED_TNT = MagicObject("Primed TNT", 0.98, 0.98)
        val ENDER_CRYSTAL = MagicObject("Ender Crystal", 2.0, 2.0)
        val ARROW = MagicObject("Arrow", 0.5, 0.5)
        val SNOWBALL = MagicObject("Snowball", 0.25, 0.25)
        val EGG = MagicObject("Egg", 0.25, 0.25)
        val FIREBALL = MagicObject("Fireball", 1.0, 1.0)
        val FIRE_CHARGE = MagicObject("Fire Charge", 0.3125, 0.3125)
        val THROWN_ENDER_PEARL = MagicObject("Ender Pearl", 0.25, 0.25)
        val WITHER_SKULL = MagicObject("Wither Skull", 0.3125, 0.3125)
        val FALLING_OBJECT = MagicObject("Falling Block", 0.98, 0.98)
        val ITEM_FRAME = MagicObject("Item Frame", -1.0, -1.0)
        val EYE_OF_ENDER = MagicObject("Eye of Ender", 0.25, 0.25)
        val THROWN_POTION = MagicObject("Potion", 0.25, 0.25)
        val FALLING_DRAGON_EGG = MagicObject("Falling Dragon Egg", 0.98, 0.98)
        val THROWN_EXP_BOTTLE = MagicObject("Experience Bottle", 0.25, 0.25)
        val FISHING_FLOAT = MagicObject("Fishing Float", 0.25, 0.25)
    }

}
