package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to change the clients camera position.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
data class ServerPlayCameraPacket(var cameraId: Int) : Packet() {
    companion object : PacketIO<ServerPlayCameraPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayCameraPacket(buffer.readVarInt())

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayCameraPacket,
            connection: NettyConnection
        ) {
            buffer.writeVarInt(packet.cameraId)
        }
    }
}
