package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.Vector3
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * 0-9 are the displayable destroy stages and each other number means that there is
 * no animation on this coordinate. You can also set an animation to air!
 * The animation will still be visible.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayBlockBreakAnimationPacket(
    override var entityId: Int,
    var x: Int,
    var y: Int,
    var z: Int,
    var stage: Int
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ServerPlayBlockBreakAnimationPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = if (version >= ProtocolVersion.MC1_8) {
            val entityId = buffer.readVarInt()
            val position = buffer.readPosition()
            ServerPlayBlockBreakAnimationPacket(
                entityId,
                position.x,
                position.y,
                position.z,
                buffer.readByte().toInt()
            )
        } else {
            ServerPlayBlockBreakAnimationPacket(
                buffer.readVarInt(),
                buffer.readInt(),
                buffer.readInt(),
                buffer.readInt(),
                buffer.readByte().toInt()
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayBlockBreakAnimationPacket,
            connection: NettyConnection
        ) {
            buffer.writeVarInt(packet.entityId)
            if (version >= ProtocolVersion.MC1_8) buffer.writePosition(Vector3(packet.x, packet.y, packet.z))
            else {
                buffer.writeInt(packet.x)
                buffer.writeInt(packet.y)
                buffer.writeInt(packet.z)
            }
            buffer.writeByte(packet.stage)
        }
    }
}
