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
open class MagicObject(
    override val name: String,
    @Suppress("UNUSED") val boundingXZ: Double,
    @Suppress("UNUSED") val boundingY: Double
) : RegistryEntry(name) {

    @Suppress("UNUSED")
    companion object : IMinecraftRegistry<MagicObject> by LazyRegistryDelegate({ MagicRegistry.objects }) {
        @JvmField
        val BOAT = MagicObject("Boat", 1.5, 0.6)

        @JvmField
        val ITEM_STACK = MagicObject("Dropped Item", 0.25, 0.25)

        @JvmField
        val MINECART = MagicObject("Minecart", 0.98, 0.7)

        @JvmField
        val MINECART_STORAGE = MagicObject("Storage Minecart", 0.98, 0.7)

        @JvmField
        val MINECART_POWERED = MagicObject("Powered Minecart", 0.98, 0.7)

        @JvmField
        val ACTIVATED_TNT = MagicObject("Primed TNT", 0.98, 0.98)

        @JvmField
        val ENDER_CRYSTAL = MagicObject("Ender Crystal", 2.0, 2.0)

        @JvmField
        val ARROW = MagicObject("Arrow", 0.5, 0.5)

        @JvmField
        val SNOWBALL = MagicObject("Snowball", 0.25, 0.25)

        @JvmField
        val EGG = MagicObject("Egg", 0.25, 0.25)

        @JvmField
        val FIREBALL = MagicObject("Fireball", 1.0, 1.0)

        @JvmField
        val FIRE_CHARGE = MagicObject("Fire Charge", 0.3125, 0.3125)

        @JvmField
        val THROWN_ENDER_PEARL = MagicObject("Ender Pearl", 0.25, 0.25)

        @JvmField
        val WITHER_SKULL = MagicObject("Wither Skull", 0.3125, 0.3125)

        @JvmField
        val FALLING_OBJECT = MagicObject("Falling Block", 0.98, 0.98)

        @JvmField
        val ITEM_FRAME = MagicObject("Item Frame", -1.0, -1.0)

        @JvmField
        val EYE_OF_ENDER = MagicObject("Eye of Ender", 0.25, 0.25)

        @JvmField
        val THROWN_POTION = MagicObject("Potion", 0.25, 0.25)

        @JvmField
        val FALLING_DRAGON_EGG = MagicObject("Falling Dragon Egg", 0.98, 0.98)

        @JvmField
        val THROWN_EXP_BOTTLE = MagicObject("Experience Bottle", 0.25, 0.25)

        @JvmField
        val FIREWORK_ROCKET = MagicObject("Firework Rocket", 0.25, 0.25)

        @JvmField
        val LEASH_KNOT = MagicObject("Leash Knot", 0.5, 0.5)

        @JvmField
        val ARMOR_STAND = MagicObject("Armor Stand", 0.5, 2.0)

        @JvmField
        val FISHING_FLOAT = MagicObject("Fishing Float", 0.25, 0.25)
    }

}
