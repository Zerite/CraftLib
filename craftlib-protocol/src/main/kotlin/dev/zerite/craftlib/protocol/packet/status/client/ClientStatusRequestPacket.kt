package dev.zerite.craftlib.protocol.packet.status.client

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Requests information about the server which is displayed in the server list.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ClientStatusRequestPacket : Packet() {
    companion object : PacketIO<ClientStatusRequestPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientStatusRequestPacket()

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientStatusRequestPacket,
            connection: NettyConnection
        ) {
        }
    }

    override fun toString() = "ClientStatusRequestPacket()"
}
