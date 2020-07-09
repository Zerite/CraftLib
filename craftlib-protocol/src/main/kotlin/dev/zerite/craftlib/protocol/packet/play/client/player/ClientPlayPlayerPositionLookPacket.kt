package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * A combination of Player Look and Player position.
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */

data class ClientPlayPlayerPositionLookPacket(
    var x: Double,
    var y: Double,
    var stance: Double,
    var z: Double,
    var yaw: Float,
    var pitch: Float,
    var onGround: Boolean
) : Packet() {
    companion object : PacketIO<ClientPlayPlayerPositionLookPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ): ClientPlayPlayerPositionLookPacket = ClientPlayPlayerPositionLookPacket(
            buffer.readDouble(),
            buffer.readDouble(),
            buffer.readDouble(),
            buffer.readDouble(),
            buffer.readFloat(),
            buffer.readFloat(),
            buffer.readBoolean()
        )

        override fun write(
                buffer: ProtocolBuffer,
                version: ProtocolVersion,
                packet: ClientPlayPlayerPositionLookPacket,
                connection: NettyConnection
        ) {
            buffer.writeDouble(packet.x)
            buffer.writeDouble(packet.y)
            buffer.writeDouble(packet.stance)
            buffer.writeDouble(packet.z)
            buffer.writeFloat(packet.yaw)
            buffer.writeFloat(packet.pitch)
            buffer.writeBoolean(packet.onGround)
        }
    }
}