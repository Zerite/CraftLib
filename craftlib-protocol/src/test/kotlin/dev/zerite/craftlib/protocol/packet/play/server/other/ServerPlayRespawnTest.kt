package dev.zerite.craftlib.protocol.packet.play.server.other

import dev.zerite.craftlib.protocol.data.registry.impl.MagicDifficulty
import dev.zerite.craftlib.protocol.data.registry.impl.MagicDimension
import dev.zerite.craftlib.protocol.data.registry.impl.MagicGamemode
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * To change the player's dimension (overworld/nether/end/custom), send them a respawn packet
 * with the appropriate dimension, followed by chunks for the new dimension, and finally
 * a position and look packet. You do not need to unload chunks, the client will do
 * it automatically.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayRespawnTest : PacketTest<ServerPlayRespawnPacket>(ServerPlayRespawnPacket) {

    init {
        example(
            ServerPlayRespawnPacket(
                MagicDimension.OVERWORLD,
                MagicDifficulty.NORMAL,
                MagicGamemode.CREATIVE,
                "default"
            )
        )
        example(
            ServerPlayRespawnPacket(
                MagicDimension.NETHER,
                MagicDifficulty.PEACEFUL,
                MagicGamemode.SURVIVAL,
                "placeholder"
            )
        )
        example(
            ServerPlayRespawnPacket(
                MagicDimension.END,
                MagicDifficulty.HARD,
                MagicGamemode.ADVENTURE,
                "amplified"
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeInt(1)
                writeByte(3)
                writeByte(2)
                writeString("amplified")
            }
        }
    }

}