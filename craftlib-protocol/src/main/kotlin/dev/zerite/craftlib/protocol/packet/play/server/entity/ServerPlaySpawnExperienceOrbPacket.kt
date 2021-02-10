package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Spawns one or more experience orbs.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlaySpawnExperienceOrbPacket(
    override var entityId: Int,
    var x: Double,
    var y: Double,
    var z: Double,
    var count: Int
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ServerPlaySpawnExperienceOrbPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlaySpawnExperienceOrbPacket(
            buffer.readVarInt(),
            if (version >= ProtocolVersion.MC1_9) buffer.readDouble() else buffer.readFixedPoint(),
            if (version >= ProtocolVersion.MC1_9) buffer.readDouble() else buffer.readFixedPoint(),
            if (version >= ProtocolVersion.MC1_9) buffer.readDouble() else buffer.readFixedPoint(),
            buffer.readShort().toInt()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlaySpawnExperienceOrbPacket,
            connection: NettyConnection
        ) {
            buffer.writeVarInt(packet.entityId)

            if (version >= ProtocolVersion.MC1_9) {
                buffer.writeDouble(packet.x)
                buffer.writeDouble(packet.y)
                buffer.writeDouble(packet.z)
            } else {
                buffer.writeFixedPoint(packet.x)
                buffer.writeFixedPoint(packet.y)
                buffer.writeFixedPoint(packet.z)
            }

            buffer.writeShort(packet.count)
        }
    }
}
