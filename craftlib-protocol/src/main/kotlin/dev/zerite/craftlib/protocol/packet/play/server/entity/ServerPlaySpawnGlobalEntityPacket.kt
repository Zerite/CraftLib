package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * With this packet, the server notifies the client of thunderbolts striking
 * within a 512 block radius around the player.
 * The coordinates specify where exactly the thunderbolt strikes.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlaySpawnGlobalEntityPacket(
    override var entityId: Int,
    var type: Int,
    var x: Double,
    var y: Double,
    var z: Double
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ServerPlaySpawnGlobalEntityPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlaySpawnGlobalEntityPacket(
            buffer.readVarInt(),
            buffer.readByte().toInt(),
            buffer.readFixedPoint(),
            buffer.readFixedPoint(),
            buffer.readFixedPoint()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlaySpawnGlobalEntityPacket,
            connection: NettyConnection
        ) {
            buffer.writeVarInt(packet.entityId)
            buffer.writeByte(packet.type)
            buffer.writeFixedPoint(packet.x)
            buffer.writeFixedPoint(packet.y)
            buffer.writeFixedPoint(packet.z)
        }
    }
}
