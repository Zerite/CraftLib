package dev.zerite.craftlib.protocol.packet.play.server.interaction

import dev.zerite.craftlib.protocol.Vector3
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This packet tells that a player goes to bed.
 * The client with the matching Entity ID will go into bed mode.
 * This Packet is sent to all nearby players including the one sent to bed.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayUseBedTest : PacketTest<ServerPlayUseBedPacket>(ServerPlayUseBedPacket) {

    init {
        example(ServerPlayUseBedPacket(0, 0, 0, 0))
        example(ServerPlayUseBedPacket(127, 100, 100, 400))
        example(ServerPlayUseBedPacket(100, 100, 100, 100)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(100)
                writeInt(100)
                writeByte(100)
                writeInt(100)
            }
            ProtocolVersion.MC1_8 {
                writeVarInt(100)
                writePosition(Vector3(100, 100, 100))
            }
        }
    }

}
