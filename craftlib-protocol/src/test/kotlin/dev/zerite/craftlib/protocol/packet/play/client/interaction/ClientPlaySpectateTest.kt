package dev.zerite.craftlib.protocol.packet.play.client.interaction

import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import java.util.*

/**
 * Sent to the server to indicate that the player is now spectating another player.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
class ClientPlaySpectateTest : PacketTest<ClientPlaySpectatePacket>(ClientPlaySpectatePacket) {
    init {
        example(ClientPlaySpectatePacket(UUID(20L, 30L))) {
            ProtocolVersion.MC1_8 {
                writeUUID(UUID(20L, 30L), mode = ProtocolBuffer.UUIDMode.RAW)
            }
        }
    }
}
