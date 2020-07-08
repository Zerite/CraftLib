package dev.zerite.craftlib.protocol.packet.play.client.display

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * The default server will check the message to see if it begins with a '/'.
 * If it doesn't, the username of the sender is prepended and sent to all other clients
 * (including the original sender). If it does, the server assumes it to be a command and attempts to process it.
 *
 * A message longer than 100 characters will cause the server to kick the client.
 * This change was initially done by allowing the client to not slice the message up to 119 (the previous limit),
 * without changes to the server. For this reason, the vanilla server kept the code to cut messages at 119, but
 * this isn't a protocol limitation and can be ignored.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ClientPlayChatMessageTest : PacketTest<ClientPlayChatMessagePacket>(ClientPlayChatMessagePacket) {
    init {
        example(ClientPlayChatMessagePacket("chat")) {
            ProtocolVersion.MC1_7_2 {
                writeString("chat")
            }
        }
        example(ClientPlayChatMessagePacket("VariedChatMessage!!??..")) {
            ProtocolVersion.MC1_7_2 {
                writeString("VariedChatMessage!!??..")
            }
        }
    }
}
