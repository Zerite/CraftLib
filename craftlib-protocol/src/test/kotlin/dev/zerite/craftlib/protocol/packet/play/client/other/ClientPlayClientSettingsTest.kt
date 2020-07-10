package dev.zerite.craftlib.protocol.packet.play.client.other

import dev.zerite.craftlib.protocol.data.registry.impl.MagicDifficulty
import dev.zerite.craftlib.protocol.data.registry.impl.MagicViewDistance
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent when the player connects, or when settings are changed.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ClientPlayClientSettingsTest : PacketTest<ClientPlayClientSettingsPacket>(ClientPlayClientSettingsPacket) {
    init {
        example(ClientPlayClientSettingsPacket("en_GB", MagicViewDistance.NORMAL, 0, MagicDifficulty.HARD, true)) {
            ProtocolVersion.MC1_7_2 {
                writeString("en_GB")
                writeByte(1)
                writeByte(0)
                writeBoolean(true)
                writeByte(3)
                writeBoolean(true)
            }
        }
        example(ClientPlayClientSettingsPacket("en_US", MagicViewDistance.TINY, 1, MagicDifficulty.PEACEFUL, false)) {
            ProtocolVersion.MC1_7_2 {
                writeString("en_US")
                writeByte(3)
                writeByte(1)
                writeBoolean(true)
                writeByte(0)
                writeBoolean(false)
            }
        }
    }
}
