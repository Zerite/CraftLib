package dev.zerite.mclib.protocol.packet.login.client

import dev.zerite.mclib.protocol.PacketIO
import dev.zerite.mclib.protocol.ProtocolBuffer
import dev.zerite.mclib.protocol.connection.NettyConnection
import dev.zerite.mclib.protocol.version.ProtocolVersion

/**
 * Sent when the client wants to initiate the login process following
 * a successful handshake packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ClientLoginStartPacket(var name: String) {
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