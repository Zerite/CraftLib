package dev.zerite.craftlib.protocol.data.world

/**
 * Simple utility class which allows you to set any coordinate in a world with
 * a block and automatically update the chunks map.
 *
 * @author Koding
 * @since  0.1.3
 */
@Suppress("UNUSED")
class World @JvmOverloads constructor(var chunks: MutableMap<Pair<Int, Int>, ChunkColumn> = hashMapOf()) {

    /**
     * Sets a block a the given position in the world.
     *
     * @param  x         The x position to set the block at.
     * @param  y         The y position to set the block at.
     * @param  z         The z position to set the block at.
     * @param  block     The new block for the position to have set.
     *
     * @author Koding
     * @since  0.1.3
     */
    operator fun set(x: Int, y: Int, z: Int, block: Block) {
        val chunkX = x shr 4
        val chunkZ = z shr 4
        val column = chunks.getOrPut(chunkX to chunkZ) { ChunkColumn(chunkX, chunkZ) }
        column[x and 0xF, y, z and 0xF] = block
    }

    /**
     * Gets a block at the given position or null if there
     * is no block present.
     *
     * @param  x         The x position to set the block at.
     * @param  y         The y position to set the block at.
     * @param  z         The z position to set the block at.
     *
     * @author Koding
     * @since  0.1.3
     */
    operator fun get(x: Int, y: Int, z: Int): Block? {
        val chunkX = x shr 4
        val chunkZ = z shr 4
        val column = chunks[chunkX to chunkZ] ?: return null
        return column[x and 0xF, y, z and 0xF]
    }

}
