package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.entity.EntityMetadata
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicMobType
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.util.ext.toLegacyUUID
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import java.util.*

/**
 * Sent by the server to indicate that a mob has been spawned at
 * a certain location.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlaySpawnMobPacket(
    override var entityId: Int,
    var uuid: UUID,
    var type: RegistryEntry,
    var x: Double,
    var y: Double,
    var z: Double,
    var pitch: Float,
    var headPitch: Float,
    var yaw: Float,
    var velocityX: Double,
    var velocityY: Double,
    var velocityZ: Double,
    var metadata: EntityMetadata
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ServerPlaySpawnMobPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ): ServerPlaySpawnMobPacket {
            val entityId = buffer.readVarInt()
            return ServerPlaySpawnMobPacket(
                entityId,
                if (version >= ProtocolVersion.MC1_9) buffer.readUUID(mode = ProtocolBuffer.UUIDMode.RAW) else entityId.toLegacyUUID(),
                MagicMobType[version, buffer.readUnsignedByte().toInt()],
                if (version >= ProtocolVersion.MC1_9) buffer.readDouble() else buffer.readFixedPoint(),
                if (version >= ProtocolVersion.MC1_9) buffer.readDouble() else buffer.readFixedPoint(),
                if (version >= ProtocolVersion.MC1_9) buffer.readDouble() else buffer.readFixedPoint(),
                buffer.readStepRotation(),
                buffer.readStepRotation(),
                buffer.readStepRotation(),
                buffer.readVelocity(),
                buffer.readVelocity(),
                buffer.readVelocity(),
                buffer.readMetadata()
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlaySpawnMobPacket,
            connection: NettyConnection
        ) {
            buffer.writeVarInt(packet.entityId)
            if (version >= ProtocolVersion.MC1_9) buffer.writeUUID(packet.uuid, mode = ProtocolBuffer.UUIDMode.RAW)
            buffer.writeByte(MagicMobType[version, packet.type, Int::class.java] ?: 0)

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
            buffer.writeStepRotation(packet.headPitch)
            buffer.writeStepRotation(packet.yaw)
            buffer.writeVelocity(packet.velocityX)
            buffer.writeVelocity(packet.velocityY)
            buffer.writeVelocity(packet.velocityZ)
            buffer.writeMetadata(packet.metadata)
        }
    }
}
