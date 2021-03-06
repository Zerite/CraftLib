package dev.zerite.craftlib.protocol.version

import kotlinx.coroutines.runBlocking
import java.net.InetAddress
import kotlin.test.Test
import kotlin.test.assertFails

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
            runBlocking {
                MinecraftProtocol.connect(InetAddress.getLocalHost(), 99999)
            }
        }
    }

}
