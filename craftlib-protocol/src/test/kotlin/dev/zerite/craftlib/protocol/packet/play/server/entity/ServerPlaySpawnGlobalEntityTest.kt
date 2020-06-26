package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import kotlin.math.roundToInt

/**
 * With this packet, the server notifies the client of thunderbolts striking
 * within a 512 block radius around the player.
 * The coordinates specify where exactly the thunderbolt strikes.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlaySpawnGlobalEntityTest :
    PacketTest<ServerPlaySpawnGlobalEntityPacket>(ServerPlaySpawnGlobalEntityPacket) {
    init {
        example(ServerPlaySpawnGlobalEntityPacket(400, 123, 53.0, 74.0, 625.0)) {
            ProtocolVersion.MC1_7_2 {
                writeVarInt(400)
                writeByte(123)
                writeInt((53.0 * 32).roundToInt())
                writeInt((74.0 * 32).roundToInt())
                writeInt((625.0 * 32).roundToInt())
            }
        }
        example(ServerPlaySpawnGlobalEntityPacket(1, 1, 1.0, 1.0, 1.0)) {
            ProtocolVersion.MC1_7_2 {
                writeVarInt(1)
                writeByte(1)
                writeInt(32)
                writeInt(32)
                writeInt(32)
            }
        }
    }
}
