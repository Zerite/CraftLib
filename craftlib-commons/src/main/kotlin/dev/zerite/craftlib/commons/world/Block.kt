package dev.zerite.craftlib.commons.world

/**
 * Stores information about a single block in the world
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class Block(
    val id: Int,
    val metadata: Int,
    val blockLight: Int = 0,
    val skyLight: Int = 0
) {

    companion object {
        /**
         * Constant value for an air block.
         */
        @JvmField
        val AIR = Block(0, 0)
    }

    /**
     * The location of this block.
     */
    lateinit var location: BlockLocation
}

/**
 * Vector for storing a 3D block location.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class BlockLocation(
    val x: Int,
    val y: Int,
    val z: Int
)
