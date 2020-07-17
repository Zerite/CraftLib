package dev.zerite.craftlib.protocol.packet.play.server.other

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Controls the max size of a packet before it is compressed.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
data class ServerPlaySetCompressionPacket(var threshold: Int) : Packet() {
    companion object : PacketIO<ServerPlaySetCompressionPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlaySetCompressionPacket(buffer.readVarInt())

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlaySetCompressionPacket,
            connection: NettyConnection
        ) {
            buffer.writeVarInt(packet.threshold)
        }
    }
}
