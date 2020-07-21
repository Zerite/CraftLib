package dev.zerite.craftlib.protocol.connection

import dev.zerite.craftlib.protocol.version.PacketDirection
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * Tests that the Netty connection is properly working, including the flags.
 *
 * @author Koding
 * @since  0.1.2
 */
class NettyConnectionTest {

    @Test
    fun `Test Flags`() {
        val dummy = NettyConnection(PacketDirection.SERVERBOUND)
        val key = "dummy"

        assertNull(dummy[key, String::class.java])
        dummy[key] = "dummy"
        assertEquals(dummy[key, String::class.java]!!, "dummy")
        dummy -= key
        assertNull(dummy[key, String::class.java])
    }

}
