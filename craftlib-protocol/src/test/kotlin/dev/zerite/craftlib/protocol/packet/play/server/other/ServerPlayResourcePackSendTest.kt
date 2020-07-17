package dev.zerite.craftlib.protocol.packet.play.server.other

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent to the client to force them to load a resource pack.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
class ServerPlayResourcePackSendTest : PacketTest<ServerPlayResourcePackSendPacket>(ServerPlayResourcePackSendPacket) {
    init {
        example(ServerPlayResourcePackSendPacket("url", "hash")) {
            ProtocolVersion.MC1_8 {
                writeString("url")
                writeString("hash")
            }
        }
    }
}
