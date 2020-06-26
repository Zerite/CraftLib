package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent when an explosion occurs (creepers, TNT, and ghast fireballs).
 * Each block in Records is set to air. Coordinates for each axis in record is int(X) + record.x
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayExplosionTest : PacketTest<ServerPlayExplosionPacket>(ServerPlayExplosionPacket) {
    init {
        example(ServerPlayExplosionPacket(0f, 10f, 20f, 5f, emptyArray(), 1f, 1f, 1f)) {
            ProtocolVersion.MC1_7_2 {
                writeFloat(0f)
                writeFloat(10f)
                writeFloat(20f)
                writeFloat(5f)
                writeInt(0)
                writeFloat(1f)
                writeFloat(1f)
                writeFloat(1f)
            }
        }
        example(
            ServerPlayExplosionPacket(
                20f,
                10f,
                0f,
                2f,
                arrayOf(ServerPlayExplosionPacket.Record(2, 4, 6)),
                2f,
                3f,
                5f
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeFloat(20f)
                writeFloat(10f)
                writeFloat(0f)
                writeFloat(2f)

                // Array
                writeInt(1)
                writeByte(2)
                writeByte(4)
                writeByte(6)

                writeFloat(2f)
                writeFloat(3f)
                writeFloat(5f)
            }
        }
    }
}