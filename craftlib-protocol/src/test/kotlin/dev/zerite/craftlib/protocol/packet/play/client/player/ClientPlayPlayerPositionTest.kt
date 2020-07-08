package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Updates the players XYZ position on the server. If Stance - Y is less than 0.1 or greater than 1.65,
 * the stance is illegal and the client will be kicked with the message “Illegal Stance”.
 *
 * If the distance between the last known position of the player on the server and the new position set by
 * this packet is greater than 100 units will result in the client being kicked for
 * "You moved too quickly :( (Hacking?)"
 *
 * Also if the fixed-point number of X or Z is set greater than 3.2E7D the client
 * will be kicked for "Illegal position"
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ClientPlayPlayerPositionTest : PacketTest<ClientPlayPlayerPositionPacket>(ClientPlayPlayerPositionPacket) {
    init {
        example(ClientPlayPlayerPositionPacket(50.0, 100.0, 0.2, 150.0, true)) {
            ProtocolVersion.MC1_7_2 {
                writeDouble(50.0)
                writeDouble(100.0)
                writeDouble(0.2)
                writeDouble(150.0)
                writeBoolean(true)
            }
        }
        example(ClientPlayPlayerPositionPacket(0.0, Double.MAX_VALUE, 0.5, Double.MIN_VALUE, false)) {
            ProtocolVersion.MC1_7_2 {
                writeDouble(0.0)
                writeDouble(Double.MAX_VALUE)
                writeDouble(0.5)
                writeDouble(Double.MIN_VALUE)
                writeBoolean(false)
            }
        }
    }
}
