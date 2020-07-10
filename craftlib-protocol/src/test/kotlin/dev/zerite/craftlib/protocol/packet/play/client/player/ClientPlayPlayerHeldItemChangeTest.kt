package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent when the player changes the slot selection
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */
class ClientPlayPlayerHeldItemChangeTest : PacketTest<ClientPlayPlayerHeldItemChangePacket>(ClientPlayPlayerHeldItemChangePacket) {
    init {
        example(ClientPlayPlayerHeldItemChangePacket(0)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(0)
            }
        }
    }
}