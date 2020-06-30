package dev.zerite.craftlib.protocol.packet.play.server.interaction

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This message is sent from the server to the client whenever a sign is discovered
 * or created. This message is NOT sent when a sign is destroyed or unloaded.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayUpdateSignTest : PacketTest<ServerPlayUpdateSignPacket>(ServerPlayUpdateSignPacket) {
    init {
        example(ServerPlayUpdateSignPacket(10, 100, 50, "first", "second", "third", "forth")) {
            ProtocolVersion.MC1_7_2 {
                writeInt(10)
                writeShort(100)
                writeInt(50)
                writeString("first")
                writeString("second")
                writeString("third")
                writeString("forth")
            }
        }
        example(ServerPlayUpdateSignPacket(50, 50, 50, "line", "line", "line", "line")) {
            ProtocolVersion.MC1_7_2 {
                writeInt(50)
                writeShort(50)
                writeInt(50)
                writeString("line")
                writeString("line")
                writeString("line")
                writeString("line")
            }
        }
    }
}