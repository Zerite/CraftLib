package dev.zerite.craftlib.nbt.impl

import org.junit.Assert
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Tests that the int array tag is working correctly.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class IntArrayTagTest : TagImplTest<IntArrayTag>() {
    @Test
    fun `Test reading empty array`() = assertEquals(emptyTag, IntArrayTag.read(byteArrayOf(0, 0, 0, 0)))

    @Test
    fun `Test getting size from tag`() {
        assertEquals(0, emptyTag.size)
        assertEquals(1, filledTag.size)
    }

    @Test
    fun `Test getting int from index`() {
        assertFailsWith(ArrayIndexOutOfBoundsException::class) { emptyTag[0] }
        assertEquals(12234234, filledTag[0])
    }

    @Test
    fun `Test setting int at index`() {
        assertFailsWith(ArrayIndexOutOfBoundsException::class) { emptyTag[0] = 45 }
        val newTag = IntArrayTag(intArrayOf(45))
        assertEquals(45, newTag[0])
        newTag[0] = 46
        assertEquals(46, newTag[0])
    }

    @Test
    fun `Test toString`() {
        assertEquals("[0 integers]", emptyTag.toString())
        assertEquals("[1 integers]", filledTag.toString())
    }

    @Test
    fun `Test hashCode`() {
        assertEquals(emptyTag.value.contentHashCode(), emptyTag.hashCode())
        assertEquals(filledTag.value.contentHashCode(), filledTag.hashCode())
    }

    @Test
    fun `Test reading array`() = assertEquals(
            filledTag, IntArrayTag.read(byteArrayOf(
            0, 0, 0, 1,
            0, -70, -83, -6
        )))

    @Test
    fun `Test writing empty array`() = Assert.assertArrayEquals(byteArrayOf(0, 0, 0, 0), emptyTag.write())

    @Test
    fun `Test writing array`() = Assert.assertArrayEquals(byteArrayOf(0, 0, 0, 1, 0, -70, -83, -6), filledTag.write())

    companion object {
        private val emptyTag = IntArrayTag(intArrayOf())
        private val filledTag = IntArrayTag(intArrayOf(12234234))
    }
}
