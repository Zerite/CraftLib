package dev.zerite.craftlib.nbt.impl

import org.junit.Assert
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests that the int tag is working correctly.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class IntTagTest : TagImplTest<IntTag>() {
    @Test
    fun `Test reading int tag`() {
        assertEquals(expectedZero, IntTag.read(byteArrayOf(0, 0, 0, 0)))
        assertEquals(expectedNegative, IntTag.read(byteArrayOf(-14, 38, -72, 94)))
        assertEquals(expectedPositive, IntTag.read(byteArrayOf(14, -4, -115, -105)))
    }

    @Test
    fun `Test writing int tag`() {
        Assert.assertArrayEquals(byteArrayOf(0, 0, 0, 0), expectedZero.write())
        Assert.assertArrayEquals(byteArrayOf(-14, 38, -72, 94), expectedNegative.write())
        Assert.assertArrayEquals(byteArrayOf(14, -4, -115, -105), expectedPositive.write())
    }

    @Test
    fun `Test toString`() {
        assertEquals("0", expectedZero.toString())
        assertEquals("-232343458", expectedNegative.toString())
        assertEquals("251432343", expectedPositive.toString())
    }

    companion object {
        private val expectedZero = IntTag(0)
        private val expectedNegative = IntTag(-232343458)
        private val expectedPositive = IntTag(251432343)
    }
}
