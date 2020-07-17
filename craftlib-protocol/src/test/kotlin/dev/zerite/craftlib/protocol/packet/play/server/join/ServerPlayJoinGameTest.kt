package dev.zerite.craftlib.protocol.packet.play.server.join

import dev.zerite.craftlib.protocol.data.registry.impl.MagicDifficulty
import dev.zerite.craftlib.protocol.data.registry.impl.MagicDimension
import dev.zerite.craftlib.protocol.data.registry.impl.MagicGamemode
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

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
                false,
                MagicGamemode.SURVIVAL,
                MagicDimension.OVERWORLD,
                MagicDifficulty.PEACEFUL,
                20,
                "default",
                false
            )
        )
        example(
            ServerPlayJoinGamePacket(
                1,
                true,
                MagicGamemode.SURVIVAL,
                MagicDimension.OVERWORLD,
                MagicDifficulty.HARD,
                21,
                "default",
                false
            )
        )
        example(
            ServerPlayJoinGamePacket(
                1,
                true,
                MagicGamemode.ADVENTURE,
                MagicDimension.END,
                MagicDifficulty.NORMAL,
                20,
                "default",
                false
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeInt(1)
                writeByte(2 or 0x8)
                writeByte(1)
                writeByte(2)
                writeByte(20)
                writeString("default")
            }
            ProtocolVersion.MC1_8 {
                writeInt(1)
                writeByte(2 or 0x8)
                writeByte(1)
                writeByte(2)
                writeByte(20)
                writeString("default")
                writeBoolean(false)
            }
        }
    }

}
