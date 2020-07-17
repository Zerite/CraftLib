package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This packet shows location, name, and type of painting.
 * Calculating the center of an image: given a (width x height) grid of cells, with (0, 0) being
 * the top left corner, the center is (max(0, width / 2 - 1), height / 2).
 * E.g. 2x1 (1, 0) 4x4 (1, 2)
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlaySpawnPaintingTest : PacketTest<ServerPlaySpawnPaintingPacket>(ServerPlaySpawnPaintingPacket) {
    init {
        example(
            ServerPlaySpawnPaintingPacket(
                0,
                "Example",
                10,
                20,
                30,
                ServerPlaySpawnPaintingPacket.Direction.POSITIVE_X
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeVarInt(0)
                writeString("Example")
                writeInt(10)
                writeInt(20)
                writeInt(30)
                writeInt(3)
            }
            ProtocolVersion.MC1_8 {
                writeVarInt(0)
                writeString("Example")
                writeLong(((10L and 0x3FFFFFFL) shl 38) or ((30L and 0x3FFFFFFL) shl 12) or (20L and 0xFFFL))
                writeByte(3)
            }
        }
        example(
            ServerPlaySpawnPaintingPacket(
                100,
                "Painting",
                20,
                24,
                28,
                ServerPlaySpawnPaintingPacket.Direction.NEGATIVE_X
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeVarInt(100)
                writeString("Painting")
                writeInt(20)
                writeInt(24)
                writeInt(28)
                writeInt(1)
            }
            ProtocolVersion.MC1_8 {
                writeVarInt(100)
                writeString("Painting")
                writeLong(((20L and 0x3FFFFFFL) shl 38) or ((28L and 0x3FFFFFFL) shl 12) or (24L and 0xFFFL))
                writeByte(1)
            }
        }
    }
}
