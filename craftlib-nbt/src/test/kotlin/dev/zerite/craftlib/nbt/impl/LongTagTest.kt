package dev.zerite.craftlib.nbt.impl

import org.junit.Assert
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests that the long tag is working correctly.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class LongTagTest : TagImplTest<LongTag>() {
    @Test
    fun `Test reading long tag`() {
        assertEquals(expectedZero, LongTag.read(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0)))
        assertEquals(expectedNegative, LongTag.read(byteArrayOf(-1, -9, -66, -39, 58, -19, 42, 75)))
        assertEquals(expectedPositive, LongTag.read(byteArrayOf(0, 8, -18, -61, -117, 9, -48, 93)))
    }

    @Test
    fun `Test writing long tag`() {
        Assert.assertArrayEquals(byteArrayOf(0, 0, 0, 0, 0, 0, 0, 0), expectedZero.write())
        Assert.assertArrayEquals(byteArrayOf(-1, -9, -66, -39, 58, -19, 42, 75), expectedNegative.write())
        Assert.assertArrayEquals(byteArrayOf(0, 8, -18, -61, -117, 9, -48, 93), expectedPositive.write())
    }

    @Test
    fun `Test toString`() {
        assertEquals("0", expectedZero.toString())
        assertEquals("-2323434584593845", expectedNegative.toString())
        assertEquals("2514323432394845", expectedPositive.toString())
    }

    companion object {
        private val expectedZero = LongTag(0)
        private val expectedNegative = LongTag(-2323434584593845)
        private val expectedPositive = LongTag(2514323432394845)
    }
}
