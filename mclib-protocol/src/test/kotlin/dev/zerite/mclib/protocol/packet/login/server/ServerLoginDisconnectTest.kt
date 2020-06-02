package dev.zerite.mclib.protocol.packet.login.server

import dev.zerite.mclib.chat.component.StringChatComponent
import dev.zerite.mclib.chat.type.ChatColor
import dev.zerite.mclib.protocol.packet.PacketTest
import dev.zerite.mclib.protocol.version.ProtocolVersion

/**
 * Tests that the login disconnect packet is being written and read correctly.
 *
 * TODO: Change over to chat DSL
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerLoginDisconnectTest : PacketTest<ServerLoginDisconnectPacket>(ServerLoginDisconnectPacket) {

    init {
        example(ServerLoginDisconnectPacket(StringChatComponent("Disconnected")))
        example(ServerLoginDisconnectPacket(StringChatComponent("Quitting").apply { color = ChatColor.AQUA }))
        example(ServerLoginDisconnectPacket(StringChatComponent("Closing connection"))) {
            ProtocolVersion.MC1_7_2 {
                writeChat(StringChatComponent("Closing connection"))
            }
        }
    }

}