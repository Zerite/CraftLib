package dev.zerite.craftlib.protocol.packet.play.server.other

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * The server responds with a list of auto-completions of the last word sent to it.
 * In the case of regular chat, this is a player username.
 * Command names and parameters are also supported.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayTabCompleteTest : PacketTest<ServerPlayTabCompletePacket>(ServerPlayTabCompletePacket) {
    init {
        example(ServerPlayTabCompletePacket(emptyArray())) {
            ProtocolVersion.MC1_7_2 {
                writeVarInt(0)
            }
        }
        example(ServerPlayTabCompletePacket(arrayOf("test", "example"))) {
            ProtocolVersion.MC1_7_2 {
                writeVarInt(2)
                writeString("test")
                writeString("example")
            }
        }
    }
}