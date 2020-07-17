package dev.zerite.craftlib.protocol.packet.play.server.other

import dev.zerite.craftlib.protocol.data.registry.impl.MagicDifficulty
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Changes the difficulty setting in the client's option menu.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
class ServerPlayServerDifficultyTest : PacketTest<ServerPlayServerDifficultyPacket>(ServerPlayServerDifficultyPacket) {
    init {
        example(ServerPlayServerDifficultyPacket(MagicDifficulty.PEACEFUL)) {
            ProtocolVersion.MC1_8 {
                writeByte(0)
            }
        }
        example(ServerPlayServerDifficultyPacket(MagicDifficulty.HARD)) {
            ProtocolVersion.MC1_8 {
                writeByte(3)
            }
        }
    }
}
