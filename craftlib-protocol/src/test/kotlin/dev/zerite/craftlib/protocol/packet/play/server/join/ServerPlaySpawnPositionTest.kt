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
                Int.MAX_VALUE,
                Int.MAX_VALUE,
                Int.MAX_VALUE
            )
        )
        example(
            ServerPlaySpawnPositionPacket(
                Int.MIN_VALUE,
                Int.MIN_VALUE,
                Int.MIN_VALUE
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeInt(Int.MIN_VALUE)
                writeInt(Int.MIN_VALUE)
                writeInt(Int.MIN_VALUE)
            }
        }
    }

}