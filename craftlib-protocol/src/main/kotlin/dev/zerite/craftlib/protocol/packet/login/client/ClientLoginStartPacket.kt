package dev.zerite.craftlib.protocol.packet.login.client

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent when the client wants to initiate the login process following
 * a successful handshake packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ClientLoginStartPacket(var name: String) : Packet() {
    companion object : PacketIO<ClientLoginStartPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientLoginStartPacket(buffer.readString())

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientLoginStartPacket,
            connection: NettyConnection
        ) {
            buffer.writeString(packet.name)
        }
    }
}