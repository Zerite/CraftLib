package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.ObjectData
import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicObject
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.util.ext.toLegacyUUID
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import java.util.*

/**
 * Sent by the server when an object / vehicle is created.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlaySpawnObjectPacket(
    override var entityId: Int,
    var uuid: UUID,
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
        ): ServerPlaySpawnObjectPacket {
            val entityId = buffer.readVarInt()
            return ServerPlaySpawnObjectPacket(
                entityId,
                if (version >= ProtocolVersion.MC1_9) buffer.readUUID(mode = ProtocolBuffer.UUIDMode.RAW) else entityId.toLegacyUUID(),
                MagicObject[version, buffer.readByte().toInt()],
                if (version >= ProtocolVersion.MC1_9) buffer.readDouble() else buffer.readFixedPoint(),
                if (version >= ProtocolVersion.MC1_9) buffer.readDouble() else buffer.readFixedPoint(),
                if (version >= ProtocolVersion.MC1_9) buffer.readDouble() else buffer.readFixedPoint(),
                buffer.readStepRotation(),
                buffer.readStepRotation(),
                buffer.readObjectData()
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlaySpawnObjectPacket,
            connection: NettyConnection
        ) {
            buffer.writeVarInt(packet.entityId)
            if (version >= ProtocolVersion.MC1_9) buffer.writeUUID(packet.uuid, mode = ProtocolBuffer.UUIDMode.RAW)
            buffer.writeByte(MagicObject[version, packet.type, Int::class.java] ?: 0)

            if (version >= ProtocolVersion.MC1_9) {
                buffer.writeDouble(packet.x)
                buffer.writeDouble(packet.y)
                buffer.writeDouble(packet.z)
            } else {
                buffer.writeFixedPoint(packet.x)
                buffer.writeFixedPoint(packet.y)
                buffer.writeFixedPoint(packet.z)
            }

            buffer.writeStepRotation(packet.pitch)
            buffer.writeStepRotation(packet.yaw)
            buffer.writeObjectData(packet.data)
        }
    }
}
