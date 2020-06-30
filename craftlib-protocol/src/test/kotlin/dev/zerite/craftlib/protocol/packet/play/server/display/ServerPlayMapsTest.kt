package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * If the first byte of the array is 0, the next two bytes are X start and Y start and
 * the rest of the bytes are the colors in that column.
 *
 * If the first byte of the array is 1, the rest of the bytes are in groups of
 * three: (data, x, y). The lower half of the data is the type (always 0 under vanilla)
 * and the upper half is the direction.
 *
 * If the first byte of the array is 2, the second byte is the map scale.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayMapsTest : PacketTest<ServerPlayMapsPacket>(ServerPlayMapsPacket) {
    init {
        example(ServerPlayMapsPacket(4, byteArrayOf(2, 2))) {
            ProtocolVersion.MC1_7_2 {
                writeVarInt(4)
                writeShort(2)
                writeBytes(byteArrayOf(2, 2))
            }
        }
        example(ServerPlayMapsPacket(0, byteArrayOf(0, 100, 120))) {
            ProtocolVersion.MC1_7_2 {
                writeVarInt(0)
                writeShort(3)
                writeBytes(byteArrayOf(0, 100, 120))
            }
        }
    }
}