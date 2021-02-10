package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.ObjectData
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.data.registry.impl.MagicObject
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.util.ext.toLegacyUUID
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server when an object / vehicle is created.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlaySpawnObjectTest : PacketTest<ServerPlaySpawnObjectPacket>(ServerPlaySpawnObjectPacket) {

    init {
        example(
            ServerPlaySpawnObjectPacket(
                0,
                0L.toLegacyUUID(),
                MagicObject.ENDER_CRYSTAL,
                0.0,
                120.0,
                690.0,
                90f,
                45f,
                ObjectData(0)
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeVarInt(0)
                writeByte(51)

                // Fixed point
                writeInt(0)
                writeInt(120 * 32)
                writeInt(690 * 32)

                // Rotations
                writeByte(64)
                writeByte(32)

                // Object data
                writeInt(0)
            }
            ProtocolVersion.MC1_9 {
                writeVarInt(0)
                writeUUID(0L.toLegacyUUID(), mode = ProtocolBuffer.UUIDMode.RAW)
                writeByte(51)

                // Position
                writeDouble(0.0)
                writeDouble(120.0)
                writeDouble(690.0)

                // Rotations
                writeByte(64)
                writeByte(32)

                // Object data
                writeInt(0)
                writeShort(0)
                writeShort(0)
                writeShort(0)
            }
        }
        example(
            ServerPlaySpawnObjectPacket(
                100,
                100L.toLegacyUUID(),
                MagicObject.ARROW,
                0.0,
                120.0,
                690.0,
                90f,
                45f,
                ObjectData(2, 3, 3, 3)
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeVarInt(100)
                writeByte(60)

                // Fixed point
                writeInt(0)
                writeInt(120 * 32)
                writeInt(690 * 32)

                // Rotations
                writeByte(64)
                writeByte(32)

                // Object data
                writeInt(2)
                writeShort(3)
                writeShort(3)
                writeShort(3)
            }
            ProtocolVersion.MC1_9 {
                writeVarInt(100)
                writeUUID(100L.toLegacyUUID(), mode = ProtocolBuffer.UUIDMode.RAW)
                writeByte(60)

                // Fixed point
                writeDouble(0.0)
                writeDouble(120.0)
                writeDouble(690.0)

                // Rotations
                writeByte(64)
                writeByte(32)

                // Object data
                writeInt(2)
                writeShort(3)
                writeShort(3)
                writeShort(3)
            }
        }
    }

}