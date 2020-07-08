package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
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
data class ClientPlayPlayerLookPacket(
    var yaw: Float,
    var pitch: Float,
    var onGround: Boolean
) : Packet() {
    companion object : PacketIO<ClientPlayPlayerLookPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientPlayPlayerLookPacket(
            buffer.readFloat(),
            buffer.readFloat(),
            buffer.readBoolean()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayPlayerLookPacket,
            connection: NettyConnection
        ) {
            buffer.writeFloat(packet.yaw)
            buffer.writeFloat(packet.pitch)
            buffer.writeBoolean(packet.onGround)
        }
    }
}
