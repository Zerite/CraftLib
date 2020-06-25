package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This packet is sent by the server when an entity moves more than 4 blocks.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayEntityTeleportPacket(
    override var entityId: Int,
    var x: Double,
    var y: Double,
    var z: Double,
    var yaw: Float,
    var pitch: Float
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ServerPlayEntityTeleportPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayEntityTeleportPacket(
            buffer.readInt(),
            buffer.readFixedPoint(),
            buffer.readFixedPoint(),
            buffer.readFixedPoint(),
            buffer.readByte().toFloat(),
            buffer.readByte().toFloat()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayEntityTeleportPacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.entityId)
            buffer.writeFixedPoint(packet.x)
            buffer.writeFixedPoint(packet.y)
            buffer.writeFixedPoint(packet.z)
            buffer.writeByte(packet.yaw.toInt())
            buffer.writeByte(packet.pitch.toInt())
        }
    }
}
