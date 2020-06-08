package dev.zerite.craftlib.nbt

import dev.zerite.craftlib.nbt.impl.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

/**
 * Tests that DSL is working properly.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class DSLTest {
    @Test
    fun `Test creating compound tag`() = assertEquals(expectedEmptyCompound, tag(expectedEmptyCompound.value))

    @Test
    fun `Test creating byte array tag`() = assertEquals(expectedByteArrayTag, tag(expectedByteArrayTag.value))

    @Test
    fun `Test creating byte tag`() = assertEquals(expectedByteTag, tag(expectedByteTag.value))

    @Test
    fun `Test creating double tag`() = assertEquals(expectedDoubleTag, tag(expectedDoubleTag.value))

    @Test
    fun `Test creating float tag`() = assertEquals(expectedFloatTag, tag(expectedFloatTag.value))

    @Test
    fun `Test creating int array tag`() = assertEquals(expectedIntArrayTag, tag(expectedIntArrayTag.value))

    @Test
    fun `Test creating int tag`() = assertEquals(expectedIntTag, tag(expectedIntTag.value))

    @Test
    fun `Test creating list tag`() = assertEquals(expectedListTag, tag(expectedListTag.value))

    @Test
    fun `Test creating long array tag`() = assertEquals(expectedLongArrayTag, tag(expectedLongArrayTag.value))

    @Test
    fun `Test creating long tag`() = assertEquals(expectedLongTag, tag(expectedLongTag.value))

    @Test
    fun `Test creating named tag`() = assertEquals(expectedNamedTag, named(expectedNamedTag.name, expectedNamedTag.tag))

    @Test
    fun `Test creating short tag`() = assertEquals(expectedShortTag, tag(expectedShortTag.value))

    @Test
    fun `Test creating string tag`() = assertEquals(expectedStringTag, tag(expectedStringTag.value))

    @Test
    fun `Test building compound tag`() =
        assertEquals(expectedBuiltCompound, compound {
            "test entry 1" to "test entry value"
        })

    @Test
    fun `Test building list tag`() =
        assertEquals(expectedBuiltList, list {
            +"test value 1"
            +"test value 2"
            +"test value 3"
        })

    @Test
    fun `Test adding different types of tags to same list`() {
        assertFails {
            list<StringTag> {
                +compound { }
            }
        }
    }

    companion object {
        private val expectedEmptyCompound = CompoundTag()
        private val expectedByteArrayTag = ByteArrayTag(byteArrayOf(123, 45, -23))
        private val expectedByteTag = ByteTag(122)
        private val expectedDoubleTag = DoubleTag(4.34923489456)
        private val expectedFloatTag = FloatTag(43.4382748374f)
        private val expectedIntArrayTag = IntArrayTag(intArrayOf(234, 238478475, 138479387))
        private val expectedIntTag = IntTag(329483)
        private val expectedListTag = ListTag(mutableListOf(IntTag(3498), IntTag(32847)))
        private val expectedLongArrayTag =
            LongArrayTag(longArrayOf(3248974938579834578L, 9823479823749854L, 2348972398472398457L))
        private val expectedLongTag = LongTag(8248792384935438957L)
        private val expectedNamedTag = NamedTag("test name", LongTag(2348L))
        private val expectedShortTag = ShortTag(31298)
        private val expectedStringTag = StringTag("test string")
        private val expectedBuiltCompound = CompoundTag().also {
            it["test entry 1"] = StringTag("test entry value")
        }
        private val expectedBuiltList = ListTag<StringTag>().also {
            it += StringTag("test value 1")
            it += StringTag("test value 2")
            it += StringTag("test value 3")
        }
    }
}
