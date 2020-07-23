package dev.zerite.craftlib.protocol.version

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ProtocolStateTest {

    @Test
    fun `Test Getting States`() {
        val state = ProtocolState("Example", 1)
        assertEquals(state.clientbound, state[PacketDirection.CLIENTBOUND])
        assertEquals(state.serverbound, state[PacketDirection.SERVERBOUND])
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
    fun `Test Packet toString`() = assertEquals("Example (1)", ProtocolState("Example", 1).toString())

}
