package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.entity.EntityMetadata
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import java.util.*

/**
 * This packet is sent by the server when a player comes into visible range, not when a player joins.
 * Servers can, however, safely spawn player entities for players not in visible range.
 * The client appears to handle it correctly.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlaySpawnPlayerPacket(
    override var entityId: Int,
    var uuid: UUID,
    var name: String,
    var data: MutableMap<String, DataEntry>,
    var x: Double,
    var y: Double,
    var z: Double,
    var yaw: Float,
    var pitch: Float,
    var currentItem: Int,
    var metadata: EntityMetadata
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ServerPlaySpawnPlayerPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlaySpawnPlayerPacket(
            buffer.readVarInt(),
            buffer.readUUID(mode = ProtocolBuffer.UUIDMode.DASHES.takeIf { version < ProtocolVersion.MC1_7_6 } ?: ProtocolBuffer.UUIDMode.STRING),
            buffer.readString(),
            buffer.readData(),
            buffer.readFixedPoint(),
            buffer.readFixedPoint(),
            buffer.readFixedPoint(),
            buffer.readStepRotation(),
            buffer.readStepRotation(),
            buffer.readShort().toInt(),
            buffer.readMetadata()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlaySpawnPlayerPacket,
            connection: NettyConnection
        ) {
            buffer.writeVarInt(packet.entityId)
            buffer.writeUUID(packet.uuid, mode = ProtocolBuffer.UUIDMode.DASHES.takeIf { version < ProtocolVersion.MC1_7_6 } ?: ProtocolBuffer.UUIDMode.STRING)
            buffer.writeString(packet.name)
            if (version >= ProtocolVersion.MC1_7_6) {
                buffer.writeVarInt(packet.data.size)
                packet.data.forEach { (name, entry) ->
                    buffer.writeString(name)
                    buffer.writeString(entry.value)
                    buffer.writeString(entry.signature)
                }
            }
            buffer.writeFixedPoint(packet.x)
            buffer.writeFixedPoint(packet.y)
            buffer.writeFixedPoint(packet.z)
            buffer.writeStepRotation(packet.yaw)
            buffer.writeStepRotation(packet.pitch)
            buffer.writeShort(packet.currentItem)
            buffer.writeMetadata(packet.metadata)
        }

        private fun ProtocolBuffer.readData() = hashMapOf<String, DataEntry>().also {
            if (connection.version >= ProtocolVersion.MC1_7_6) {
                repeat(readVarInt()) { _ ->
                    it[readString()] = DataEntry(readString(), readString())
                }
            }
        }
    }

    data class DataEntry(val value: String, val signature: String)
}