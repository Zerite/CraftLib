package dev.zerite.craftlib.schematic

import dev.zerite.craftlib.commons.world.Block
import dev.zerite.craftlib.nbt.impl.CompoundTag

/**
 * Houses all the valid schematic values which have either been parsed from a
 * schematic file or created programmatically.
 *
 * @author Koding
 * @since  0.1.3
 */
data class Schematic @JvmOverloads constructor(
    var width: Short,
    var height: Short,
    var length: Short,
    var materials: SchematicMaterials,
    var blocks: Array<Block> = Array(width * height * length) { Block.AIR },
    var entities: List<CompoundTag> = arrayListOf(),
    var tileEntities: List<CompoundTag> = arrayListOf(),
    var originX: Int = 0,
    var originY: Int = 0,
    var originZ: Int = 0,
    var offsetX: Int = 0,
    var offsetY: Int = 0,
    var offsetZ: Int = 0
) {

    companion object {
        /**
         * Calculates the array index with the given coordinates
         * and sizing values.
         *
         * @param  x        The x position to calculate with.
         * @param  y        The y position to calculate with.
         * @param  z        The z position to calculate with.
         * @param  length   The length of the schematic.
         * @param  width    The width of the schematic.
         *
         * @author Koding
         * @since  0.1.3
         */
        fun index(x: Int, y: Int, z: Int, length: Short, width: Short) = (y * length + z) * width + x
    }

    /**
     * Finds the array index with the given coordinates whilst passing
     * in this schematics {@code length} and {@code width} values automatically.
     *
     * @param  x        The x position to calculate with.
     * @param  y        The y position to calculate with.
     * @param  z        The z position to calculate with.
     *
     * @author Koding
     * @since  0.1.3
     */
    @Suppress("UNUSED")
    fun index(x: Int, y: Int, z: Int) =
        index(x, y, z, length, width)

    /**
     * Retrieves a block at the given coordinate, otherwise returning
     * the default air block. All positions are relative to this schematic and
     * begin from 0 to the maximum of that axis.
     *
     * @param  x        The x position which we are looking up.
     * @param  y        The y position which we are looking up.
     * @param  z        The z position which we are looking up.
     *
     * @author Koding
     * @since  0.1.3
     */
    operator fun get(x: Int, y: Int, z: Int) = blocks.getOrElse(index(x, y, z)) { Block.AIR }

    /**
     * Sets a block at the given coordinate. All positions are relative
     * to this schematic and begin from 0 to the maximum of that axis.
     *
     * @param  x        The x position which we are writing to.
     * @param  y        The y position which we are writing to.
     * @param  z        The z position which we are writing to.
     * @param  value    The block to write in this schematic.
     *
     * @author Koding
     * @since  0.1.3
     */
    operator fun set(x: Int, y: Int, z: Int, value: Block) {
        blocks[index(x, y, z)] = value
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Schematic

        if (width != other.width) return false
        if (height != other.height) return false
        if (length != other.length) return false
        if (materials != other.materials) return false
        if (!blocks.contentEquals(other.blocks)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = width.toInt()
        result = 31 * result + height
        result = 31 * result + length
        result = 31 * result + materials.hashCode()
        result = 31 * result + blocks.contentHashCode()
        return result
    }
}

/**
 * Stores the possible value for the schematic materials string.
 *
 * @author Koding
 * @since  0.1.3
 */
@Suppress("UNUSED")
enum class SchematicMaterials(val key: String) {
    ALPHA("Alpha"),
    CLASSIC("Classic"),
    POCKET("Pocket");

    companion object {
        /**
         * Finds a schematic material with the given key.
         *
         * @param  key        The key to find a material with.
         * @author Koding
         * @since  0.1.3
         */
        operator fun get(key: String) = values().first { it.key == key }
    }
}
