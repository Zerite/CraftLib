package dev.zerite.craftlib.protocol.packet.play.client.interaction

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This packet is sent from the client to the server when the client attacks or
 * right-clicks another entity (a player, minecart, etc).
 *
 * A Notchian server only accepts this packet if the entity being attacked/used is visible
 * without obstruction and within a 4-unit radius of the player's position.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ClientPlayUseEntityTest : PacketTest<ClientPlayUseEntityPacket>(ClientPlayUseEntityPacket) {
    init {
        example(ClientPlayUseEntityPacket(100, 1)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(100)
                writeByte(1)
            }
        }
        example(ClientPlayUseEntityPacket(420, 0)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(420)
                writeByte(0)
            }
        }
    }
}
