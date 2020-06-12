package dev.zerite.craftlib.protocol.packet.play.server.other

import dev.zerite.craftlib.protocol.data.registry.impl.MagicGameStateReason
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Tests that the change game state packet works.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayChangeGameStateTest : PacketTest<ServerPlayChangeGameStatePacket>(ServerPlayChangeGameStatePacket) {

    init {
        example(ServerPlayChangeGameStatePacket(MagicGameStateReason.BEGIN_RAINING, 0.0f))
        example(ServerPlayChangeGameStatePacket(MagicGameStateReason.FADE_TIME, 1.0f))
        example(ServerPlayChangeGameStatePacket(MagicGameStateReason.ENTER_CREDITS, 0.0f)) {
            ProtocolVersion.MC1_7_2 {
                writeByte(4)
                writeFloat(0.0f)
            }
        }
    }

}