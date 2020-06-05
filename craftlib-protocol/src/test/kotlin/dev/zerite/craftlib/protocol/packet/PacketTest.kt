package dev.zerite.craftlib.protocol.packet

import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.PacketDirection
import dev.zerite.craftlib.protocol.version.ProtocolState
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import dev.zerite.craftlib.protocol.wrap
import io.netty.buffer.Unpooled
import org.junit.Assert
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests a single packet's reading and writing capabilities, and if it
 * matches properly with the expected output when writing.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
abstract class PacketTest<T : Any>(val io: PacketIO<T>) {

    companion object {
        /**
         * The maximum supported protocol version which we should use to
         * write all the packets withs.
         */
        private val maxSupported = ProtocolVersion.MC1_7_2
    }

    /**
     * Mappings of the example packets to their protocol versions and expected
     * IO output.
     */
    private val examples = hashMapOf<T, MutableMap<ProtocolVersion, ByteArray>>()

    @Test
    fun `Test Packet Contents Are Identical`() =
        runTest { packet, buf, version, _, connection ->
            io.write(buf, version, packet, connection)
            assertEquals(packet, io.read(buf, version, connection), "Read packet doesn't match written in $version")
        }


    @Test
    fun `Test Writing Packet`() =
        runTest { packet, buf, version, output, connection ->
            io.write(buf, version, packet, connection)
            Assert.assertArrayEquals("Written packet doesn't match expected in $version", output, buf.array())
        }

    @Test
    fun `Test Reading Packet`() =
        runTest { packet, _, version, output, connection ->
            val actual = io.read(output.wrap(connection), version, connection)
            assertEquals(packet, actual, "Read packet doesn't match expected in $version")
        }

    /**
     * Runs through all the examples and runs tests.
     *
     * @param  build          The processor for each packet.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    private fun runTest(build: (packet: T, buf: ProtocolBuffer, version: ProtocolVersion, output: ByteArray, connection: NettyConnection) -> Unit) {
        examples.forEach { (packet, map) ->
            map.forEach { (version, output) ->
                val dummy = NettyConnection(PacketDirection.CLIENTBOUND)
                    .also { it.version = version }
                val buf = Unpooled.buffer().wrap(dummy)

                try {
                    build(packet, buf, version, output, dummy)
                } finally {
                    buf.release()
                }
            }
        }
    }

    /**
     * Adds an example to be tested in this class.
     *
     * @param  packet      The packet to test.
     * @param  builder     Builder function for the example list.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    protected fun example(packet: T, builder: ExampleListBuilder.() -> Unit = {}) =
        ProtocolState.SideData.runForAllProtocols(
            ExampleListBuilder().apply(builder).examples.toTypedArray()
        ) { version, bytes ->
            if (version > maxSupported) return@runForAllProtocols
            examples.computeIfAbsent(packet) { EnumMap(ProtocolVersion::class.java) }[version] = bytes
        }

    /**
     * Simple builder class for a packet example list.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    protected class ExampleListBuilder {
        /**
         * Stores the list of examples we've provided for this packet mapped
         * to their protocol version and expected output.
         */
        val examples = arrayListOf<Pair<ProtocolVersion, ByteArray>>()

        /**
         * Adds an example mapping to this packet test.
         *
         * @param  build     Build the protocol buffer data which will write what
         *                   we should compare the packet to.
         * @author Koding
         * @since  0.1.0-SNAPSHOT
         */
        operator fun ProtocolVersion.invoke(build: ProtocolBuffer.() -> Unit) =
            Unpooled.buffer().wrap(
                NettyConnection(PacketDirection.CLIENTBOUND)
                    .also { it.version = this }
            ).apply(build)
                .apply {
                    examples.add(this@invoke to (array() ?: return@apply))
                    release()
                }
    }

}