package dev.zerite.craftlib.protocol.packet.play.server.other

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to indicate that the connection is still
 * alive and valid.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayKeepAlivePacket(var id: Int) : Packet() {
    companion object : PacketIO<ServerPlayKeepAlivePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayKeepAlivePacket(buffer.readInt())

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayKeepAlivePacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.id)
        }
    }
}