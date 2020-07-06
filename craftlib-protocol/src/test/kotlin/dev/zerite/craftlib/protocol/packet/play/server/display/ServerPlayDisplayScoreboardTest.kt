package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.protocol.data.registry.impl.MagicScoreboardPosition
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This is sent to the client when it should display a scoreboard.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayDisplayScoreboardTest :
    PacketTest<ServerPlayDisplayScoreboardPacket>(ServerPlayDisplayScoreboardPacket) {
    init {
        example(ServerPlayDisplayScoreboardPacket(MagicScoreboardPosition.LIST, "scoreboard")) {
            ProtocolVersion.MC1_7_2 {
                writeByte(0)
                writeString("scoreboard")
            }
        }
        example(ServerPlayDisplayScoreboardPacket(MagicScoreboardPosition.BELOW_NAME, "below")) {
            ProtocolVersion.MC1_7_2 {
                writeByte(2)
                writeString("below")
            }
        }
    }
}