package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to indicate a single block change to
 * the client.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayBlockChangeTest : PacketTest<ServerPlayBlockChangePacket>(ServerPlayBlockChangePacket) {
    init {
        example(ServerPlayBlockChangePacket(20, 20, 20, 0, 0)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(20)
                writeByte(20)
                writeInt(20)
                writeVarInt(0)
                writeByte(0)
            }
            ProtocolVersion.MC1_8 {
                writeLong(((20L and 0x3FFFFFFL) shl 38) or ((20L and 0x3FFFFFFL) shl 12) or (20L and 0xFFFL))
                writeVarInt(0)
            }
        }
        example(ServerPlayBlockChangePacket(10, 40, 30, 2, 6)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(10)
                writeByte(40)
                writeInt(30)
                writeVarInt(2)
                writeByte(6)
            }
            ProtocolVersion.MC1_8 {
                writeLong(((10L and 0x3FFFFFFL) shl 38) or ((30L and 0x3FFFFFFL) shl 12) or (40L and 0xFFFL))
                writeVarInt((2 shl 4) or (6 and 0xF))
            }
        }
    }
}
