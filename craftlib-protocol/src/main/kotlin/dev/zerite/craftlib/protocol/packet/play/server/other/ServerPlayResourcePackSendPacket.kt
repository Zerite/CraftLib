package dev.zerite.craftlib.protocol.packet.play.server.other

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent to the client to force them to load a resource pack.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
data class ServerPlayResourcePackSendPacket(
    var url: String,
    var hash: String
) : Packet() {
    companion object : PacketIO<ServerPlayResourcePackSendPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayResourcePackSendPacket(
            buffer.readString(),
            buffer.readString()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayResourcePackSendPacket,
            connection: NettyConnection
        ) {
            buffer.writeString(packet.url)
            buffer.writeString(packet.hash)
        }
    }
}
