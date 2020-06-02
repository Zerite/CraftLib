package dev.zerite.mclib.protocol.packet.login.server

import dev.zerite.mclib.protocol.ProtocolBuffer
import dev.zerite.mclib.protocol.packet.PacketTest
import dev.zerite.mclib.protocol.version.ProtocolVersion
import java.util.*

/**
 * Tests that the login success packet from the server is read and written
 * correctly to the buffers.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerLoginSuccessTest : PacketTest<ServerLoginSuccessPacket>(ServerLoginSuccessPacket) {

    init {
        val uuid = UUID.randomUUID()

        example(ServerLoginSuccessPacket(UUID.randomUUID(), "ExampleUser"))
        example(ServerLoginSuccessPacket(UUID.randomUUID(), "TestUser"))
        example(ServerLoginSuccessPacket(uuid, "AuthPlayer")) {
            ProtocolVersion.MC1_7_2 {
                writeUUID(uuid, mode = ProtocolBuffer.UUIDMode.DASHES)
                writeString("AuthPlayer")
            }
        }
    }

}