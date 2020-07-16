package dev.zerite.craftlib.protocol.packet.status.server

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.other.StatusResponse
import dev.zerite.craftlib.protocol.util.ext.gson
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to players to display specific data in the
 * server list.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerStatusResponsePacket(
    var response: StatusResponse
) : Packet() {
    companion object : PacketIO<ServerStatusResponsePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerStatusResponsePacket(gson.fromJson(buffer.readString(), StatusResponse::class.java))

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerStatusResponsePacket,
            connection: NettyConnection
        ) {
            buffer.writeString(gson.toJson(packet.response))
        }
    }
}
