package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * A combination of Player Look and Player position.
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */
class ClientPlayPlayerPositionLookTest : PacketTest<ClientPlayPlayerPositionLookPacket>(ClientPlayPlayerPositionLookPacket) {
    init {
        example(ClientPlayPlayerPositionLookPacket(50.0, 100.0, 101.5, 150.0, 90f, 180f, true)) {
            ProtocolVersion.MC1_7_2 {
                writeDouble(50.0)
                writeDouble(100.0)
                writeDouble(101.5)
                writeDouble(150.0)
                writeFloat(90f)
                writeFloat(180f)
                writeBoolean(true)
            }
            ProtocolVersion.MC1_8 {
                writeDouble(50.0)
                writeDouble(100.0)
                writeDouble(150.0)
                writeFloat(90f)
                writeFloat(180f)
                writeBoolean(true)
            }
        }
        example(ClientPlayPlayerPositionLookPacket(0.0, 10.0, 11.5, Double.MIN_VALUE, 90f, 180f, false)) {
            ProtocolVersion.MC1_7_2 {
                writeDouble(0.0)
                writeDouble(10.0)
                writeDouble(11.5)
                writeDouble(Double.MIN_VALUE)
                writeFloat(90f)
                writeFloat(180f)
                writeBoolean(false)
            }
            ProtocolVersion.MC1_8 {
                writeDouble(0.0)
                writeDouble(10.0)
                writeDouble(Double.MIN_VALUE)
                writeFloat(90f)
                writeFloat(180f)
                writeBoolean(false)
            }
        }
    }
}
