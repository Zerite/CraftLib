package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This is sent to the client when it should update a scoreboard item.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayUpdateScoreTest : PacketTest<ServerPlayUpdateScorePacket>(ServerPlayUpdateScorePacket) {
    init {
        example(ServerPlayUpdateScorePacket("key", true, "score", 5)) {
            ProtocolVersion.MC1_7_2 {
                writeString("key")
                writeByte(0)
                writeString("score")
                writeInt(5)
            }
            ProtocolVersion.MC1_8 {
                writeString("key")
                writeByte(0)
                writeString("score")
                writeVarInt(5)
            }
        }
        example(ServerPlayUpdateScorePacket("removed", false)) {
            ProtocolVersion.MC1_7_2 {
                writeString("removed")
                writeByte(1)
            }
        }
    }
}
