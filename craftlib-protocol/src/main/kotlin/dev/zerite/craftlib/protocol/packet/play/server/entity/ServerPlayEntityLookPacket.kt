package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to indicate that an entity is looking in a
 * new direction.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayEntityLookPacket(
    override var entityId: Int,
    var yaw: Float,
    var pitch: Float
) : EntityIdPacket, Packet() {

    companion object : PacketIO<ServerPlayEntityLookPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayEntityLookPacket(
            buffer.readInt(),
            buffer.readStepRotation(),
            buffer.readStepRotation()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayEntityLookPacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.entityId)
            buffer.writeStepRotation(packet.yaw)
            buffer.writeStepRotation(packet.pitch)
        }
    }

}