package dev.zerite.craftlib.protocol

import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.PacketDirection
import io.netty.buffer.Unpooled
import org.junit.Assert
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests our custom Netty ByteBuf implementation and ensures there are
 * no failures in writing our data types.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ProtocolBufferTest {

    /**
     * Dummy connection to allow wrapping of the buffers.
     */
    private val dummy = NettyConnection(PacketDirection.CLIENTBOUND)

    @Test
    fun `Test VarInt IO`() {
        val expected = byteArrayOf(
            0b1_001_1000.toByte(), 0b1_000_1100.toByte(), 0b0_000_0110.toByte(), // 99,864
            0b0_000_0000.toByte(), // 0
            0b1_111_1111.toByte(), 0b1_111_1111.toByte(), 0b1_111_1111.toByte(),
            0b1_111_1111.toByte(), 0b0_000_0111.toByte() // Int.MAX_VALUE
        )
        val buffer = expected.wrap(dummy)

        assertEquals(99864, buffer.readVarInt())
        assertEquals(0, buffer.readVarInt())
        assertEquals(Int.MAX_VALUE, buffer.readVarInt())

        val output = Unpooled.buffer(expected.size).wrap(dummy)
        output.writeVarInt(99864)
        output.writeVarInt(0)
        output.writeVarInt(Int.MAX_VALUE)
        Assert.assertArrayEquals(expected, output.array())

        buffer.release()
        output.release()
    }

    @Test
    fun `Test Byte IO`() {
        val buffer = byteBuffer(
            0x00, // 0
            0x7F, // 127
            0x34 // 52
        )

        assertEquals(0x00, buffer.readByte())
        assertEquals(0x7F, buffer.readByte())
        assertEquals(0x34, buffer.readByte())

        val expected = byteArrayOf(0x00, 0x7F, 0x34)
        val output = Unpooled.buffer(3).wrap(dummy)

        output.writeByte(0x00)
        output.writeByte(0x7F)
        output.writeByte(0x34)

        Assert.assertArrayEquals(expected, output.array())

        buffer.release()
        output.release()
    }

    @Test
    fun `Test Byte Array IO`() {
        val bytes = byteArrayOf(0b0_000_0110, 0x00, 0x7F, 0x3D, 0x4A, 0x6B, 0x3A)
        val written = bytes.drop(1).toByteArray()
        val buffer = bytes.wrap(dummy)
        Assert.assertArrayEquals(written, buffer.readByteArray())

        val output = Unpooled.buffer(bytes.size).wrap(dummy)
        output.writeByteArray(written)
        Assert.assertArrayEquals(bytes, output.array())

        buffer.release()
        output.release()
    }

    @Test
    fun `Test String IO`() {
        val original = byteArrayOf(
            0b0_000_0101, 'a'.toByte(), 'a'.toByte(), 'a'.toByte(), 'a'.toByte(), 'a'.toByte(), // aaaaa
            0b0_000_0101, 'z'.toByte(), 'a'.toByte(), 'x'.toByte(), 'y'.toByte(), 'e'.toByte(), // zaxye
            0b0_000_1000, 'e'.toByte(), 'f'.toByte(), 'g'.toByte(), 'h'.toByte(), 'i'.toByte(),
            'j'.toByte(), 'k'.toByte(), 'l'.toByte()                                            // efghijkl
        )
        val buffer = original.wrap(dummy)

        assertEquals("aaaaa", buffer.readString())
        assertEquals("zaxye", buffer.readString())
        assertEquals("efghijkl", buffer.readString())

        val output = Unpooled.buffer(original.size).wrap(dummy)
        output.writeString("aaaaa")
        output.writeString("zaxye")
        output.writeString("efghijkl")

        Assert.assertArrayEquals(original, output.array())

        buffer.release()
        output.release()
    }

    /**
     * Creates a new byte array and wraps it into our
     * byte buffer.
     *
     * @param  bytes        The bytes to include into the array.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    private fun byteBuffer(vararg bytes: Byte) =
        byteArrayOf(*bytes).wrap(dummy)

}