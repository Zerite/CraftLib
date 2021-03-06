package dev.zerite.craftlib.protocol.packet.play.client.inventory

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.packet.play.client.inventory.ClientPlayCloseWindowPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This packet is sent by the client when closing a window.
 *
 * Note, notchian clients send a close window message with window id 0 to close their inventory even though there is never an Open Window message for inventory.
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */
class ClientPlayCloseWindowTest : PacketTest<ClientPlayCloseWindowPacket>(ClientPlayCloseWindowPacket) {
    init {
        example(ClientPlayCloseWindowPacket(0)) {
            ProtocolVersion.MC1_7_2 {
                writeByte(0)
            }
        }
    }
}
