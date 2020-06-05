package dev.zerite.mclib.protocol.packet.play.server.display

import dev.zerite.mclib.chat.dsl.chat
import dev.zerite.mclib.chat.type.ChatColor
import dev.zerite.mclib.protocol.packet.PacketTest
import dev.zerite.mclib.protocol.version.ProtocolVersion

/**
 * Tests that the chat message packet is properly reading and writing the
 * raw message JSON.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayChatMessageTest : PacketTest<ServerPlayChatMessagePacket>(ServerPlayChatMessagePacket) {

    init {
        example(ServerPlayChatMessagePacket(chat { string("placeholder") }))
        example(ServerPlayChatMessagePacket(chat { string("Sample Text") }))
        example(ServerPlayChatMessagePacket(chat { string("writing test") + ChatColor.BLUE })) {
            ProtocolVersion.MC1_7_2 {
                writeChat(chat { string("writing test") + ChatColor.BLUE })
            }
        }
    }

}