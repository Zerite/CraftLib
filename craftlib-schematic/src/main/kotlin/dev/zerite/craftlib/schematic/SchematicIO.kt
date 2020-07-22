package dev.zerite.craftlib.schematic

import dev.zerite.craftlib.commons.io.ByteNibbleArray
import dev.zerite.craftlib.nbt.NBTIO
import dev.zerite.craftlib.nbt.impl.ByteArrayTag
import dev.zerite.craftlib.nbt.impl.ShortTag
import dev.zerite.craftlib.nbt.impl.StringTag
import dev.zerite.craftlib.schematic.data.Schematic
import dev.zerite.craftlib.schematic.data.SchematicBlock
import dev.zerite.craftlib.schematic.data.SchematicMaterials
import java.io.InputStream

/**
 * Utility relating to IO operations which involve the schematic format.
 *
 * @author Koding
 * @since  0.1.3
 */
@Suppress("UNUSED")
object SchematicIO {

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
    @OptIn(ExperimentalUnsignedTypes::class)
    suspend fun read(input: InputStream, compressed: Boolean = true) =
        (if (compressed) NBTIO.readCompressed(input) else NBTIO.read(input)).tag.let {
            val rawBlocks = it["Blocks", ByteArrayTag(ByteArray(0))].value
            val addBlocks = ByteNibbleArray(it["AddBlocks", ByteArrayTag(ByteArray(0))].value, flipped = true)
            val data = it["Data", ByteArrayTag(ByteArray(0))].value

            val width = it["Width", ShortTag(0)].value
            val length = it["Length", ShortTag(0)].value
            val height = it["Height", ShortTag(0)].value

            val blocks = Array(rawBlocks.size) { SchematicBlock.AIR }

            for (i in blocks.indices) {
                val id = rawBlocks[i]
                val add = addBlocks[i, 0]
                val meta = if (data.size <= i) 0 else data[i]
                blocks[i] = SchematicBlock(
                    (add shl 4) or id.toUByte().toInt(),
                    (meta.toInt() and 0xF).toByte()
                )
            }

            Schematic(
                width,
                height,
                length,
                SchematicMaterials[it["Materials", StringTag("Alpha")].value],
                blocks
            )
        }

}
