package dev.zerite.craftlib.protocol.packet.play.server.entity.movement

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
    var pitch: Float,
    var onGround: Boolean = false
) : EntityIdPacket, Packet() {

    companion object : PacketIO<ServerPlayEntityLookPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) =
            ServerPlayEntityLookPacket(
                if (version >= ProtocolVersion.MC1_8) buffer.readVarInt() else buffer.readInt(),
                buffer.readStepRotation(),
                buffer.readStepRotation(),
                if (version >= ProtocolVersion.MC1_8) buffer.readBoolean() else false
            )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayEntityLookPacket,
            connection: NettyConnection
        ) {
            if (version >= ProtocolVersion.MC1_8) buffer.writeVarInt(packet.entityId)
            else buffer.writeInt(packet.entityId)
            buffer.writeStepRotation(packet.yaw)
            buffer.writeStepRotation(packet.pitch)
            if (version >= ProtocolVersion.MC1_8)
                buffer.writeBoolean(packet.onGround)
        }
    }

}
