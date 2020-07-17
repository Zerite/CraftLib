package dev.zerite.craftlib.protocol.packet.play.server.entity.movement

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server when an entity moves less than 4 blocks in one
 * single movement.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayEntityRelativeMovePacket(
    override var entityId: Int,
    var x: Double,
    var y: Double,
    var z: Double,
    var onGround: Boolean = false
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ServerPlayEntityRelativeMovePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) =
            ServerPlayEntityRelativeMovePacket(
                if (version >= ProtocolVersion.MC1_8) buffer.readVarInt() else buffer.readInt(),
                buffer.readFixedPoint { readByte().toDouble() },
                buffer.readFixedPoint { readByte().toDouble() },
                buffer.readFixedPoint { readByte().toDouble() },
                if (version >= ProtocolVersion.MC1_8) buffer.readBoolean() else false
            )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayEntityRelativeMovePacket,
            connection: NettyConnection
        ) {
            if (version >= ProtocolVersion.MC1_8) buffer.writeVarInt(packet.entityId)
            else buffer.writeInt(packet.entityId)
            buffer.writeFixedPoint(packet.x) { writeByte(it) }
            buffer.writeFixedPoint(packet.y) { writeByte(it) }
            buffer.writeFixedPoint(packet.z) { writeByte(it) }
            if (version >= ProtocolVersion.MC1_8)
                buffer.writeBoolean(packet.onGround)
        }
    }
}
