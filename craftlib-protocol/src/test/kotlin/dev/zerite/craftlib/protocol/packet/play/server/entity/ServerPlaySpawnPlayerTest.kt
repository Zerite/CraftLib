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
        example(ServerPlaySpawnPlayerPacket(0, UUID(0L, 0L), "Example", 0.0, 0.0, 0.0, 90f, 0f, 2, EntityMetadata()))
        example(
            ServerPlaySpawnPlayerPacket(
                200,
                UUID(100L, 100L),
                "Example",
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
                100,
                UUID(20L, 30L),
                "Player",
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
                writeUUID(UUID(20L, 30L), mode = ProtocolBuffer.UUIDMode.STRING)
                writeString("Player")
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