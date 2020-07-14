package dev.zerite.craftlib.protocol.data.world

import dev.zerite.craftlib.protocol.util.ext.trim

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
            data: ByteArray,
            metadata: ChunkMetadata,
            hasSkyLight: Boolean = true,
            readBiomes: Boolean = metadata.biomes
        ): ChunkColumn {
            var marker = 0
            return ChunkColumn(
                metadata.chunkX,
                metadata.chunkZ,
                Array(16) { ChunkArrays() }.apply {
                    val masked = (0..15).count { metadata.primaryBitmap and (1 shl it) != 0 }
                    val maskedAdd = (0..15).count { metadata.addBitmap and (1 shl it) != 0 }
                    var i = 0

                    forEachIndexed { it, chunk ->
                        if (metadata.primaryBitmap and (1 shl it) == 0) {
                            chunk.blockTypes = ByteArray(FULL_BYTE_SIZE)
                            chunk.metadata = ByteNibbleArray(ByteArray(HALF_BYTE_SIZE))
                            chunk.blockLight = ByteNibbleArray(ByteArray(HALF_BYTE_SIZE))
                            chunk.skyLight = ByteNibbleArray(ByteArray(HALF_BYTE_SIZE))
                            chunk.addArray = ByteNibbleArray(ByteArray(HALF_BYTE_SIZE))
                            return@forEachIndexed
                        }

                        var bytesPosition = i * 4096
                        chunk.blockTypes = data.copyOfRange(bytesPosition, bytesPosition + 4096)
                        bytesPosition += ((masked - i) * 4096) + (i * 2048)
                        chunk.metadata = ByteNibbleArray(data.copyOfRange(bytesPosition, bytesPosition + 2048))
                        bytesPosition += masked * 2048
                        chunk.blockLight = ByteNibbleArray(data.copyOfRange(bytesPosition, bytesPosition + 2048))
                        bytesPosition += masked * 2048
                        marker += 8192

                        chunk.skyLight = ByteNibbleArray(
                            if (hasSkyLight) data.copyOfRange(
                                bytesPosition,
                                bytesPosition + 2048
                            ).apply {
                                bytesPosition += maskedAdd * 2048
                                marker += 2048
                            } else ByteArray(0)
                        )

                        chunk.addArray = ByteNibbleArray(
                            if (metadata.addBitmap and (1 shl it) != 0) data.copyOfRange(
                                bytesPosition,
                                bytesPosition + 2048
                            ).apply {
                                bytesPosition += masked * 2048
                                marker += 2048
                            } else ByteArray(0)
                        )

                        i++
                    }
                }.map {
                    val blockData = arrayOfNulls<Block?>(16 * 16 * 16)

                    for (y in 0 until 16) {
                        for (z in 0 until 16) {
                            for (x in 0 until 16) {
                                val index = Chunk.index(x, y, z)
                                if (index > it.blockTypes.size || it.blockTypes.isEmpty()) continue

                                val type = (it.blockTypes[index].toInt() and 255) or (it.addArray[index, 0] shl 8)
                                val meta = it.metadata[index, 0]
                                val light = it.blockLight[index, 0]
                                val sky = it.skyLight[index, 0]

                                blockData[index] =
                                    if (type == 0 && meta == 0 && light == 0 && sky == 0) null
                                    else Block(type, meta, light, sky).apply { location = BlockLocation(x, y, z) }
                            }
                        }
                    }

                    Chunk(blockData)
                }.toTypedArray(),
                if (readBiomes) data.copyOfRange(marker, marker + 256) else ByteArray(16 * 16)
            )
        }

        /**
         * Writes a chunk column to the buffer whilst accounting for the
         * provided metadata values.
         *
         * @param  column        The chunk column we are writing.
         * @param  hasSkyLight   Whether this chunk data should include skylight.
         * @param  writeBiomes   Whether we should write biome data.
         *
         * @author Koding
         * @since  0.1.0-SNAPSHOT
         */
        fun write(
            column: ChunkColumn,
            hasSkyLight: Boolean = true,
            writeBiomes: Boolean = true
        ): ChunkWriteOutput {
            var primaryBitmap = 0
            var addBitmap = 0

            val output = ByteArray(196864)
            var outputPos = 0

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

                                blockTypes[index] = (block.id and 255).toByte()
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
                val masked = (0..15).count { primaryBitmap and (1 shl it) != 0 }
                var i = 0

                for (it in 0..15) {
                    val chunk = this[it]
                    if (primaryBitmap and (1 shl it) == 0) continue

                    var bytesPosition = i * 4096
                    System.arraycopy(chunk.blockTypes, 0, output, bytesPosition, 4096)
                    bytesPosition += ((masked - i) * 4096) + (i * 2048)
                    System.arraycopy(chunk.metadata.data, 0, output, bytesPosition, chunk.metadata.data.size)
                    bytesPosition += masked * 2048
                    System.arraycopy(chunk.blockLight.data, 0, output, bytesPosition, chunk.blockLight.data.size)
                    bytesPosition += masked * 2048

                    outputPos += 8192
                    if (hasSkyLight) {
                        System.arraycopy(chunk.skyLight.data, 0, output, bytesPosition, 2048)
                        bytesPosition += masked * 2048
                        outputPos += 2048
                    }

                    if (addBitmap and (1 shl it) != 0) {
                        System.arraycopy(chunk.skyLight.data, 0, output, bytesPosition, 2048)
                        bytesPosition += masked * 2048
                        outputPos += 2048
                    }

                    i++
                }
            }

            if (writeBiomes) {
                System.arraycopy(column.biomes, 0, output, outputPos, 256)
                outputPos += 256
            }

            return ChunkWriteOutput(primaryBitmap, addBitmap, output.trim(outputPos))
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
    val addBitmask: Int,
    val output: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChunkWriteOutput

        if (primaryBitmask != other.primaryBitmask) return false
        if (addBitmask != other.addBitmask) return false
        if (!output.contentEquals(other.output)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = primaryBitmask
        result = 31 * result + addBitmask
        result = 31 * result + output.contentHashCode()
        return result
    }
}

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
