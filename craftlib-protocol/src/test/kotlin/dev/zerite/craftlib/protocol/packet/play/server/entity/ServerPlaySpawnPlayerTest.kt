package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.data.entity.EntityMetadata
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import java.util.*

/**
 * This packet is sent by the server when a player comes into visible range, not when a player joins.
 * Servers can, however, safely spawn player entities for players not in visible range.
 * The client appears to handle it correctly.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlaySpawnPlayerTest : PacketTest<ServerPlaySpawnPlayerPacket>(ServerPlaySpawnPlayerPacket) {

    init {
        example(
            ServerPlaySpawnPlayerPacket(
                0,
                UUID(0L, 0L),
                "Player",
                hashMapOf(),
                0.0,
                0.0,
                0.0,
                90f,
                0f,
                0,
                EntityMetadata()
            )
        )
        example(
            ServerPlaySpawnPlayerPacket(
                0,
                UUID(0L, 0L),
                "Player",
                hashMapOf(),
                0.0,
                0.0,
                0.0,
                90f,
                0f,
                2,
                EntityMetadata()
            ), maximumVersion = ProtocolVersion.MC1_8
        )
        example(
            ServerPlaySpawnPlayerPacket(
                200,
                UUID(100L, 100L),
                "Player",
                hashMapOf(),
                80.0,
                160.0,
                200.0,
                45f,
                0f,
                0,
                EntityMetadata()
            )
        )
        example(
            ServerPlaySpawnPlayerPacket(
                200,
                UUID(100L, 100L),
                "Example",
                hashMapOf(
                    "TestDataEntry" to ServerPlaySpawnPlayerPacket.DataEntry("TestDataValue", "TestSignature")
                ),
                80.0,
                160.0,
                200.0,
                45f,
                0f,
                0,
                EntityMetadata()
            ),
            minimumVersion = ProtocolVersion.MC1_7_6
        )
        example(
            ServerPlaySpawnPlayerPacket(
                100,
                UUID(20L, 30L),
                "Player",
                hashMapOf(),
                40.0,
                40.0,
                40.0,
                45f,
                90f,
                0,
                EntityMetadata()
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeVarInt(100)
                writeUUID(UUID(20L, 30L), mode = ProtocolBuffer.UUIDMode.DASHES)
                writeString("Player")
                writeInt(1280)
                writeInt(1280)
                writeInt(1280)
                writeByte(32)
                writeByte(64)
                writeShort(0)
                writeByte(127)
            }
            ProtocolVersion.MC1_7_6 {
                writeVarInt(100)
                writeUUID(UUID(20L, 30L), mode = ProtocolBuffer.UUIDMode.STRING)
                writeString("Player")
                writeVarInt(0)
                writeInt(1280)
                writeInt(1280)
                writeInt(1280)
                writeByte(32)
                writeByte(64)
                writeShort(0)
                writeByte(127)
            }
            ProtocolVersion.MC1_8 {
                writeVarInt(100)
                writeUUID(UUID(20L, 30L), mode = ProtocolBuffer.UUIDMode.RAW)
                writeInt(1280)
                writeInt(1280)
                writeInt(1280)
                writeByte(32)
                writeByte(64)
                writeShort(0)
                writeByte(127)
            }
            ProtocolVersion.MC1_9 {
                writeVarInt(100)
                writeUUID(UUID(20L, 30L), mode = ProtocolBuffer.UUIDMode.RAW)
                writeDouble(40.0)
                writeDouble(40.0)
                writeDouble(40.0)
                writeByte(32)
                writeByte(64)
                writeByte(127)
            }
        }
        example(
            ServerPlaySpawnPlayerPacket(
                100,
                UUID(20L, 30L),
                "Player",
                hashMapOf(
                    "TestDataEntry" to ServerPlaySpawnPlayerPacket.DataEntry("TestValue", "TestSignature"),
                    "TestDataEntry2" to ServerPlaySpawnPlayerPacket.DataEntry("TestValue2", "TestSignature2")
                ),
                40.0,
                40.0,
                40.0,
                45f,
                90f,
                0,
                EntityMetadata()
            ),
            minimumVersion = ProtocolVersion.MC1_7_2,
            maximumVersion = ProtocolVersion.MC1_7_6
        ) {
            ProtocolVersion.MC1_7_6 {
                writeVarInt(100)
                writeUUID(UUID(20L, 30L), mode = ProtocolBuffer.UUIDMode.STRING)
                writeString("Player")
                writeVarInt(2)
                writeString("TestDataEntry2")
                writeString("TestValue2")
                writeString("TestSignature2")
                writeString("TestDataEntry")
                writeString("TestValue")
                writeString("TestSignature")
                writeInt(1280)
                writeInt(1280)
                writeInt(1280)
                writeByte(32)
                writeByte(64)
                writeShort(0)
                writeByte(127)
            }
        }
    }

}
