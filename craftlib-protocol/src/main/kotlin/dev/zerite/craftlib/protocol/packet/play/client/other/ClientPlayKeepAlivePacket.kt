package dev.zerite.craftlib.protocol.packet.play.client.other

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * The server will frequently send out a keep-alive, each containing a random ID.
 * The client must respond with the same packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ClientPlayKeepAlivePacket(var id: Int) : Packet() {
    companion object : PacketIO<ClientPlayKeepAlivePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientPlayKeepAlivePacket(
            if (version >= ProtocolVersion.MC1_8) buffer.readVarInt() else buffer.readInt()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayKeepAlivePacket,
            connection: NettyConnection
        ) {
            if (version >= ProtocolVersion.MC1_8) buffer.writeVarInt(packet.id)
            else buffer.writeInt(packet.id)
        }
    }
}
