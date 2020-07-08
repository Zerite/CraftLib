package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.Slot
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * While the user is in the standard inventory (i.e., not a crafting bench) on a creative-mode server then the server will send this packet:
 *
 * If an item is dropped into the quick bar
 *
 * If an item is picked up from the quick bar (item id is -1)
 *
 * @author chachy
 * @since 0.1.0-SNAPSHOT
 */


class ClientPlayPlayerCreateInventoryActionTest : PacketTest<ClientPlayPlayerCreateInventoryActionPacket>(ClientPlayPlayerCreateInventoryActionPacket) {
    init {
        example(ClientPlayPlayerCreateInventoryActionPacket(0, Slot(0))) {
            ProtocolVersion.MC1_7_2 {
                writeShort(0)
                writeSlot(Slot(0))
            }
        }
    }
}