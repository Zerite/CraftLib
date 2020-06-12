package dev.zerite.craftlib.protocol.packet.play.server.player

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Tests that the health update packet is working.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayUpdateHealthTest : PacketTest<ServerPlayUpdateHealthPacket>(ServerPlayUpdateHealthPacket) {

    init {
        example(ServerPlayUpdateHealthPacket(10f, 10, 10f))
        example(ServerPlayUpdateHealthPacket(0f, 20, 20f))
        example(ServerPlayUpdateHealthPacket(0f, 10, 20f)) {
            ProtocolVersion.MC1_7_2 {
                writeFloat(0f)
                writeShort(10)
                writeFloat(20f)
            }
        }
    }

}