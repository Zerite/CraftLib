package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.Vector3
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.util.ext.toLegacyUUID
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
                0.toLegacyUUID(),
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
                writePosition(Vector3(10, 20, 30))
                writeByte(3)
            }
            ProtocolVersion.MC1_9 {
                writeVarInt(0)
                writeUUID(0.toLegacyUUID(), mode = ProtocolBuffer.UUIDMode.RAW)
                writeString("Example")
                writePosition(Vector3(10, 20, 30))
                writeByte(3)
            }
        }
        example(
            ServerPlaySpawnPaintingPacket(
                100,
                100.toLegacyUUID(),
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
                writePosition(Vector3(20, 24, 28))
                writeByte(1)
            }
            ProtocolVersion.MC1_9 {
                writeVarInt(100)
                writeUUID(100.toLegacyUUID(), mode = ProtocolBuffer.UUIDMode.RAW)
                writeString("Painting")
                writePosition(Vector3(20, 24, 28))
                writeByte(1)
            }
        }
    }
}
