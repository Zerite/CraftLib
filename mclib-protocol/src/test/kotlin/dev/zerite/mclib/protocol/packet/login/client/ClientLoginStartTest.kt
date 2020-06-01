package dev.zerite.mclib.protocol.packet.login.client

import dev.zerite.mclib.protocol.packet.PacketTest
import dev.zerite.mclib.protocol.version.ProtocolVersion

/**
 * Tests the login start packet which tells the server we need to begin
 * authenticating to start play.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ClientLoginStartTest : PacketTest<ClientLoginStartPacket>(ClientLoginStartPacket) {

    init {
        example(ClientLoginStartPacket("Example"))
        example(ClientLoginStartPacket("TestUser42"))
        example(ClientLoginStartPacket("TestAuthUser")) {
            ProtocolVersion.MC1_7_2 {
                writeString("TestAuthUser")
            }
        }
    }

}