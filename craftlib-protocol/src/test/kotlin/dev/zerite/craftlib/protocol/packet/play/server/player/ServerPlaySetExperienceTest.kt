package dev.zerite.craftlib.protocol.packet.play.server.player

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server when the client should change experience levels.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlaySetExperienceTest : PacketTest<ServerPlaySetExperiencePacket>(ServerPlaySetExperiencePacket) {
    init {
        example(ServerPlaySetExperiencePacket(1f, 5, 200)) {
            ProtocolVersion.MC1_7_2 {
                writeFloat(1f)
                writeShort(5)
                writeShort(200)
            }
            ProtocolVersion.MC1_8 {
                writeFloat(1f)
                writeVarInt(5)
                writeVarInt(200)
            }
        }
        example(ServerPlaySetExperiencePacket(0.5f, 10, 10)) {
            ProtocolVersion.MC1_7_2 {
                writeFloat(0.5f)
                writeShort(10)
                writeShort(10)
            }
            ProtocolVersion.MC1_8 {
                writeFloat(0.5f)
                writeVarInt(10)
                writeVarInt(10)
            }
        }
    }
}
