package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to tell the client the world total age and current
 * daylight time.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayTimeUpdatePacket(var age: Long, var time: Long) {
    companion object : PacketIO<ServerPlayTimeUpdatePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayTimeUpdatePacket(
            buffer.readLong(),
            buffer.readLong()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayTimeUpdatePacket,
            connection: NettyConnection
        ) {
            buffer.writeLong(packet.age)
            buffer.writeLong(packet.time)
        }
    }
}