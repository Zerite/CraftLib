package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Spawns one or more experience orbs.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlaySpawnExperienceOrbTest : PacketTest<ServerPlaySpawnExperienceOrbPacket>(ServerPlaySpawnExperienceOrbPacket) {

    init {
        example(ServerPlaySpawnExperienceOrbPacket(120, 0.0, 20.0, 40.0, 60)) {
            ProtocolVersion.MC1_7_2 {
                writeVarInt(120)
                writeInt(0)
                writeInt(20 * 32)
                writeInt(40 * 32)
                writeShort(60)
            }
            ProtocolVersion.MC1_9 {
                writeVarInt(120)
                writeDouble(0.0)
                writeDouble(20.0)
                writeDouble(40.0)
                writeShort(60)
            }
        }
        example(ServerPlaySpawnExperienceOrbPacket(80, 10.0, 30.0, 50.0, 42)) {
            ProtocolVersion.MC1_7_2 {
                writeVarInt(80)
                writeInt(10 * 32)
                writeInt(30 * 32)
                writeInt(50 * 32)
                writeShort(42)
            }
            ProtocolVersion.MC1_9 {
                writeVarInt(80)
                writeDouble(10.0)
                writeDouble(30.0)
                writeDouble(50.0)
                writeShort(42)
            }
        }
    }

}
