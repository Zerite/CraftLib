package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This packet is sent when a player has been attached to an entity (e.g. Minecart)
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayAttachEntityTest : PacketTest<ServerPlayAttachEntityPacket>(ServerPlayAttachEntityPacket) {
    init {
        example(ServerPlayAttachEntityPacket(150, 120, false)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(150)
                writeInt(120)
                writeBoolean(false)
            }
        }
        example(ServerPlayAttachEntityPacket(120, 150, true)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(120)
                writeInt(150)
                writeBoolean(true)
            }
        }
    }
}