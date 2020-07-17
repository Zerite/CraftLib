package dev.zerite.craftlib.protocol.packet.play.client.other

import dev.zerite.craftlib.chat.component.StringChatComponent
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This message is sent from the client to the server when the "Done" button is pushed after placing a sign.
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */
class ClientPlayUpdateSignTest : PacketTest<ClientPlayUpdateSignPacket>(ClientPlayUpdateSignPacket) {
    init {
        example(
            ClientPlayUpdateSignPacket(
                130,
                90,
                130,
                StringChatComponent("This"),
                StringChatComponent("is"),
                StringChatComponent("a"),
                StringChatComponent("Test")
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeInt(130)
                writeShort(90)
                writeInt(130)
                writeString("This")
                writeString("is")
                writeString("a")
                writeString("Test")
            }
            ProtocolVersion.MC1_8 {
                writeLong(((130L and 0x3FFFFFFL) shl 38) or ((130L and 0x3FFFFFFL) shl 12) or (90L and 0xFFFL))
                writeString("{\"text\":\"This\"}")
                writeString("{\"text\":\"is\"}")
                writeString("{\"text\":\"a\"}")
                writeString("{\"text\":\"Test\"}")
            }
        }
    }
}
