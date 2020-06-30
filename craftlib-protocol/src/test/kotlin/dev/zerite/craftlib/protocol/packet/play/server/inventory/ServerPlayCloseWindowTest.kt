package dev.zerite.craftlib.protocol.packet.play.server.inventory

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This packet is sent from the server to the client when a window is forcibly closed,
 * such as when a chest is destroyed while it's open.
 *
 * Note, notchian clients send a close window message with window id 0 to close their
 * inventory even though there is never an Open Window message for inventory.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayCloseWindowTest : PacketTest<ServerPlayCloseWindowPacket>(ServerPlayCloseWindowPacket) {
    init {
        example(ServerPlayCloseWindowPacket(69)) {
            ProtocolVersion.MC1_7_2 {
                writeByte(69)
            }
        }
        example(ServerPlayCloseWindowPacket(120)) {
            ProtocolVersion.MC1_7_2 {
                writeByte(120)
            }
        }
    }
}