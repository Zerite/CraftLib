package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.data.registry.impl.MagicAnimation
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent whenever an entity should change animation.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayAnimationTest : PacketTest<ServerPlayAnimationPacket>(ServerPlayAnimationPacket) {

    init {
        example(ServerPlayAnimationPacket(0, MagicAnimation.CRITICAL_EFFECT))
        example(ServerPlayAnimationPacket(100, MagicAnimation.UNCROUCH))
        example(ServerPlayAnimationPacket(200, MagicAnimation.CROUCH)) {
            ProtocolVersion.MC1_7_2 {
                writeVarInt(200)
                writeByte(104)
            }
        }
    }

}