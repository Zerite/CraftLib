package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.data.registry.impl.MagicEntityStatus
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to play a specific event or animation for an entity
 * on the client.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayEntityStatusTest : PacketTest<ServerPlayEntityStatusPacket>(ServerPlayEntityStatusPacket) {

    init {
        example(ServerPlayEntityStatusPacket(10, MagicEntityStatus.EATING_ACCEPTED)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(10)
                writeByte(9)
            }
        }
        example(ServerPlayEntityStatusPacket(21, MagicEntityStatus.SHEEP_EATING_GRASS)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(21)
                writeByte(10)
            }
        }
    }

}