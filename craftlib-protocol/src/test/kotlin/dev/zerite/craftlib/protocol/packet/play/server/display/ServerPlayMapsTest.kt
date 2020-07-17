package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Reads map data containing info about players and landmarks.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
class ServerPlayMapsTest : PacketTest<ServerPlayMapsPacket>(ServerPlayMapsPacket) {
    init {
        example(
            ServerPlayMapsPacket(10, 1, arrayOf(ServerPlayMapsPacket.PlayerIcon(2, 2, 2, 2)), 0),
            minimumVersion = ProtocolVersion.MC1_8
        ) {
            ProtocolVersion.MC1_8 {
                writeVarInt(10)
                writeByte(1)

                // Players
                writeVarInt(1)
                writeByte(((2 and 0xF) shl 4) or (2 and 0xF))
                writeByte(2)
                writeByte(2)

                writeByte(0)
            }
        }
        example(
            ServerPlayMapsPacket(
                10,
                2,
                arrayOf(ServerPlayMapsPacket.PlayerIcon(2, 2, 2, 2)),
                3,
                3,
                3,
                3,
                3,
                ByteArray(0)
            ), minimumVersion = ProtocolVersion.MC1_8
        ) {
            ProtocolVersion.MC1_8 {
                writeVarInt(10)
                writeByte(2)

                // Players
                writeVarInt(1)
                writeByte(((2 and 0xF) shl 4) or (2 and 0xF))
                writeByte(2)
                writeByte(2)

                writeByte(3)
                writeByte(3)
                writeByte(3)
                writeByte(3)
                writeVarInt(3)
                writeVarInt(0)
            }
        }
    }
}
