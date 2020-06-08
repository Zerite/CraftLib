package dev.zerite.craftlib.nbt.impl

import org.junit.Assert
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests that the double tag is working correctly.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class DoubleTagTest : TagImplTest<DoubleTag>() {
    @Test
    fun `Test reading double tag`() {
        assertEquals(expectedZero, DoubleTag.read(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0)))
        assertEquals(expectedNegative, DoubleTag.read(byteArrayOf(-63, -85, -78, -113, 68, -78, 75, -37)))
        assertEquals(expectedPositive, DoubleTag.read(byteArrayOf(65, -83, -7, 27, 46, -90, 100, 24)))
    }

    @Test
    fun `Test writing double tag`() {
        Assert.assertArrayEquals(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0), expectedZero.write())
        Assert.assertArrayEquals(byteArrayOf(-63, -85, -78, -113, 68, -78, 75, -37), expectedNegative.write())
        Assert.assertArrayEquals(byteArrayOf(65, -83, -7, 27, 46, -90, 100, 24), expectedPositive.write())
    }

    @Test
    fun `Test toString`() {
        assertEquals("0.0", expectedZero.toString())
        assertEquals("-2.3234345834823498E8", expectedNegative.toString())
        assertEquals("2.514323433249824E8", expectedPositive.toString())
    }

    companion object {
        private val expectedZero = DoubleTag(0.0)
        private val expectedNegative = DoubleTag(-232343458.34823498)
        private val expectedPositive = DoubleTag(251432343.324982394845)
    }
}
