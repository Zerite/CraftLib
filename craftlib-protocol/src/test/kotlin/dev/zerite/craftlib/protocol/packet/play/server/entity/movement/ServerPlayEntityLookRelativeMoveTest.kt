package dev.zerite.craftlib.protocol.packet.play.server.entity.movement

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This packet is sent by the server when an entity rotates and moves. Since a byte
 * range is limited from -128 to 127, and movement is offset of fixed-point numbers,
 * this packet allows at most four blocks movement in any direction. (-128/32 == -4)
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayEntityLookRelativeMoveTest :
    PacketTest<ServerPlayEntityLookRelativeMovePacket>(
        ServerPlayEntityLookRelativeMovePacket
    ) {

    init {
        example(
            ServerPlayEntityLookRelativeMovePacket(
                69,
                1.0,
                1.0,
                1.0,
                45f,
                90f
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeInt(69)
                writeByte(32)
                writeByte(32)
                writeByte(32)
                writeByte(45)
                writeByte(90)
            }
        }
        example(
            ServerPlayEntityLookRelativeMovePacket(
                42,
                2.0,
                2.0,
                2.0,
                100f,
                127f
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeInt(42)
                writeByte(64)
                writeByte(64)
                writeByte(64)
                writeByte(100)
                writeByte(127)
            }
        }
    }
}
