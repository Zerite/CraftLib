package dev.zerite.craftlib.nbt.impl

import org.junit.Assert
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests that the short tag is working correctly.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ShortTagTest : TagImplTest<ShortTag>() {
    @Test
    fun `Test reading short tag`() {
        assertEquals(expectedZero, ShortTag.read(byteArrayOf(0, 0)))
        assertEquals(expectedNegative, ShortTag.read(byteArrayOf(-91, 62)))
        assertEquals(expectedPositive, ShortTag.read(byteArrayOf(98, 55)))
    }

    @Test
    fun `Test writing short tag`() {
        Assert.assertArrayEquals(byteArrayOf(0, 0), expectedZero.write())
        Assert.assertArrayEquals(byteArrayOf(-91, 62), expectedNegative.write())
        Assert.assertArrayEquals(byteArrayOf(98, 55), expectedPositive.write())
    }

    @Test
    fun `Test toString`() {
        assertEquals("0", expectedZero.toString())
        assertEquals("-23234", expectedNegative.toString())
        assertEquals("25143", expectedPositive.toString())
    }

    companion object {
        private val expectedZero = ShortTag(0)
        private val expectedNegative = ShortTag(-23234)
        private val expectedPositive = ShortTag(25143)
    }
}
