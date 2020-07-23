@file:JvmName("SchematicUtil")

package dev.zerite.craftlib.schematic

import dev.zerite.craftlib.commons.io.ByteNibbleArray
import dev.zerite.craftlib.commons.world.Block
import dev.zerite.craftlib.nbt.compound
import dev.zerite.craftlib.nbt.impl.*
import dev.zerite.craftlib.nbt.readTag
import dev.zerite.craftlib.nbt.write
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.io.InputStream
import java.io.OutputStream

/**
 * Utility relating to IO operations which involve the schematic format.
 *
 * @author Koding
 * @since  0.1.3
 */
@Suppress("UNUSED")
object SchematicIO {

    /**
     * Parses a schematic from the provided compound tag.
     *
     * @param  tag         The tag input data we're parsing.
     * @author Koding
     * @since  0.1.3
     */
    @OptIn(ExperimentalUnsignedTypes::class)
    @JvmStatic
    fun readTag(tag: CompoundTag) = tag.let {
        val rawBlocks = it["Blocks", ByteArrayTag(ByteArray(0))].value
        val addBlocks = ByteNibbleArray(it["AddBlocks", ByteArrayTag(ByteArray(0))].value, flipped = true)
        val data = it["Data", ByteArrayTag(ByteArray(0))].value

        val width = it["Width", ShortTag(0)].value
        val length = it["Length", ShortTag(0)].value
        val height = it["Height", ShortTag(0)].value

        val blocks = Array(rawBlocks.size) { Block.AIR }

        for (i in blocks.indices) {
            val id = rawBlocks[i]
            val add = addBlocks[i, 0]
            val meta = if (data.size <= i) 0 else data[i]
            blocks[i] = Block(
                (add shl 8) or id.toUByte().toInt(),
                meta.toInt() and 0xF
            )
        }

        Schematic(
            width,
            height,
            length,
            SchematicMaterials[it["Materials", StringTag("Alpha")].value],
            blocks = blocks,
            entities = it["Entities", ListTag<CompoundTag>()].value,
            tileEntities = it["TileEntities", ListTag<CompoundTag>()].value,
            originX = it["WEOriginX", IntTag(0)].value,
            originY = it["WEOriginY", IntTag(0)].value,
            originZ = it["WEOriginZ", IntTag(0)].value,
            offsetX = it["WEOffsetX", IntTag(0)].value,
            offsetY = it["WEOffsetY", IntTag(0)].value,
            offsetZ = it["WEOffsetZ", IntTag(0)].value
        )
    }

    /**
     * Reads a NBT compound from the provided input stream and parses it
     * into a schematic class.
     *
     * @param  input         The input stream which we are reading the compound from.
     * @param  compressed    Whether the input stream contains compressed NBT data.
     *
     * @author Koding
     * @since  0.1.3
     */
    @Suppress("UNUSED")
    suspend fun read(input: InputStream, compressed: Boolean = true) =
        readTag(input.readTag(compressed).tag)

    /**
     * Reads a NBT compound from the provided input stream and parses it
     * into a schematic class, returning a future with the result.
     *
     * @param  input         The input stream which we are reading the compound from.
     * @param  compressed    Whether the input stream contains compressed NBT data.
     *
     * @author Koding
     * @since  0.1.3
     */
    @JvmStatic
    fun readFuture(input: InputStream, compressed: Boolean = true) =
        GlobalScope.future { read(input, compressed) }

    /**
     * Writes a schematic into a NBT tag.
     *
     * @param  schematic      The schematic we're writing to a tag.
     * @author Koding
     * @since  0.1.3
     */
    @JvmStatic
    @Suppress("UNUSED")
    fun writeTag(schematic: Schematic): CompoundTag {
        val size = schematic.width * schematic.height * schematic.length
        val blocks = ByteArray(size)
        val addBlocks = ByteNibbleArray(ByteArray(size / 2), flipped = true)
        val data = ByteArray(size)
        var add = false

        schematic.blocks.forEachIndexed { index, it ->
            blocks[index] = (it.id and 0xFF).toByte()
            data[index] = (it.metadata and 0xF).toByte()

            ((it.id shr 8) and 0xF).takeIf { it != 0 }?.let {
                add = true
                addBlocks[index] = it
            }
        }

        return compound {
            "Width" to schematic.width
            "Height" to schematic.height
            "Length" to schematic.length
            "Materials" to schematic.materials.key

            "Blocks" to blocks
            "Data" to data
            if (add) "AddBlocks" to addBlocks.data

            "Entities" to schematic.entities
            "TileEntities" to schematic.tileEntities

            "WEOriginX" to schematic.originX
            "WEOriginY" to schematic.originY
            "WEOriginZ" to schematic.originZ
            "WEOffsetX" to schematic.offsetX
            "WEOffsetY" to schematic.offsetY
            "WEOffsetZ" to schematic.offsetZ
        }
    }

    /**
     * Writes a schematic into the provided {@code OutputStream}, optionally
     * compressing it with GZIP.
     *
     * @param  schematic       The schematic we're writing.
     * @param  output          The output stream which we're writing to.
     * @param  compressed      Whether we should compress the written schematic.
     *
     * @author Koding
     * @since  0.1.3
     */
    @Suppress("UNUSED")
    suspend fun write(schematic: Schematic, output: OutputStream, compressed: Boolean = true) =
        writeTag(schematic).write(output, compressed)

    /**
     * Writes a schematic into the provided {@code OutputStream}, optionally
     * compressing it with GZIP. Returns a {@code Future} for handling
     * the completion.
     *
     * @param  schematic       The schematic we're writing.
     * @param  output          The output stream which we're writing to.
     * @param  compressed      Whether we should compress the written schematic.
     *
     * @author Koding
     * @since  0.1.3
     */
    @JvmStatic
    fun writeFuture(schematic: Schematic, output: OutputStream, compressed: Boolean = true) =
        GlobalScope.future { write(schematic, output, compressed) }

}

/**
 * Reads a schematic from the compound tag.
 *
 * @author Koding
 * @since  0.1.3
 */
@Suppress("UNUSED")
fun CompoundTag.readSchematic() = SchematicIO.readTag(this)

/**
 * Converts this schematic to a NBT compound tag.
 */
@Suppress("UNUSED")
val Schematic.compound
    get() = SchematicIO.writeTag(this)
