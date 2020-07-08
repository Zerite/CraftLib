package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Steer Vehicle
 *
 * @author chachy
 * @since 0.1.0-SNAPSHOT
 */

class ClientPlayPlayerSteerVehicleTest : PacketTest<ClientPlayPlayerSteerVehiclePacket>(ClientPlayPlayerSteerVehiclePacket) {
    init {
        example(ClientPlayPlayerSteerVehiclePacket(2f, 2f, false, unMount = false)) {
            ProtocolVersion.MC1_7_2 {
                writeFloat(2f)
                writeFloat(2f)
                writeBoolean(false)
                writeBoolean(false)
            }
        }


        example(ClientPlayPlayerSteerVehiclePacket(0f, 2f, true, unMount = true)) {
            ProtocolVersion.MC1_7_2 {
                writeFloat(0f)
                writeFloat(2f)
                writeBoolean(true)
                writeBoolean(true)
            }
        }
    }
}