package dev.zerite.craftlib.nbt.impl

import org.junit.Assert
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests that the byte tag is working correctly.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ByteTagTest : TagImplTest<ByteTag>() {
    @Test
    fun `Test reading byte tag`() {
        assertEquals(expectedZeroByte, ByteTag.read(byteArrayOf(0)))
        assertEquals(expectedNegativeByte, ByteTag.read(byteArrayOf(-23)))
        assertEquals(expectedByte, ByteTag.read(byteArrayOf(45)))
    }

    @Test
    fun `Test writing byte tag`() {
        Assert.assertArrayEquals(byteArrayOf(0), expectedZeroByte.write())
        Assert.assertArrayEquals(byteArrayOf(-23), expectedNegativeByte.write())
        Assert.assertArrayEquals(byteArrayOf(45), expectedByte.write())
    }

    @Test
    fun `Test toString`() {
        assertEquals("0", expectedZeroByte.toString())
        assertEquals("-23", expectedNegativeByte.toString())
        assertEquals("45", expectedByte.toString())
    }

    companion object {
        private val expectedZeroByte = ByteTag(0)
        private val expectedNegativeByte = ByteTag(-23)
        private val expectedByte = ByteTag(45)
    }
}
