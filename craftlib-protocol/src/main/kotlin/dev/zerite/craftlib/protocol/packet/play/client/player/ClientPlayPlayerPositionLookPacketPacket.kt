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

data class ClientPlayPlayerPositionLookPacketPacket(
    var x: Double,
    var y: Double,
    var stance: Double,
    var z: Double,
    var yaw: Float,
    var pitch: Float,
    var onGround: Boolean
) : Packet() {
    companion object : PacketIO<ClientPlayPlayerPositionLookPacketPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ): ClientPlayPlayerPositionLookPacketPacket = ClientPlayPlayerPositionLookPacketPacket(
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
            packetPacket: ClientPlayPlayerPositionLookPacketPacket,
            connection: NettyConnection
        ) {
            buffer.writeDouble(packetPacket.x)
            buffer.writeDouble(packetPacket.y)
            buffer.writeDouble(packetPacket.stance)
            buffer.writeDouble(packetPacket.z)
            buffer.writeFloat(packetPacket.yaw)
            buffer.writeFloat(packetPacket.pitch)
            buffer.writeBoolean(packetPacket.onGround)
        }
    }
}