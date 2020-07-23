package dev.zerite.craftlib.schematic

import dev.zerite.craftlib.commons.world.Block
import dev.zerite.craftlib.nbt.named
import dev.zerite.craftlib.nbt.readTag
import dev.zerite.craftlib.nbt.write
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayOutputStream
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests the IO operations relating to schematics.
 *
 * @author Koding
 * @since  0.1.3
 */
class SchematicIOTest {

    /**
     * The example schematic we're comparing against.
     */
    private val schematic = Schematic(4, 4, 4, SchematicMaterials.ALPHA).apply {
        this[0, 2, 3] = Block(10, 10)
        this[1, 3, 2] = Block(4, 6)
    }

    @Test
    fun `Test IO`() = runBlocking {
        val output = ByteArrayOutputStream()
        schematic.compound.named("Schematic").write(output)
        val read = output.toByteArray().inputStream().readTag().tag.readSchematic()
        assertEquals(schematic, read)
    }

    @Test
    fun `Test Compressed IO`() = runBlocking {
        val output = ByteArrayOutputStream()
        schematic.compound.named("Schematic").write(output, compressed = true)
        val read = output.toByteArray().inputStream().readTag(compressed = true).tag.readSchematic()
        assertEquals(schematic, read)
    }

}
