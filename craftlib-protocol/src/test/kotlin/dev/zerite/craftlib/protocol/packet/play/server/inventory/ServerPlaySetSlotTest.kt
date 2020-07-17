package dev.zerite.craftlib.protocol.packet.play.server.inventory

import dev.zerite.craftlib.protocol.Slot
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Tests that the set slot packet is functioning properly.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlaySetSlotTest : PacketTest<ServerPlaySetSlotPacket>(ServerPlaySetSlotPacket) {
    init {
        example(ServerPlaySetSlotPacket(0, 1, Slot(0)))
        example(ServerPlaySetSlotPacket(255, 20, Slot(42, count = 7)))
        example(ServerPlaySetSlotPacket(0, 10, Slot(82, count = 3))) {
            ProtocolVersion.MC1_7_2 {
                writeByte(0)
                writeShort(10)

                // Slot
                writeShort(82)
                writeByte(3)
                writeShort(0)
                writeShort(-1)
            }
            ProtocolVersion.MC1_8 {
                writeByte(0)
                writeShort(10)

                // Slot
                writeShort(82)
                writeByte(3)
                writeShort(0)
                writeByte(0)
            }
        }
    }
}
