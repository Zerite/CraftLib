package dev.zerite.craftlib.protocol.packet.play.server.player

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to update the client's position and look vectors.
 * Additionally it is used to take the client out of the loading screen upon
 * joining a world.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayPlayerPositionLookPacket(
    var x: Double,
    var y: Double,
    var z: Double,
    var yaw: Float,
    var pitch: Float,
    var onGround: Boolean
) : Packet() {
    companion object : PacketIO<ServerPlayPlayerPositionLookPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayPlayerPositionLookPacket(
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
            packet: ServerPlayPlayerPositionLookPacket,
            connection: NettyConnection
        ) {
            buffer.writeDouble(packet.x)
            buffer.writeDouble(packet.y)
            buffer.writeDouble(packet.z)
            buffer.writeFloat(packet.yaw)
            buffer.writeFloat(packet.pitch)
            buffer.writeBoolean(packet.onGround)
        }
    }
}