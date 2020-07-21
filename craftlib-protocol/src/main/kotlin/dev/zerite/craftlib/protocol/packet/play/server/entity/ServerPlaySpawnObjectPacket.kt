package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.ObjectData
import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicObject
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server when an object / vehicle is created.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlaySpawnObjectPacket(
    override var entityId: Int,
    var type: RegistryEntry,
    var x: Double,
    var y: Double,
    var z: Double,
    var pitch: Float,
    var yaw: Float,
    var data: ObjectData
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ServerPlaySpawnObjectPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlaySpawnObjectPacket(
            buffer.readVarInt(),
            MagicObject[version, buffer.readByte().toInt()],
            buffer.readFixedPoint(),
            buffer.readFixedPoint(),
            buffer.readFixedPoint(),
            buffer.readStepRotation(),
            buffer.readStepRotation(),
            buffer.readObjectData()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlaySpawnObjectPacket,
            connection: NettyConnection
        ) {
            buffer.writeVarInt(packet.entityId)
            buffer.writeByte(MagicObject[version, packet.type, Int::class.java] ?: 0)
            buffer.writeFixedPoint(packet.x)
            buffer.writeFixedPoint(packet.y)
            buffer.writeFixedPoint(packet.z)
            buffer.writeStepRotation(packet.pitch)
            buffer.writeStepRotation(packet.yaw)
            buffer.writeObjectData(packet.data)
        }
    }
}
