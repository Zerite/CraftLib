package dev.zerite.craftlib.protocol.packet.play.client.interaction

import dev.zerite.craftlib.protocol.Slot
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * In normal operation (ie placing a block), this packet is sent once, with the values set normally.
 *
 * This packet has a special case where X, Y, Z, and Direction are all -1. (Note that Y is unsigned so set to 255.)
 * This special packet indicates that the currently held item for the player should have its state updated such as
 * eating food, shooting bows, using buckets, etc.
 *
 * In a Notchian Beta client, the block or item ID corresponds to whatever the client is currently holding,
 * and the client sends one of these packets any time a right-click is issued on a surface, so no
 * assumptions can be made about the safety of the ID. However, with the implementation of server-side inventory,
 * a Notchian server seems to ignore the item ID, instead operating on server-side inventory information and holding selection.
 * The client has been observed (1.2.5 and 1.3.2) to send both real item IDs and -1 in a single session.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ClientPlayPlayerBlockPlacementTest :
    PacketTest<ClientPlayPlayerBlockPlacementPacket>(ClientPlayPlayerBlockPlacementPacket) {
    init {
        example(ClientPlayPlayerBlockPlacementPacket(100, 50, 64, 0, Slot(0), 5, 6, 7)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(100)
                writeByte(50)
                writeInt(64)
                writeByte(0)

                // Item
                writeShort(0)
                writeByte(0)
                writeShort(0)
                writeShort(-1)

                // Cursor
                writeByte(5)
                writeByte(6)
                writeByte(7)
            }
        }
    }
}
