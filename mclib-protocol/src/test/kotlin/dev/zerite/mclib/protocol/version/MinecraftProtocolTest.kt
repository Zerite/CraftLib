package dev.zerite.mclib.protocol.version

import java.net.InetAddress
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertNotNull

/**
 * Tests the Minecraft protocol.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class MinecraftProtocolTest {

    @Test
    fun `Test Client Connection`() {
        assertFails {
            MinecraftProtocol.connect(InetAddress.getLocalHost(), 99999)
        }
    }

}