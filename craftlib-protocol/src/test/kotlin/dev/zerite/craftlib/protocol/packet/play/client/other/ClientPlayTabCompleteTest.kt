package dev.zerite.craftlib.protocol.packet.play.client.other

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent when the user presses \[tab] while writing text. The payload contains all text behind the cursor.
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */
class ClientPlayTabCompleteTest : PacketTest<ClientPlayTabCompletePacket>(ClientPlayTabCompletePacket) {
    init {
        example(ClientPlayTabCompletePacket("complete")) {
            ProtocolVersion.MC1_7_2 {
                writeString("complete")
            }
        }
    }
}
