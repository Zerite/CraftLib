package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This packet is sent by the server when an entity rotates and moves. Since a byte
 * range is limited from -128 to 127, and movement is offset of fixed-point numbers,
 * this packet allows at most four blocks movement in any direction. (-128/32 == -4)
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayEntityLookRelativeMovePacket(
    override var entityId: Int,
    var x: Double,
    var y: Double,
    var z: Double,
    var yaw: Float,
    var pitch: Float
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ServerPlayEntityLookRelativeMovePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayEntityLookRelativeMovePacket(
            buffer.readInt(),
            buffer.readFixedPoint { readByte().toDouble() },
            buffer.readFixedPoint { readByte().toDouble() },
            buffer.readFixedPoint { readByte().toDouble() },
            buffer.readByte().toFloat(),
            buffer.readByte().toFloat()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayEntityLookRelativeMovePacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.entityId)
            buffer.writeFixedPoint(packet.x) { writeByte(it) }
            buffer.writeFixedPoint(packet.y) { writeByte(it) }
            buffer.writeFixedPoint(packet.z) { writeByte(it) }
            buffer.writeByte(packet.yaw.toInt())
            buffer.writeByte(packet.pitch.toInt())
        }
    }
}
