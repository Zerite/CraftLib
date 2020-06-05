package dev.zerite.mclib.protocol.packet.play.server.inventory

import dev.zerite.mclib.protocol.packet.PacketTest
import dev.zerite.mclib.protocol.packet.play.server.inventory.ServerPlayHeldItemChangePacket
import dev.zerite.mclib.protocol.version.ProtocolVersion
import kotlin.test.Test
import kotlin.test.assertFails

/**
 * Tests that the held item change packet is properly handled.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayHeldItemChangeTest : PacketTest<ServerPlayHeldItemChangePacket>(
    ServerPlayHeldItemChangePacket
) {

    init {
        example(
            ServerPlayHeldItemChangePacket(
                0
            )
        )
        example(
            ServerPlayHeldItemChangePacket(
                7
            )
        )
        example(
            ServerPlayHeldItemChangePacket(
                8
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeByte(8)
            }
        }
    }

    @Test
    fun `Test Fails`() {
        assertFails {
            ServerPlayHeldItemChangePacket(
                9
            )
        }
    }

}