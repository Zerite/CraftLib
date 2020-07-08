package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Enchant Item
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */


class ClientPlayPlayerEnchantItemTest : PacketTest<ClientPlayPlayerEnchantItemPacket>(ClientPlayPlayerEnchantItemPacket) {
    init {
        example(ClientPlayPlayerEnchantItemPacket(0, 0)) {
            ProtocolVersion.MC1_7_2 {
                writeByte(0)
                writeByte(0)
            }
        }
    }
}