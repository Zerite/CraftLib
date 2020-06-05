package dev.zerite.mclib.protocol.packet.play.server.player

import dev.zerite.mclib.protocol.packet.PacketTest
import dev.zerite.mclib.protocol.version.ProtocolVersion

/**
 * Tests that positions and look vectors are being encoded correctly.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayPlayerPositionLookTest :
    PacketTest<ServerPlayPlayerPositionLookPacket>(ServerPlayPlayerPositionLookPacket) {

    init {
        example(ServerPlayPlayerPositionLookPacket(0.0, 0.0, 0.0, 0.0f, 0.0f, true))
        example(
            ServerPlayPlayerPositionLookPacket(
                Double.MAX_VALUE,
                Double.MAX_VALUE,
                Double.MAX_VALUE,
                1.0f,
                1.0f,
                false
            )
        )
        example(
            ServerPlayPlayerPositionLookPacket(
                Double.MIN_VALUE,
                Double.MIN_VALUE,
                Double.MIN_VALUE,
                -1.0f,
                -1.0f,
                true
            )
        )
        example(ServerPlayPlayerPositionLookPacket(1.0, 2.0, 3.0, 1.0f, 0.0f, true)) {
            ProtocolVersion.MC1_7_2 {
                writeDouble(1.0)
                writeDouble(2.0)
                writeDouble(3.0)
                writeFloat(1.0f)
                writeFloat(0.0f)
                writeBoolean(true)
            }
        }
    }

}