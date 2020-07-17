package dev.zerite.craftlib.protocol.packet.play.client.inventory

import dev.zerite.craftlib.protocol.Slot
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This packet is sent by the player when it clicks on a slot in a window.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ClientPlayClickWindowTest : PacketTest<ClientPlayClickWindowPacket>(ClientPlayClickWindowPacket) {
    init {
        example(ClientPlayClickWindowPacket(10, 20, 0, 21, 0, Slot(0))) {
            ProtocolVersion.MC1_7_2 {
                writeByte(10)
                writeShort(20)
                writeByte(0)
                writeShort(21)
                writeByte(0)

                // Slot
                writeShort(0)
                writeByte(0)
                writeShort(0)
                writeShort(-1)
            }
            ProtocolVersion.MC1_8 {
                writeByte(10)
                writeShort(20)
                writeByte(0)
                writeShort(21)
                writeByte(0)

                // Slot
                writeShort(0)
                writeByte(0)
                writeShort(0)
                writeByte(0)
            }
        }
        example(ClientPlayClickWindowPacket(20, 50, 2, 43, 1, Slot(1, 1))) {
            ProtocolVersion.MC1_7_2 {
                writeByte(20)
                writeShort(50)
                writeByte(2)
                writeShort(43)
                writeByte(1)

                // Slot
                writeShort(1)
                writeByte(1)
                writeShort(0)
                writeShort(-1)
            }
            ProtocolVersion.MC1_8 {
                writeByte(20)
                writeShort(50)
                writeByte(2)
                writeShort(43)
                writeByte(1)

                // Slot
                writeShort(1)
                writeByte(1)
                writeShort(0)
                writeByte(0)
            }
        }
    }
}
