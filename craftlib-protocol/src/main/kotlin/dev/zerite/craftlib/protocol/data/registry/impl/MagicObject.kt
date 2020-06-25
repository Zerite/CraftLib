package dev.zerite.craftlib.protocol.data.registry.impl

import dev.zerite.craftlib.protocol.data.registry.IMinecraftRegistry
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
    companion object {
        val BOAT = MagicObject("BOAT", 1.5, 0.6)
        val ITEM_STACK = MagicObject("ITEM_STACK", 0.25, 0.25)
        val MINECART = MagicObject("MINECART", 0.98, 0.7)
        val MINECART_STORAGE = MagicObject("MINECART_STORAGE", 0.98, 0.7)
        val MINECART_POWERED = MagicObject("MINECART_POWERED", 0.98, 0.7)
        val ACTIVATED_TNT = MagicObject("ACTIVATED_TNT", 0.98, 0.98)
        val ENDER_CRYSTAL = MagicObject("ENDER_CRYSTAL", 2.0, 2.0)
        val ARROW = MagicObject("ARROW", 0.5, 0.5)
        val SNOWBALL = MagicObject("SNOWBALL", 0.25, 0.25)
        val EGG = MagicObject("EGG", 0.25, 0.25)
        val FIREBALL = MagicObject("FIREBALL", 1.0, 1.0)
        val FIRE_CHARGE = MagicObject("FIRE_CHARGE", 0.3125, 0.3125)
        val THROWN_ENDER_PEARL = MagicObject("THROWN_ENDER_PEARL", 0.25, 0.25)
        val WITHER_SKULL = MagicObject("WITHER_SKULL", 0.3125, 0.3125)
        val FALLING_OBJECT = MagicObject("FALLING_OBJECT", 0.98, 0.98)
        val ITEM_FRAME = MagicObject("ITEM_FRAME", -1.0, -1.0)
        val EYE_OF_ENDER = MagicObject("EYE_OF_ENDER", 0.25, 0.25)
        val THROWN_POTION = MagicObject("THROWN_POTION", 0.25, 0.25)
        val FALLING_DRAGON_EGG = MagicObject("FALLING_DRAGON_EGG", 0.98, 0.98)
        val THROWN_EXP_BOTTLE = MagicObject("THROWN_EXP_BOTTLE", 0.25, 0.25)
        val FISHING_FLOAT = MagicObject("FISHING_FLOAT", 0.25, 0.25)
    }

}

/**
 * Easy accessor for the object registry.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
object ObjectType : IMinecraftRegistry<MagicObject> by MagicRegistry.objects