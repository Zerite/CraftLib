package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This packet is sent by the server when an entity moves more than 4 blocks.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayEntityTeleportTest : PacketTest<ServerPlayEntityTeleportPacket>(ServerPlayEntityTeleportPacket) {

    init {
        example(ServerPlayEntityTeleportPacket(200, 0.0, 2.0, 4.0, 90f, 45f)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(200)
                writeInt(0)
                writeInt(2 * 32)
                writeInt(4 * 32)
                writeByte(90)
                writeByte(45)
            }
        }
        example(ServerPlayEntityTeleportPacket(100, 20.0, 20.0, 20.0, 45f, 90f)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(100)
                writeInt(20 * 32)
                writeInt(20 * 32)
                writeInt(20 * 32)
                writeByte(45)
                writeByte(90)
            }
        }
    }

}
