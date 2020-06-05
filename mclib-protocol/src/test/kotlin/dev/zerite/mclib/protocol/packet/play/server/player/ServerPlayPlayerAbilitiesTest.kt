package dev.zerite.mclib.protocol.packet.play.server.player

import dev.zerite.mclib.protocol.packet.PacketTest
import dev.zerite.mclib.protocol.version.ProtocolVersion

/**
 * Tests that the bitwise operations and packet IO is working.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayPlayerAbilitiesTest : PacketTest<ServerPlayPlayerAbilitiesPacket>(
    ServerPlayPlayerAbilitiesPacket
) {

    init {
        example(
            ServerPlayPlayerAbilitiesPacket(
                0,
                0.05f,
                0.01f
            )
        )
        example(
            ServerPlayPlayerAbilitiesPacket(
                0b1111,
                0.5f,
                0.1f
            )
        )
        example(
            ServerPlayPlayerAbilitiesPacket(
                0,
                0.05f,
                0.01f
            ).apply {
                god = false
                canFly = true
                flying = true
                creative = false
            }
        ) {
            ProtocolVersion.MC1_7_2 {
                writeByte(0b0110)
                writeFloat(0.05f)
                writeFloat(0.01f)
            }
        }
    }

}