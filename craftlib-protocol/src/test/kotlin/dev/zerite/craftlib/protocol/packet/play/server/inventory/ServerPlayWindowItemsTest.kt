package dev.zerite.craftlib.protocol.packet.play.server.inventory

import dev.zerite.craftlib.nbt.compound
import dev.zerite.craftlib.protocol.Slot
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Tests slot IO and the window items packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayWindowItemsTest : PacketTest<ServerPlayWindowItemsPacket>(ServerPlayWindowItemsPacket) {

    init {
        example(ServerPlayWindowItemsPacket(0, emptyArray()))
        example(ServerPlayWindowItemsPacket(42, arrayOf(Slot(0, count = 1, data = compound {
            "test" to "example"
        }))))
        example(ServerPlayWindowItemsPacket(21, arrayOf(Slot(42, count = 64)))) {
            ProtocolVersion.MC1_7_2 {
                writeByte(21)
                writeShort(1)
                writeSlot(Slot(42, count = 64))
            }
        }
    }

}