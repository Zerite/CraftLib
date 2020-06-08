package dev.zerite.craftlib.nbt.impl

import org.junit.Assert
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests that the named tag is working correctly.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class NamedTagTest : TagImplTest<NamedTag<*>>() {
    @Test
    fun `Test writing named tag`() = Assert.assertArrayEquals(
        byteArrayOf(
            0, 4,
            97, 97, 97, 97,
            1
        ), namedTag.write()
    )

    @Test
    fun `Test named tag reporting correct ID`() = assertEquals(namedTag.tag.id, namedTag.id)

    @Test
    fun `Test toString`() = assertEquals("ByteTag('aaaa'): 1", namedTag.toString())

    companion object {
        private val namedTag = NamedTag("aaaa", ByteTag(1))
    }
}
