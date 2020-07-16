package dev.zerite.craftlib.protocol.packet.status.client

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the client to calculate the ping to the server.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ClientStatusPingPacket(
    var time: Long
) : Packet() {
    companion object : PacketIO<ClientStatusPingPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientStatusPingPacket(buffer.readLong())

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientStatusPingPacket,
            connection: NettyConnection
        ) {
            buffer.writeLong(packet.time)
        }
    }
}
