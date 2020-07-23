package dev.zerite.craftlib.protocol.version

import dev.zerite.craftlib.protocol.packet.handshake.client.ClientHandshakePacket
import dev.zerite.craftlib.protocol.packet.status.client.ClientStatusRequestPacket
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ProtocolStateTest {

    @Test
    fun `Test Getting States`() {
        val state = ProtocolState("Example", 1)
        assertEquals(state.clientbound, state[PacketDirection.CLIENTBOUND])
        assertEquals(state.serverbound, state[PacketDirection.SERVERBOUND])

        assertEquals(MinecraftProtocol.HANDSHAKE.clientbound, MinecraftProtocol.HANDSHAKE[PacketDirection.CLIENTBOUND])
        assertEquals(MinecraftProtocol.HANDSHAKE.serverbound, MinecraftProtocol.HANDSHAKE[PacketDirection.SERVERBOUND])
        assertEquals(MinecraftProtocol.HANDSHAKE, MinecraftProtocol[-1])
    }

    @Test
    fun `Test State Fails`() {
        val state = ProtocolState("Example", 1)
        assertNull(state[PacketDirection.CLIENTBOUND][ProtocolVersion.UNKNOWN, -1])
        assertNull(state[PacketDirection.CLIENTBOUND][ProtocolVersion.UNKNOWN, Any()])
    }

    @Test
    fun `Test Packet Direction`() {
        assertEquals("S->C", PacketDirection.CLIENTBOUND.toString())
        assertEquals("C->S", PacketDirection.SERVERBOUND.toString())

        assertEquals(PacketDirection.SERVERBOUND, PacketDirection.CLIENTBOUND.invert())
        assertEquals(PacketDirection.CLIENTBOUND, PacketDirection.SERVERBOUND.invert())
    }

    @Test
    fun `Test Packet Lookup`() {
        val state = MinecraftProtocol.HANDSHAKE
        assertEquals(ClientHandshakePacket, state[PacketDirection.SERVERBOUND][ProtocolVersion.MC1_7_2, 0]?.io)
        assertNull(state[PacketDirection.SERVERBOUND][ProtocolVersion.MC1_7_2, 1]?.io)

        assertEquals(
            ClientHandshakePacket,
            state[PacketDirection.SERVERBOUND][ProtocolVersion.MC1_7_2, ClientHandshakePacket(
                ProtocolVersion.MC1_7_2,
                "",
                1,
                MinecraftProtocol.STATUS
            )]?.io
        )
        assertNull(state[PacketDirection.SERVERBOUND][ProtocolVersion.MC1_7_2, ClientStatusRequestPacket()]?.io)
    }

    @Test
    fun `Test Packet toString`() = assertEquals("Example (1)", ProtocolState("Example", 1).toString())

}
