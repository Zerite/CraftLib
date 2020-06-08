package dev.zerite.craftlib.nbt.impl

import org.junit.Assert
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests that the float tag is working correctly.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class FloatTagTest : TagImplTest<FloatTag>() {
    @Test
    fun `Test reading float tag`() {
        assertEquals(expectedZero, FloatTag.read(byteArrayOf(0, 0, 0, 0)))
        assertEquals(expectedNegative, FloatTag.read(byteArrayOf(-54, 15, 108, 86)))
        assertEquals(expectedPositive, FloatTag.read(byteArrayOf(75, -9, -93, 118)))
    }

    @Test
    fun `Test writing float tag`() {
        Assert.assertArrayEquals(byteArrayOf(0, 0, 0, 0), expectedZero.write())
        Assert.assertArrayEquals(byteArrayOf(-54, 15, 108, 86), expectedNegative.write())
        Assert.assertArrayEquals(byteArrayOf(75, -9, -93, 118), expectedPositive.write())
    }

    @Test
    fun `Test toString`() {
        assertEquals("0.0", expectedZero.toString())
        assertEquals("-2349845.5", expectedNegative.toString())
        assertEquals("3.2458476E7", expectedPositive.toString())
    }

    companion object {
        private val expectedZero = FloatTag(0f)
        private val expectedNegative = FloatTag(-2349845.458f)
        private val expectedPositive = FloatTag(32458475.234f)
    }
}
