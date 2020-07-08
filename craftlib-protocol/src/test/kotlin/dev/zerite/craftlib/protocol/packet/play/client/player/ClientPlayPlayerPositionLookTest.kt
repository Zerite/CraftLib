package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * A combination of Player Look and Player position.
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */

class ClientPlayPlayerPositionLookTest : PacketTest<ClientPlayPlayerPositionLookPacketPacket>(ClientPlayPlayerPositionLookPacketPacket) {
    init {
        example(ClientPlayPlayerPositionLookPacketPacket(50.0, 100.0, 0.2, 150.0, 90f, 180f, true)) {
            ProtocolVersion.MC1_7_2 {
                writeDouble(50.0)
                writeDouble(100.0)
                writeDouble(0.2)
                writeDouble(150.0)
                writeFloat(90f)
                writeFloat(180f)
                writeBoolean(true)
            }
        }

        example(ClientPlayPlayerPositionLookPacketPacket(0.0, Double.MAX_VALUE, 0.5, Double.MIN_VALUE, 90f, 180f, false)) {
            ProtocolVersion.MC1_7_2 {
                writeDouble(0.0)
                writeDouble(Double.MAX_VALUE)
                writeDouble(0.5)
                writeDouble(Double.MIN_VALUE)
                writeFloat(90f)
                writeFloat(180f)
                writeBoolean(false)
            }
        }
    }
}