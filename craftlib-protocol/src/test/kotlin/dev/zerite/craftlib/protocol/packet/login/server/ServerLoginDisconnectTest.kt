package dev.zerite.craftlib.protocol.packet.login.server

import dev.zerite.craftlib.chat.dsl.chat
import dev.zerite.craftlib.chat.type.ChatColor
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Tests that the login disconnect packet is being written and read correctly.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerLoginDisconnectTest : PacketTest<ServerLoginDisconnectPacket>(ServerLoginDisconnectPacket) {

    init {
        example(ServerLoginDisconnectPacket(chat { string("Disconnected") }))
        example(ServerLoginDisconnectPacket(chat { string("Quitting") + ChatColor.RED }))
        example(ServerLoginDisconnectPacket(chat { string("Closing connection") })) {
            ProtocolVersion.MC1_7_2 {
                writeChat(chat { string("Closing connection") })
            }
        }
    }

}