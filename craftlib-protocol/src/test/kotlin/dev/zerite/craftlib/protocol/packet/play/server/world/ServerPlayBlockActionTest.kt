package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This packet is used for a number of things:
 *  - Chests opening and closing
 *  - Pistons pushing and pulling
 *  - Note blocks playing
 *
 *  @author Koding
 *  @since  0.1.0-SNAPSHOT
 */
class ServerPlayBlockActionTest : PacketTest<ServerPlayBlockActionPacket>(ServerPlayBlockActionPacket) {
    init {
        example(ServerPlayBlockActionPacket(100, 100, 100, 2, 4, 1)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(100)
                writeShort(100)
                writeInt(100)
                writeByte(2)
                writeByte(4)
                writeVarInt(1)
            }
        }
        example(ServerPlayBlockActionPacket(50, 80, 110, 4, 8, 0)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(50)
                writeShort(80)
                writeInt(110)
                writeByte(4)
                writeByte(8)
                writeVarInt(0)
            }
        }
    }
}