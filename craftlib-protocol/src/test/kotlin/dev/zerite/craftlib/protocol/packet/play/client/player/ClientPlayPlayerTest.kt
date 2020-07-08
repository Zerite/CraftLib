package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This packet is used to indicate whether the player is on ground (walking/swimming), or airborne (jumping/falling).
 * When dropping from sufficient height, fall damage is applied when this state goes from False to True.
 * The amount of damage applied is based on the point where it last changed from True to False.
 * Note that there are several movement related packets containing this state.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ClientPlayPlayerTest : PacketTest<ClientPlayPlayerPacket>(ClientPlayPlayerPacket) {
    init {
        example(ClientPlayPlayerPacket(true)) {
            ProtocolVersion.MC1_7_2 {
                writeBoolean(true)
            }
        }
        example(ClientPlayPlayerPacket(false)) {
            ProtocolVersion.MC1_7_2 {
                writeBoolean(false)
            }
        }
    }
}
