package dev.zerite.craftlib.nbt.impl

import org.junit.Assert
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Tests that the byte array tag is working correctly.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ByteArrayTagTest : TagImplTest<ByteArrayTag>() {
    @Test
    fun `Test reading empty array`() = assertEquals(emptyTag, ByteArrayTag.read(byteArrayOf(0, 0, 0, 0)))

    @Test
    fun `Test getting size from tag`() {
        assertEquals(0, emptyTag.size)
        assertEquals(5, filledTag.size)
    }

    @Test
    fun `Test getting byte from index`() {
        assertFailsWith(ArrayIndexOutOfBoundsException::class) { emptyTag[0] }
        assertEquals(-127, filledTag[2])
    }

    @Test
    fun `Test setting byte at index`() {
        assertFailsWith(ArrayIndexOutOfBoundsException::class) { emptyTag[0] = 45 }
        val newTag = ByteArrayTag(byteArrayOf(45))
        assertEquals(45, newTag[0])
        newTag[0] = 46
        assertEquals(46, newTag[0])
    }

    @Test
    fun `Test toString`() {
        assertEquals("[]", emptyTag.toString())
        assertEquals("[123, -23, -127, 127, 34]", filledTag.toString())
    }

    @Test
    fun `Test hashCode`() {
        assertEquals(emptyTag.value.contentHashCode(), emptyTag.hashCode())
        assertEquals(filledTag.value.contentHashCode(), filledTag.hashCode())
    }

    @Test
    fun `Test reading array`() = assertEquals(
        filledTag, ByteArrayTag.read(
            byteArrayOf(
                0, 0, 0, 5,
                123, -23, -127, 127, 34
            )
        )
    )

    @Test
    fun `Test writing empty array`() = Assert.assertArrayEquals(byteArrayOf(0, 0, 0, 0), emptyTag.write())

    @Test
    fun `Test writing array`() = Assert.assertArrayEquals(byteArrayOf(0, 0, 0, 5, 123, -23, -127, 127, 34), filledTag.write())

    companion object {
        private val emptyTag = ByteArrayTag(byteArrayOf())
        private val filledTag = ByteArrayTag(byteArrayOf(123, -23, -127, 127, 34))
    }
}
