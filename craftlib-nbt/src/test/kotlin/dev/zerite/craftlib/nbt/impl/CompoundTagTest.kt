package dev.zerite.craftlib.nbt.impl

import dev.zerite.craftlib.nbt.NBTTag
import dev.zerite.craftlib.nbt.compound
import org.junit.Assert
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

/**
 * Tests that the compound tag is working correctly.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class CompoundTagTest : TagImplTest<CompoundTag>() {
    @Test
    fun `Test getting size`() {
        assertEquals(0, emptyCompound.size)
        assertEquals(1, filledCompound.size)
    }

    @Test
    fun `Test adding values`() {
        val compound = CompoundTag()
        assertNull(compound["new entry"])
        compound["new entry"] = StringTag("new value")
        @Suppress("RemoveExplicitTypeArguments") // the IDE is wrong here
        assertNotNull(compound.get<NBTTag>("new entry"))
    }

    @Test
    fun `Test overwriting values`() {
        val compound = CompoundTag()
        compound["new entry"] = StringTag("new value")
        compound["new entry"] = IntTag(3)
        @Suppress("RemoveExplicitTypeArguments") // the IDE is wrong here
        assertEquals(IntTag(3), compound.get<IntTag>("new entry"))
        assertEquals(1, compound.size)
    }

    @Test
    fun `Test reading compound tag`() {
        assertEquals(emptyCompound, CompoundTag.read(byteArrayOf(0)))
        assertEquals<NBTTag>(
            filledCompound, CompoundTag.read(
                byteArrayOf(
                    8, // TAG_String
                    0, 10, // Name length
                    116, 101, 115, 116, 32, 101, 110, 116, 114, 121, // Name
                    0, 10, // Value length
                    116, 101, 115, 116, 32, 118, 97, 108, 117, 101, // Value
                    0 // TAG_End
                )
            )
        )
    }

    @Test
    fun `Test writing compound tag`() {
        Assert.assertArrayEquals(byteArrayOf(0), emptyCompound.write())
        Assert.assertArrayEquals(
            byteArrayOf(
                8, // TAG_String
                0, 10, // Name length
                116, 101, 115, 116, 32, 101, 110, 116, 114, 121, // Name
                0, 10, // Value length
                116, 101, 115, 116, 32, 118, 97, 108, 117, 101, // Value
                0 // TAG_End
            ), filledCompound.write()
        )
    }

    companion object {
        private val emptyCompound = CompoundTag()
        private val filledCompound = compound {
            "test entry" to "test value"
        }
    }
}
