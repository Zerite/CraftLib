package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to update multiple block changes in a
 * single packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayMultiBlockChangeTest : PacketTest<ServerPlayMultiBlockChangePacket>(ServerPlayMultiBlockChangePacket) {
    init {
        example(ServerPlayMultiBlockChangePacket(10, 10, emptyArray()))
        example(
            ServerPlayMultiBlockChangePacket(
                10,
                10,
                arrayOf(ServerPlayMultiBlockChangePacket.Record(10, 10, 10, 10, 10))
            )
        )
        example(
            ServerPlayMultiBlockChangePacket(
                20,
                40,
                arrayOf(ServerPlayMultiBlockChangePacket.Record(5, 20, 2, 4, 6))
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeInt(20)
                writeInt(40)
                writeShort(1)
                writeInt(4)

                // Block record
                writeShort((2 and 15 shl 12) or (6 and 15 shl 8) or (4 and 255))
                writeShort((20 shl 4 and 4095) or (5 and 15))
            }
        }
    }
}