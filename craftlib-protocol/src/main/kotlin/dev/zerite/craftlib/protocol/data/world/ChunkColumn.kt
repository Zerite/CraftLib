package dev.zerite.craftlib.protocol.data.world

import java.io.DataInput
import java.io.DataOutput

/**
 * Stores a full column of chunks and has functionality to
 * read them from a buffer.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ChunkColumn(val x: Int, val z: Int, val chunks: Array<Chunk>, private var biomes: ByteArray) {

    companion object {
        /**
         * Constant values for the sizes of byte arrays.
         */
        private const val FULL_BYTE_SIZE = 16 * 16 * 16
        private const val HALF_BYTE_SIZE = 16 * 16 * 8

        /**
         * Reads a chunk column from the buffer whilst accounting for the
         * provided metadata values.
         *
         * @param  data          The data buffer which we are reading from.
         * @param  metadata      Metadata about the chunk.
         * @param  hasSkyLight   Whether this chunk data should include skylight.
         * @param  readBiomes    Whether we should read biome data.
         *
         * @author Koding
         * @since  0.1.0-SNAPSHOT
         */
        fun read(
            data: DataInput,
            metadata: ChunkMetadata,
            hasSkyLight: Boolean = true,
            readBiomes: Boolean = metadata.biomes
        ) = ChunkColumn(
            metadata.chunkX,
            metadata.chunkZ,
            Array(16) { ChunkArrays() }.apply {
                readIf(metadata.primaryBitmap, { blockTypes = ByteArray(0) }) {
                    blockTypes = ByteArray(FULL_BYTE_SIZE).apply { data.readFully(this) }
                }
                readIf(metadata.primaryBitmap, { this.metadata = ByteNibbleArray(ByteArray(0)) }) {
                    this.metadata = ByteNibbleArray(ByteArray(HALF_BYTE_SIZE).apply { data.readFully(this) })
                }
                readIf(metadata.primaryBitmap, { blockLight = ByteNibbleArray(ByteArray(0)) }) {
                    blockLight = ByteNibbleArray(ByteArray(HALF_BYTE_SIZE).apply { data.readFully(this) })
                }
                readIf(
                    metadata.primaryBitmap,
                    { skyLight = ByteNibbleArray(ByteArray(0)) },
                    { hasSkyLight }
                ) {
                    skyLight = ByteNibbleArray(ByteArray(HALF_BYTE_SIZE).apply { data.readFully(this) })
                }
                readIf(metadata.addBitmap, { addArray = ByteNibbleArray(ByteArray(0)) }) {
                    addArray = ByteNibbleArray(ByteArray(HALF_BYTE_SIZE).apply { data.readFully(this) })
                }
            }.map {
                val blockData = arrayOfNulls<Block?>(16 * 16 * 16)

                for (y in 0 until 16) {
                    for (z in 0 until 16) {
                        for (x in 0 until 16) {
                            val index = Chunk.index(x, y, z)
                            if (index > it.blockTypes.size || it.blockTypes.isEmpty()) continue

                            val type = it.blockTypes[index].toInt() + it.addArray[index, 0]
                            val meta = it.metadata[index, 0]
                            val light = it.blockLight[index, 0]
                            val sky = it.skyLight[index, 0]

                            blockData[index] =
                                if (type == 0 && meta == 0 && light == 0 && sky == 0) null
                                else Block(
                                    type,
                                    meta,
                                    light,
                                    sky
                                ).apply { location = BlockLocation(x, y, z) }
                        }
                    }
                }

                Chunk(blockData)
            }.toTypedArray(),
            if (readBiomes) ByteArray(16 * 16).apply { data.readFully(this) } else ByteArray(16 * 16)
        )

        /**
         * Writes a chunk column to the buffer whilst accounting for the
         * provided metadata values.
         *
         * @param  data          The data buffer which we are writing to.
         * @param  column        The chunk column we are writing.
         * @param  hasSkyLight   Whether this chunk data should include skylight.
         * @param  writeBiomes   Whether we should write biome data.
         *
         * @author Koding
         * @since  0.1.0-SNAPSHOT
         */
        fun write(
            data: DataOutput,
            column: ChunkColumn,
            hasSkyLight: Boolean = true,
            writeBiomes: Boolean = true
        ): ChunkWriteOutput {
            var primaryBitmap = 0
            var addBitmap = 0

            column.chunks.mapIndexed { i, it ->
                ChunkArrays().apply {
                    blockTypes = ByteArray(FULL_BYTE_SIZE)
                    metadata = ByteNibbleArray(ByteArray(HALF_BYTE_SIZE))
                    blockLight = ByteNibbleArray(ByteArray(HALF_BYTE_SIZE))
                    skyLight = ByteNibbleArray(ByteArray(HALF_BYTE_SIZE))
                    addArray = ByteNibbleArray(ByteArray(HALF_BYTE_SIZE))

                    var foundBlock = false
                    var foundAdd = false

                    for (y in 0 until 16) {
                        for (z in 0 until 16) {
                            for (x in 0 until 16) {
                                val index = Chunk.index(x, y, z)
                                val block = it[x, y, z] ?: continue
                                val add = block.id and 0xF00 shr 8

                                blockTypes[index] = block.id.toByte()
                                metadata[index] = block.metadata
                                blockLight[index] = block.blockLight
                                skyLight[index] = block.skyLight
                                addArray[index] = add

                                if (block.id != 0 || block.metadata != 0) foundBlock = true
                                if (add != 0) foundAdd = true
                            }
                        }
                    }

                    if (foundBlock) primaryBitmap = primaryBitmap or (1 shl i)
                    if (foundAdd) addBitmap = addBitmap or (1 shl i)
                }
            }.toTypedArray().apply {
                writeIf(primaryBitmap) { data.write(blockTypes) }
                writeIf(primaryBitmap) { data.write(metadata.data) }
                writeIf(primaryBitmap) { data.write(blockLight.data) }
                writeIf(primaryBitmap, { hasSkyLight }) { data.write(skyLight.data) }
                writeIf(addBitmap) { data.write(addArray.data) }
            }

            if (writeBiomes) data.write(column.biomes)
            return ChunkWriteOutput(primaryBitmap, addBitmap)
        }

        /**
         * Reads an array of chunk array data into the class if it
         * meets a list of conditions.
         *
         * @param  mask         The mask we check against.
         * @param  default      The fallback value if we should not execute this.
         * @param  check        Any additional checks which we may need.
         * @param  reader       Reads the data and inserts it into the chunk array data.
         *
         * @author Koding
         * @since  0.1.0-SNAPSHOT
         */
        private fun Array<ChunkArrays>.readIf(
            mask: Int,
            default: ChunkArrays.() -> Unit,
            check: Int.() -> Boolean = { true },
            reader: ChunkArrays.() -> Unit
        ) = repeat(size) {
            if (mask and (1 shl it) != 0 && it.check()) reader(this[it])
            else default(this[it])
        }

        /**
         * Writes an array of chunk array data into the class if it
         * meets a list of conditions.
         *
         * @param  mask         The mask we check against.
         * @param  check        Any additional checks which we may need.
         * @param  writer       Writes the data and inserts it into the output.
         *
         * @author Koding
         * @since  0.1.0-SNAPSHOT
         */
        private fun Array<ChunkArrays>.writeIf(
            mask: Int,
            check: Int.() -> Boolean = { true },
            writer: ChunkArrays.() -> Unit
        ) = repeat(size) {
            if (mask and (1 shl it) != 0 && it.check()) writer(this[it])
        }
    }

    init {
        if (biomes.size < 16 * 16)
            biomes = ByteArray(16 * 16) { biomes.getOrNull(it) ?: 0 }
    }

    /**
     * Gets a chunk at the provided index.
     *
     * @param  index           The index of the chunk we're looking for.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun get(index: Int) = chunks[index]

    /**
     * Gets a block in this chunk column and return it.
     *
     * @param  x              The x coordinate in this chunk. Max 16.
     * @param  y              The y coordinate in this chunk. Max 256.
     * @param  z              The z coordinate in this chunk. Max 16.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun get(x: Int, y: Int, z: Int): Block? {
        val chunkY = y / 16
        return this[chunkY][x, y - chunkY * 16, z]
    }

    /**
     * Gets the biome at the specific coordinate within the chunk.
     *
     * @param  x              The x coordinate in this chunk. Max 16.
     * @param  z              The z coordinate in this chunk. Max 16.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @Suppress("UNUSED")
    fun biomeAt(x: Int, z: Int) = biomes[x + 16 * z].toInt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChunkColumn

        if (x != other.x) return false
        if (z != other.z) return false
        if (!chunks.contentEquals(other.chunks)) return false
        if (!biomes.contentEquals(other.biomes)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + z
        result = 31 * result + chunks.contentHashCode()
        result = 31 * result + biomes.contentHashCode()
        return result
    }
}

/**
 * The output data for when a chunk has been written.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ChunkWriteOutput(
    val primaryBitmask: Int,
    val addBitmask: Int
)

/**
 * Stores array data about a chunk which we read.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ChunkArrays {
    lateinit var blockTypes: ByteArray
    lateinit var metadata: ByteNibbleArray
    lateinit var blockLight: ByteNibbleArray
    lateinit var skyLight: ByteNibbleArray
    lateinit var addArray: ByteNibbleArray
}
