package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import kotlin.test.Test
import kotlin.test.assertFails

class ServerPlayPlayerListItemTest : PacketTest<ServerPlayPlayerListItemPacket>(ServerPlayPlayerListItemPacket) {

    init {
        example(ServerPlayPlayerListItemPacket("Test", true, 1000))
        example(ServerPlayPlayerListItemPacket("ExampleOffline", false, 420))
        example(ServerPlayPlayerListItemPacket("WritingUser", true, 4200)) {
            ProtocolVersion.MC1_7_2 {
                writeString("WritingUser")
                writeBoolean(true)
                writeShort(4200)
            }
        }
    }

    @Test
    fun `Test Exceptions`() {
        assertFails { ServerPlayPlayerListItemPacket("Failure", true, Short.MAX_VALUE + 1) }
    }

}