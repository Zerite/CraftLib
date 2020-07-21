package dev.zerite.craftlib.nbt

import dev.zerite.craftlib.nbt.impl.CompoundTag
import dev.zerite.craftlib.nbt.impl.LongTag
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests that the NBT IO is being correctly written and read.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class NBTIOTest {
    private fun <T> getInputStream(resourceName: String, handler: suspend (InputStream) -> T) =
        NBTIOTest::class.java.getResourceAsStream("/$resourceName")?.use {
            runBlocking { handler(it) }
        } ?: error("Failed to find resource: $resourceName")

    private suspend fun writeToByteArray(tag: NBTTag, compressed: Boolean): ByteArray {
        val out = ByteArrayOutputStream()
        if (compressed) NBTIO.writeCompressed(tag, out)
        else NBTIO.write(tag, out)
        return out.toByteArray()
    }

    private suspend fun testWrite(tag: NBTTag, compressed: Boolean) {
        val outputTag = ByteArrayInputStream(writeToByteArray(tag, compressed)).let {
            if (compressed) NBTIO.readCompressed(it)
            else NBTIO.read(it)
        }
        assertEquals(tag, outputTag)
    }

    @Test
    fun `Test reading hello_world`() =
        getInputStream("hello_world.nbt") {
            assertEquals<NBTTag>(expectedHelloWorld, NBTIO.read(it))
        }

    @Test
    fun `Test writing hello_world`() = runBlocking {
        testWrite(expectedHelloWorld, false)
    }

    @Test
    fun `Test writing compressed hello_world`() = runBlocking {
        testWrite(expectedHelloWorld, true)
    }

    @Test
    fun `Test reading big test`() =
        getInputStream("big_test.nbt") {
            assertEquals<NBTTag>(expectedBigTest, NBTIO.readCompressed(it))
        }

    @Test
    fun `Test writing big test`() = runBlocking {
        testWrite(expectedBigTest, false)
    }

    @Test
    fun `Test writing compressed big test`() = runBlocking {
        testWrite(expectedBigTest, true)
    }

    @Test
    fun `Test reading player-nan-value`() =
        getInputStream("player_nan_value.nbt") {
            assertEquals<NBTTag>(expectedPlayerNanValue, NBTIO.readCompressed(it))
        }

    @Test
    fun `Test writing player-nan-value`() = runBlocking {
        testWrite(expectedPlayerNanValue, false)
    }

    @Test
    fun `Test writing compressed player-nan-value`() = runBlocking {
        testWrite(expectedPlayerNanValue, true)
    }

    companion object {
        private val expectedHelloWorld = named("hello world", compound {
            "name" to "Bananrama"
        })
        private val expectedPlayerNanValue = named("", compound {
            "Air" to 300.toShort()
            "AttackTime" to 0.toShort()
            "DeathTime" to 0.toShort()
            "FallDistance" to 0.0f
            "Fire" to (-20).toShort()
            "Health" to 20.toShort()
            "HurtTime" to 0.toShort()
            "Inventory" to emptyList<NBTTag>()
            "Motion" to listOf(0.0, 0.0, 0.0)
            "OnGround" to 1.toByte()
            "Pos" to listOf(0.0, Double.NaN, 0.0)
            "Rotation" to listOf(164.39995f, -63.150204f)
        })
        private val expectedBigTest = named("Level", compound {
            "nested compound test" to compound {
                "egg" to compound {
                    "name" to "Eggbert"
                    "value" to 0.5f
                }
                "ham" to compound {
                    "name" to "Hampus"
                    "value" to 0.75f
                }
            }
            "intTest" to 2147483647
            "byteTest" to 127.toByte()
            "stringTest" to "HELLO WORLD THIS IS A TEST STRING ÅÄÖ!"
            "listTest (long)" to list<LongTag> {
                add(11L)
                add(12L)
                add(13L)
                add(14L)
                add(15L)
            }
            "doubleTest" to 0.49312871321823148
            "floatTest" to 0.49823147058486938F
            "longTest" to 9223372036854775807L
            "listTest (compound)" to list<CompoundTag> {
                +compound {
                    "created-on" to 1264099775885L
                    "name" to "Compound tag #0"
                }
                +compound {
                    "created-on" to 1264099775885L
                    "name" to "Compound tag #1"
                }
            }
            "byteArrayTest (the first 1000 values of (n*n*255+n*7)%100, starting with n=0 (0, 62, 34, 16, 8, ...))" to ByteArray(
                1000
            ) {
                ((it * it * 255 + it * 7) % 100).toByte()
            }
            "shortTest" to 32767.toShort()
        })
    }
}
