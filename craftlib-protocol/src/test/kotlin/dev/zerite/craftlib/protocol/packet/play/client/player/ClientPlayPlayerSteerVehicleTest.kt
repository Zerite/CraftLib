package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Steer Vehicle
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */
class ClientPlayPlayerSteerVehicleTest : PacketTest<ClientPlayPlayerSteerVehiclePacket>(ClientPlayPlayerSteerVehiclePacket) {
    init {
        example(ClientPlayPlayerSteerVehiclePacket(2f, 2f, 0)) {
            ProtocolVersion.MC1_7_2 {
                writeFloat(2f)
                writeFloat(2f)
                writeBoolean(false)
                writeBoolean(false)
            }
            ProtocolVersion.MC1_8 {
                writeFloat(2f)
                writeFloat(2f)
                writeByte(0)
            }
        }
        example(ClientPlayPlayerSteerVehiclePacket(0f, 2f, 0x1)) {
            ProtocolVersion.MC1_7_2 {
                writeFloat(0f)
                writeFloat(2f)
                writeBoolean(true)
                writeBoolean(false)
            }
            ProtocolVersion.MC1_8 {
                writeFloat(0f)
                writeFloat(2f)
                writeByte(0x1)
            }
        }
    }
}
