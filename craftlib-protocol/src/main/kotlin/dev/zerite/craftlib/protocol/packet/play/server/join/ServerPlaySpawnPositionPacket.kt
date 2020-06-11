package dev.zerite.craftlib.protocol.packet.play.server.join

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

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
) : Packet() {
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