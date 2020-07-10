package dev.zerite.craftlib.protocol.packet.play.client.other

import dev.zerite.craftlib.protocol.data.registry.impl.MagicClientStatus
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.packet.play.client.other.ClientPlayClientStatusPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent when the client is ready to complete login and when the client is ready to respawn after death.
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */
class ClientPlayClientStatusTest : PacketTest<ClientPlayClientStatusPacket>(ClientPlayClientStatusPacket) {
    init {
        example(ClientPlayClientStatusPacket(MagicClientStatus.OPEN_INVENTORY_ACHIEVEMENT)) {
            ProtocolVersion.MC1_7_2 {
                writeByte(2)
            }
        }
    }
}
