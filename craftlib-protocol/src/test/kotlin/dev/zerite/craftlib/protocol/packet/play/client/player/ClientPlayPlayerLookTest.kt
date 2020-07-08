package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Updates the direction the player is looking in.
 *
 * Yaw is measured in degrees, and does not follow classical trigonometry rules.
 * The unit circle of yaw on the xz-plane starts at (0, 1) and turns backwards towards (-1, 0),
 * or in other words, it turns clockwise instead of counterclockwise. Additionally, yaw is not
 * clamped to between 0 and 360 degrees; any number is valid, including negative numbers and
 * numbers greater than 360.
 *
 * Pitch is measured in degrees, where 0 is looking straight ahead, -90 is looking straight up,
 * and 90 is looking straight down.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ClientPlayPlayerLookTest : PacketTest<ClientPlayPlayerLookPacket>(ClientPlayPlayerLookPacket) {
    init {
        example(ClientPlayPlayerLookPacket(90f, 180f, false)) {
            ProtocolVersion.MC1_7_2 {
                writeFloat(90f)
                writeFloat(180f)
                writeBoolean(false)
            }
        }
        example(ClientPlayPlayerLookPacket(40f, 69f, true)) {
            ProtocolVersion.MC1_7_2 {
                writeFloat(40f)
                writeFloat(69f)
                writeBoolean(true)
            }
        }
    }
}
