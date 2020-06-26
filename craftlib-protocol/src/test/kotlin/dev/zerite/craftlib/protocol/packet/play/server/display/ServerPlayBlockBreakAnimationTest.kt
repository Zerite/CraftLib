package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * 0-9 are the displayable destroy stages and each other number means that there is
 * no animation on this coordinate. You can also set an animation to air!
 * The animation will still be visible.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayBlockBreakAnimationTest :
    PacketTest<ServerPlayBlockBreakAnimationPacket>(ServerPlayBlockBreakAnimationPacket) {
    init {
        example(ServerPlayBlockBreakAnimationPacket(69, 42, 80, 21, 4)) {
            ProtocolVersion.MC1_7_2 {
                writeVarInt(69)
                writeInt(42)
                writeInt(80)
                writeInt(21)
                writeByte(4)
            }
        }
        example(ServerPlayBlockBreakAnimationPacket(400, 550, 60, 700, 0)) {
            ProtocolVersion.MC1_7_2 {
                writeVarInt(400)
                writeInt(550)
                writeInt(60)
                writeInt(700)
                writeByte(0)
            }
        }
    }
}