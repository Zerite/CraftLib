package dev.zerite.craftlib.protocol.packet.play.client.other

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * The server will frequently send out a keep-alive, each containing a random ID.
 * The client must respond with the same packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ClientPlayKeepAliveTest : PacketTest<ClientPlayKeepAlivePacket>(ClientPlayKeepAlivePacket) {
    init {
        example(ClientPlayKeepAlivePacket(10)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(10)
            }
        }
        example(ClientPlayKeepAlivePacket(Int.MAX_VALUE)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(Int.MAX_VALUE)
            }
        }
    }
}
