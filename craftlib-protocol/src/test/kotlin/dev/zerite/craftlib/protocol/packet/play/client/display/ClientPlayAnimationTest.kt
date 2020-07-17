package dev.zerite.craftlib.protocol.packet.play.client.display

import dev.zerite.craftlib.protocol.data.registry.impl.MagicClientAnimation
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the client to display an animation to other players.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ClientPlayAnimationTest : PacketTest<ClientPlayAnimationPacket>(ClientPlayAnimationPacket) {
    init {
        example(
            ClientPlayAnimationPacket(60, MagicClientAnimation.DAMAGE_ANIMATION),
            maximumVersion = ProtocolVersion.MC1_7_6
        ) {
            ProtocolVersion.MC1_7_2 {
                writeInt(60)
                writeByte(2)
            }
        }
        example(
            ClientPlayAnimationPacket(127, MagicClientAnimation.LEAVE_BED),
            maximumVersion = ProtocolVersion.MC1_7_6
        ) {
            ProtocolVersion.MC1_7_2 {
                writeInt(127)
                writeByte(3)
            }
        }
    }
}
