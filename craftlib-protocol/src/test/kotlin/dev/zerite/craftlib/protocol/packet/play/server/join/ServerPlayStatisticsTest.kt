package dev.zerite.craftlib.protocol.packet.play.server.join

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Tests whether the server play statistics packet has proper IO.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayStatisticsTest : PacketTest<ServerPlayStatisticsPacket>(ServerPlayStatisticsPacket) {

    init {
        example(ServerPlayStatisticsPacket(emptyArray()))
        example(ServerPlayStatisticsPacket(arrayOf(ServerPlayStatisticsPacket.Statistic("example", 10))))
        example(ServerPlayStatisticsPacket(arrayOf(ServerPlayStatisticsPacket.Statistic("stat", 21)))) {
            ProtocolVersion.MC1_7_2 {
                writeVarInt(1)
                writeString("stat")
                writeVarInt(21)
            }
        }
    }

}