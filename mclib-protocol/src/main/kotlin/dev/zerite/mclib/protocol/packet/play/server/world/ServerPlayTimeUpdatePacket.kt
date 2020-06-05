package dev.zerite.mclib.protocol.packet.play.server.world

import dev.zerite.mclib.protocol.PacketIO
import dev.zerite.mclib.protocol.ProtocolBuffer
import dev.zerite.mclib.protocol.connection.NettyConnection
import dev.zerite.mclib.protocol.version.ProtocolVersion

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