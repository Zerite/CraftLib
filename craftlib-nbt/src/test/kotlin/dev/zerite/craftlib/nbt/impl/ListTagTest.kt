package dev.zerite.craftlib.nbt.impl

import dev.zerite.craftlib.nbt.NBTTag
import dev.zerite.craftlib.nbt.list
import org.junit.Assert
import java.io.ByteArrayInputStream
import java.io.DataInputStream
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 * Tests that the list tag is working correctly.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ListTagTest : TagImplTest<ListTag<*>>() {
    @Test
    fun `Test getting size`() {
        assertEquals(0, expectedEmptyList.size)
        assertEquals(3, expectedFilledList.size)
    }

    @Test
    fun `Test adding to new instance`() {
        val newEmpty = ListTag<StringTag>()
        val newFilled = newEmpty + StringTag("test string")
        assertEquals(expectedEmptyList, newEmpty)
        assertNotEquals(expectedEmptyList, newFilled)
        assertNotEquals(newEmpty, newFilled)
        assertEquals(1, newFilled.size)
        assertEquals(StringTag("test string"), newFilled[0])
    }

    @Test
    fun `Test contains`() {
        assert(StringTag("test string 2") in expectedFilledList)
        assert(StringTag("test string 1") in expectedFilledList)
        assert(StringTag("test string 4") !in expectedFilledList)
        assert(StringTag("test string 1") !in expectedEmptyList)
    }

    @Test
    fun `Test toString`() {
        assertEquals(
            """
            0 entries
            {
            }
        """.trimIndent(), expectedEmptyList.toString()
        )
        assertEquals(
            """
            3 entries
            {
              StringTag(None): 'test string 1'
              StringTag(None): 'test string 2'
              StringTag(None): 'test string 3'
            }
        """.trimIndent(), expectedFilledList.toString()
        )
    }

    @Test
    fun `Test adding to same instance`() {
        val newEmpty = ListTag<StringTag>()
        assertEquals(expectedEmptyList, newEmpty)
        assertNotEquals(expectedFilledList, newEmpty)

        newEmpty += StringTag("test string 1")
        newEmpty += StringTag("test string 2")
        newEmpty += StringTag("test string 3")

        assertNotEquals(expectedEmptyList, newEmpty)
        assertEquals(expectedFilledList, newEmpty)
    }

    @Test
    fun `Test reading list tag`() {
        assertEquals<NBTTag>(expectedEmptyList, ListTag.read(DataInputStream(ByteArrayInputStream(byteArrayOf(0, 0, 0, 0, 0)))))
        assertEquals<NBTTag>(expectedEmptyList, ListTag.read(DataInputStream(ByteArrayInputStream(byteArrayOf(-1, 0, 0, 0, 0)))))
        assertEquals<NBTTag>(expectedEmptyList, ListTag.read(DataInputStream(ByteArrayInputStream(byteArrayOf(2, 0, 0, 0, 0)))))
        assertEquals<NBTTag>(
            expectedFilledList, ListTag.read(
                DataInputStream(ByteArrayInputStream(byteArrayOf(
                    8,
                    0, 0, 0, 3,
                    0, 13, 116, 101, 115, 116, 32, 115, 116, 114, 105, 110, 103, 32, 49,
                    0, 13, 116, 101, 115, 116, 32, 115, 116, 114, 105, 110, 103, 32, 50,
                    0, 13, 116, 101, 115, 116, 32, 115, 116, 114, 105, 110, 103, 32, 51
                )))
            )
        )
    }

    @Test
    fun `Test writing list tag`() {
        Assert.assertArrayEquals(byteArrayOf(0, 0, 0, 0, 0), expectedEmptyList.write())
        Assert.assertArrayEquals(
            byteArrayOf(
                8,
                0, 0, 0, 3,
                0, 13, 116, 101, 115, 116, 32, 115, 116, 114, 105, 110, 103, 32, 49,
                0, 13, 116, 101, 115, 116, 32, 115, 116, 114, 105, 110, 103, 32, 50,
                0, 13, 116, 101, 115, 116, 32, 115, 116, 114, 105, 110, 103, 32, 51
            ), expectedFilledList.write()
        )
    }

    companion object {
        private val expectedEmptyList = ListTag<StringTag>()
        private val expectedFilledList = list<StringTag> {
            +"test string 1"
            +"test string 2"
            +"test string 3"
        }
    }
}
