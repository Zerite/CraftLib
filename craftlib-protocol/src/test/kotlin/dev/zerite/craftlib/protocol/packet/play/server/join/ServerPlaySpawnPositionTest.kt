package dev.zerite.craftlib.protocol.packet.play.server.join

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Tests spawn positions and validates that they are being written
 * and read properly.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlaySpawnPositionTest : PacketTest<ServerPlaySpawnPositionPacket>(
    ServerPlaySpawnPositionPacket
) {

    init {
        example(
            ServerPlaySpawnPositionPacket(
                0,
                0,
                0
            )
        )
        example(
            ServerPlaySpawnPositionPacket(
                30503,
                10,
                4382423
            )
        )
        example(
            ServerPlaySpawnPositionPacket(
                10, 20, 30
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeInt(10)
                writeInt(20)
                writeInt(30)
            }
            ProtocolVersion.MC1_8 {
                writeLong(((10L and 0x3FFFFFFL) shl 38) or ((30L and 0x3FFFFFFL) shl 12) or (20L and 0xFFFL))
            }
        }
    }

}
