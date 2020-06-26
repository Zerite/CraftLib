package dev.zerite.craftlib.protocol.packet.play.server.player

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server when the client should change experience levels.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlaySetExperiencePacket(
    var experience: Float,
    var level: Int,
    var total: Int
) : Packet() {
    companion object : PacketIO<ServerPlaySetExperiencePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlaySetExperiencePacket(
            buffer.readFloat(),
            buffer.readShort().toInt(),
            buffer.readShort().toInt()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlaySetExperiencePacket,
            connection: NettyConnection
        ) {
            buffer.writeFloat(packet.experience)
            buffer.writeShort(packet.level)
            buffer.writeShort(packet.total)
        }
    }
}