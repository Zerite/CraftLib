package dev.zerite.mclib.protocol.packet.play.server.join

import dev.zerite.mclib.protocol.PacketIO
import dev.zerite.mclib.protocol.ProtocolBuffer
import dev.zerite.mclib.protocol.connection.NettyConnection
import dev.zerite.mclib.protocol.version.ProtocolVersion

/**
 * Sent by the server to indicate the position where the player should
 * spawn upon entering the world.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlaySpawnPositionPacket(
    var x: Int,
    var y: Int,
    var z: Int
) {
    companion object : PacketIO<ServerPlaySpawnPositionPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlaySpawnPositionPacket(
            buffer.readInt(),
            buffer.readInt(),
            buffer.readInt()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlaySpawnPositionPacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.x)
            buffer.writeInt(packet.y)
            buffer.writeInt(packet.z)
        }
    }
}