package dev.zerite.craftlib.protocol.packet.play.server.entity.movement

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Tests that the entity head look packet is encoded correctly.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayEntityHeadLookTest : PacketTest<ServerPlayEntityHeadLookPacket>(
    ServerPlayEntityHeadLookPacket
) {

    init {
        example(
            ServerPlayEntityHeadLookPacket(
                0,
                0f
            )
        )
        example(
            ServerPlayEntityHeadLookPacket(
                256,
                90f
            )
        )
        example(
            ServerPlayEntityHeadLookPacket(
                42,
                45f
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeInt(42)
                writeByte(32)
            }
        }
    }

}