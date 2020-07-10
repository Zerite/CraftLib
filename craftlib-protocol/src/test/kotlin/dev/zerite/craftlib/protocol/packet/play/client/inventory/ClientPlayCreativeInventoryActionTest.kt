package dev.zerite.craftlib.protocol.packet.play.client.inventory

import dev.zerite.craftlib.protocol.Slot
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * While the user is in the standard inventory (i.e., not a crafting bench) on a creative-mode server then the server will send this packet:
 * If an item is dropped into the quick bar
 * If an item is picked up from the quick bar (item id is -1)
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */
class ClientPlayCreativeInventoryActionTest :
    PacketTest<ClientPlayCreativeInventoryActionPacket>(ClientPlayCreativeInventoryActionPacket) {
    init {
        example(ClientPlayCreativeInventoryActionPacket(0, Slot(0))) {
            ProtocolVersion.MC1_7_2 {
                writeShort(0)
                writeShort(0)
                writeByte(0)
                writeShort(0)
                writeShort(0)
            }
        }
    }
}
