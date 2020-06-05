package dev.zerite.mclib.protocol.packet.play.server.join

import dev.zerite.mclib.protocol.data.enum.Difficulty
import dev.zerite.mclib.protocol.data.enum.Dimension
import dev.zerite.mclib.protocol.data.enum.Gamemode
import dev.zerite.mclib.protocol.packet.PacketTest
import dev.zerite.mclib.protocol.version.ProtocolVersion

/**
 * Tests that the server join game packet is properly working utilizing
 * the setters.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayJoinGameTest : PacketTest<ServerPlayJoinGamePacket>(
    ServerPlayJoinGamePacket
) {

    init {
        example(
            ServerPlayJoinGamePacket(
                1,
                0,
                0,
                0,
                20,
                "default"
            )
        )
        example(
            ServerPlayJoinGamePacket(
                1,
                Gamemode.SURVIVAL,
                true,
                Dimension.OVERWORLD,
                Difficulty.HARD,
                21,
                "default"
            )
        )
        example(
            ServerPlayJoinGamePacket(
                1,
                0,
                0,
                0,
                20,
                "default"
            )
                .apply {
                    gamemode = Gamemode.ADVENTURE
                    dimension = Dimension.END
                    difficulty = Difficulty.NORMAL
                    hardcore = true
                }
        ) {
            ProtocolVersion.MC1_7_2 {
                writeInt(1)
                writeByte(2 or 0x8)
                writeByte(1)
                writeByte(2)
                writeByte(20)
                writeString("default")
            }
        }
    }

}