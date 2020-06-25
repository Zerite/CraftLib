package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server when an list of entities is to be destroyed on the client.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayDestroyEntitiesTest : PacketTest<ServerPlayDestroyEntitiesPacket>(ServerPlayDestroyEntitiesPacket) {

    init {
        example(ServerPlayDestroyEntitiesPacket(IntArray(0))) {
            ProtocolVersion.MC1_7_2 {
                writeByte(0)
            }
        }
        example(ServerPlayDestroyEntitiesPacket(IntArray(3) { it })) {
            ProtocolVersion.MC1_7_2 {
                writeByte(3)
                writeInt(0)
                writeInt(1)
                writeInt(2)
            }
        }
    }

}
