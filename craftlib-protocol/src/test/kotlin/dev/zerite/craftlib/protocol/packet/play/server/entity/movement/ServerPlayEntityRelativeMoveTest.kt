package dev.zerite.craftlib.protocol.packet.play.server.entity.movement

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Tests that entity relative movement is working correctly.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayEntityRelativeMoveTest :
    PacketTest<ServerPlayEntityRelativeMovePacket>(
        ServerPlayEntityRelativeMovePacket
    ) {

    init {
        example(
            ServerPlayEntityRelativeMovePacket(
                0,
                3.5,
                3.5,
                3.5
            )
        )
        example(
            ServerPlayEntityRelativeMovePacket(
                127,
                1.5,
                1.6875,
                1.875
            )
        )
        example(
            ServerPlayEntityRelativeMovePacket(
                200,
                3.0,
                3.0,
                3.0
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeInt(200)
                writeByte(96)
                writeByte(96)
                writeByte(96)
            }
            ProtocolVersion.MC1_8 {
                writeVarInt(200)
                writeByte(96)
                writeByte(96)
                writeByte(96)
                writeBoolean(false)
            }
        }
    }

}
