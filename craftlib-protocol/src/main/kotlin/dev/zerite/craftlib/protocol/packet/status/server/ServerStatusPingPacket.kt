package dev.zerite.craftlib.protocol.packet.status.server

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent to the client to calculate their ping to the server.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerStatusPingPacket(var time: Long) : Packet() {
    companion object : PacketIO<ServerStatusPingPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerStatusPingPacket(buffer.readLong())

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerStatusPingPacket,
            connection: NettyConnection
        ) {
            buffer.writeLong(packet.time)
        }
    }
}
