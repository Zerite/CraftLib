package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.chat.component.StringChatComponent
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Updates the player list header and footer for the client.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
class ServerPlayPlayerListHeaderFooterTest :
    PacketTest<ServerPlayPlayerListHeaderFooterPacket>(ServerPlayPlayerListHeaderFooterPacket) {
    init {
        example(ServerPlayPlayerListHeaderFooterPacket(StringChatComponent("example"), StringChatComponent("text"))) {
            ProtocolVersion.MC1_8 {
                writeString("{\"text\":\"example\"}")
                writeString("{\"text\":\"text\"}")
            }
        }
    }
}
