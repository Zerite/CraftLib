package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.protocol.data.registry.impl.MagicScoreboardAction
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This is sent to the client when it should create a new scoreboard or remove one.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayScoreboardObjectiveTest :
    PacketTest<ServerPlayScoreboardObjectivePacket>(ServerPlayScoreboardObjectivePacket) {
    init {
        example(ServerPlayScoreboardObjectivePacket("name", "value", MagicScoreboardAction.CREATE_SCOREBOARD)) {
            ProtocolVersion.MC1_7_2 {
                writeString("name")
                writeString("value")
                writeByte(0)
            }
        }
        example(ServerPlayScoreboardObjectivePacket("players", "KodingDev", MagicScoreboardAction.UPDATE_TEXT)) {
            ProtocolVersion.MC1_7_2 {
                writeString("players")
                writeString("KodingDev")
                writeByte(2)
            }
        }
    }
}