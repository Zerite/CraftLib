package dev.zerite.craftlib.protocol.packet.login.server

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sets the compression threshold to use throughout this connection.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
data class ServerLoginSetCompressionPacket(var threshold: Int) : Packet() {
    companion object : PacketIO<ServerLoginSetCompressionPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerLoginSetCompressionPacket(buffer.readVarInt())

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerLoginSetCompressionPacket,
            connection: NettyConnection
        ) {
            buffer.writeVarInt(packet.threshold)
        }
    }
}
