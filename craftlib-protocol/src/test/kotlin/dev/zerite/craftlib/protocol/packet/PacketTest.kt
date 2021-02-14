package dev.zerite.craftlib.protocol.packet

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.PacketDirection
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import dev.zerite.craftlib.protocol.wrap
import io.netty.buffer.Unpooled
import org.junit.Assert
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith
import java.util.*
import kotlin.test.assertEquals

/**
 * Tests a single packet's reading and writing capabilities, and if it
 * matches properly with the expected output when writing.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@RunWith(JUnitPlatform::class)
abstract class PacketTest<T : Packet>(private val io: PacketIO<T>) {

    companion object {
        /**
         * The maximum supported protocol version which we should use to
         * write packets with.
         */
        private val maxSupported = ProtocolVersion.MC1_9
    }

    /**
     * Mappings of the example packets to their protocol versions and expected
     * IO output.
     */
    private val examples = hashMapOf<T, MutableMap<ProtocolVersion, ByteArray>>()

    /**
     * Contains a list of the raw examples to test.
     */
    private val rawExamples = arrayListOf<T>()

    /**
     * Builds all the packet tests for each packet.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @TestFactory
    fun buildPacketTests() =
        arrayListOf<DynamicTest>()
            .apply {
                runTest { packet, buf, version, _, connection, index ->
                    add(DynamicTest.dynamicTest(
                        "Test Packet Contents Are Identical ($version ${index + 1})"
                    ) {
                        io.write(buf, version, packet, connection)
                        assertEquals(
                            packet,
                            io.read(buf, version, connection),
                            "Read packet doesn't match written in $version"
                        )
                        buf.release()
                    })
                }

                runTest { packet, buf, version, output, connection, index ->
                    add(DynamicTest.dynamicTest("Test Writing Packet ($version ${index + 1})") {
                        io.write(buf, version, packet, connection)
                        Assert.assertArrayEquals(
                            "Written packet doesn't match expected in $version",
                            output,
                            buf.array()
                        )
                        buf.release()
                    })
                }

                runTest { packet, buf, version, output, connection, index ->
                    add(DynamicTest.dynamicTest("Test Reading Packet ($version ${index + 1})") {
                        val actual = io.read(output.wrap(connection), version, connection)
                        assertEquals(packet, actual, "Read packet doesn't match expected in $version")
                        buf.release()
                    })
                }

                rawExamples.forEachIndexed { i, packet ->
                    ProtocolVersion.values().forEach { version ->
                        if (version > maxSupported || version == ProtocolVersion.UNKNOWN) return@forEach

                        val dummy = NettyConnection(PacketDirection.CLIENTBOUND)
                        dummy.version = version
                        val buf = Unpooled.buffer().wrap(dummy)

                        add(DynamicTest.dynamicTest(
                            "Test Packet Example IO ($version ${i + 1})"
                        ) {
                            io.write(buf, version, packet, dummy)
                            assertEquals(
                                packet,
                                io.read(buf, version, dummy),
                                "Read packet doesn't match written in $version (${i + 1})"
                            )
                            buf.release()
                        })
                    }
                }
            }

    /**
     * Runs through all the examples and runs tests.
     *
     * @param  build          The processor for each packet.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    private fun runTest(
        build: (packet: T, buf: ProtocolBuffer, version: ProtocolVersion, output: ByteArray, connection: NettyConnection, index: Int) -> Unit
    ) {
        examples.entries.reversed().forEachIndexed { index, (packet, map) ->
            map.forEach { (version, output) ->
                val dummy = NettyConnection(PacketDirection.CLIENTBOUND)
                dummy.version = version
                val buf = Unpooled.buffer().wrap(dummy)
                build(packet, buf, version, output, dummy, index)
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
    protected fun example(
        packet: T,
        minimumVersion: ProtocolVersion = ProtocolVersion.values().filter { it <= maxSupported }.minByOrNull { it.id }
            ?: error("No compatible versions"),
        maximumVersion: ProtocolVersion? = null,
        builder: ExampleListBuilder.() -> Unit = {}
    ) = ExampleListBuilder().apply(builder).examples.toTypedArray().forEach { (version, bytes) ->
        if (version < minimumVersion || version > maxSupported || (maximumVersion != null && version > maximumVersion)) return@forEach
        examples.computeIfAbsent(packet) { EnumMap(ProtocolVersion::class.java) }[version] = bytes
    }

    /**
     * Adds an example to be tested in this class.
     *
     * @param  packet      The packet to test.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    protected fun example(packet: T) = rawExamples.add(packet)

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
