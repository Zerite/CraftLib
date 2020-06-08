package dev.zerite.craftlib.nbt.impl

import org.junit.Assert
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests that the string tag is working correctly.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class StringTagTest : TagImplTest<StringTag>() {
    @Test
    fun `Test reading short tag`() {
        assertEquals(expectedEmpty, StringTag.read(byteArrayOf(0, 0)))
        assertEquals(
            expectedAscii,
            StringTag.read(byteArrayOf(0, 11, 116, 101, 115, 116, 32, 115, 116, 114, 105, 110, 103))
        )
        assertEquals(
            expectedUnicode, StringTag.read(
                byteArrayOf(
                    0,
                    69,
                    -19,
                    -96,
                    -67,
                    -19,
                    -72,
                    -126,
                    -19,
                    -96,
                    -66,
                    -19,
                    -76,
                    -93,
                    -19,
                    -96,
                    -67,
                    -19,
                    -79,
                    -115,
                    -19,
                    -96,
                    -67,
                    -19,
                    -72,
                    -127,
                    -19,
                    -96,
                    -66,
                    -19,
                    -76,
                    -98,
                    -19,
                    -96,
                    -67,
                    -19,
                    -72,
                    -119,
                    -19,
                    -96,
                    -67,
                    -19,
                    -72,
                    -119,
                    -30,
                    -100,
                    -116,
                    -19,
                    -96,
                    -67,
                    -19,
                    -79,
                    -113,
                    -19,
                    -96,
                    -67,
                    -19,
                    -79,
                    -128,
                    -19,
                    -96,
                    -67,
                    -19,
                    -72,
                    -122,
                    -19,
                    -96,
                    -66,
                    -19,
                    -76,
                    -108
                )
            )
        )
    }

    @Test
    fun `Test writing short tag`() {
        Assert.assertArrayEquals(byteArrayOf(0, 0), expectedEmpty.write())
        Assert.assertArrayEquals(
            byteArrayOf(0, 11, 116, 101, 115, 116, 32, 115, 116, 114, 105, 110, 103),
            expectedAscii.write()
        )
        Assert.assertArrayEquals(
            byteArrayOf(
                0,
                69,
                -19,
                -96,
                -67,
                -19,
                -72,
                -126,
                -19,
                -96,
                -66,
                -19,
                -76,
                -93,
                -19,
                -96,
                -67,
                -19,
                -79,
                -115,
                -19,
                -96,
                -67,
                -19,
                -72,
                -127,
                -19,
                -96,
                -66,
                -19,
                -76,
                -98,
                -19,
                -96,
                -67,
                -19,
                -72,
                -119,
                -19,
                -96,
                -67,
                -19,
                -72,
                -119,
                -30,
                -100,
                -116,
                -19,
                -96,
                -67,
                -19,
                -79,
                -113,
                -19,
                -96,
                -67,
                -19,
                -79,
                -128,
                -19,
                -96,
                -67,
                -19,
                -72,
                -122,
                -19,
                -96,
                -66,
                -19,
                -76,
                -108
            ), expectedUnicode.write()
        )
    }

    @Test
    fun `Test toString`() {
        assertEquals("''", expectedEmpty.toString())
        assertEquals("'test string'", expectedAscii.toString())
        assertEquals("'😂🤣👍😁🤞😉😉✌👏👀😆🤔'", expectedUnicode.toString())
    }

    companion object {
        private val expectedEmpty = StringTag("")
        private val expectedAscii = StringTag("test string")
        private val expectedUnicode = StringTag("😂🤣👍😁🤞😉😉✌👏👀😆🤔")
    }
}
