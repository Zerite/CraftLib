package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This message is sent from the client to the server when the "Done" button is pushed after placing a sign.
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */

class ClientPlayPlayerUpdateSignTest : PacketTest<ClientPlayPlayerUpdateSignPacket>(ClientPlayPlayerUpdateSignPacket) {
    init {
        example(ClientPlayPlayerUpdateSignPacket(130, 90, 130, "This", "is","a", "Test")) {
            ProtocolVersion.MC1_7_2 {
                writeInt(130)
                writeShort(90)
                writeInt(130)
                writeString("This")
                writeString("is")
                writeString("a")
                writeString("Test")
            }
        }
    }
}