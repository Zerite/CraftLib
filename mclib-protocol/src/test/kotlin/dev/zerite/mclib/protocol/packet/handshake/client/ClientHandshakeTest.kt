package dev.zerite.mclib.protocol.packet.handshake.client

import dev.zerite.mclib.protocol.packet.PacketTest
import dev.zerite.mclib.protocol.version.MinecraftProtocol
import dev.zerite.mclib.protocol.version.ProtocolVersion
import kotlin.test.Test
import kotlin.test.assertFails

/**
 * Tests the reading and writing of the client handshake packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ClientHandshakeTest : PacketTest<ClientHandshakePacket>(ClientHandshakePacket) {

    init {
        example(ClientHandshakePacket(ProtocolVersion.MC1_7_2, "localhost", 25565, MinecraftProtocol.LOGIN))
        example(ClientHandshakePacket(ProtocolVersion.MC1_12, "example.local", 25577, MinecraftProtocol.STATUS))
        example(ClientHandshakePacket(ProtocolVersion.MC1_15_2, "127.0.0.1", 25565, MinecraftProtocol.LOGIN)) {
            ProtocolVersion.MC1_7_2 {
                writeVarInt(578)
                writeString("127.0.0.1")
                writeShort(25565)
                writeVarInt(2)
            }
        }
    }

    @Test
    fun `Test Packet Validation`() {
        assertFails("Invalid protocol state not caught") {
            ClientHandshakePacket(
                ProtocolVersion.UNKNOWN,
                "localhost",
                25565,
                MinecraftProtocol.HANDSHAKE
            )
        }
    }

}