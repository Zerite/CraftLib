package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.data.entity.EntityMetadata
import dev.zerite.craftlib.protocol.data.entity.MetadataValue
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Tests that the mob spawning packet is working properly, including testing
 * the data types of fixed point, step rotation and velocity.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlaySpawnMobTest : PacketTest<ServerPlaySpawnMobPacket>(ServerPlaySpawnMobPacket) {

    init {
        example(ServerPlaySpawnMobPacket(0, 0, 0.0, 0.0, 0.0, 0f, 0f, 0f, 0.0, 0.0, 0.0, EntityMetadata(hashMapOf())))
        example(
            ServerPlaySpawnMobPacket(
                127,
                1,
                100.0,
                100.0,
                100.0,
                1f,
                1f,
                1f,
                100.0,
                100.0,
                100.0,
                EntityMetadata(hashMapOf(1 to MetadataValue(1, "example")))
            )
        )
        example(
            ServerPlaySpawnMobPacket(
                127,
                1,
                100.0,
                100.0,
                100.0,
                1f,
                1f,
                1f,
                1.0,
                1.0,
                1.0,
                EntityMetadata(hashMapOf())
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeVarInt(127)
                writeByte(1)

                // Fixed point
                writeInt(3200)
                writeInt(3200)
                writeInt(3200)

                // Rotation
                writeByte(1)
                writeByte(1)
                writeByte(1)

                // Velocity
                writeShort(8000)
                writeShort(8000)
                writeShort(8000)

                // Meta
                writeByte(127)
            }
        }
    }

}