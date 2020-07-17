package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.Vector3
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This packet shows location, name, and type of painting.
 * Calculating the center of an image: given a (width x height) grid of cells, with (0, 0) being
 * the top left corner, the center is (max(0, width / 2 - 1), height / 2).
 * E.g. 2x1 (1, 0) 4x4 (1, 2)
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlaySpawnPaintingPacket(
    override var entityId: Int,
    var title: String,
    var x: Int,
    var y: Int,
    var z: Int,
    var direction: Direction
) : EntityIdPacket, Packet() {

    companion object : PacketIO<ServerPlaySpawnPaintingPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = if (version >= ProtocolVersion.MC1_8) {
            val id = buffer.readVarInt()
            val title = buffer.readString(13)
            val position = buffer.readPosition()
            ServerPlaySpawnPaintingPacket(
                id,
                title,
                position.x,
                position.y,
                position.z,
                Direction.values()[buffer.readUnsignedByte().toInt()]
            )
        } else {
            ServerPlaySpawnPaintingPacket(
                buffer.readVarInt(),
                buffer.readString(13),
                buffer.readInt(),
                buffer.readInt(),
                buffer.readInt(),
                Direction.values()[buffer.readInt()]
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlaySpawnPaintingPacket,
            connection: NettyConnection
        ) {
            buffer.writeVarInt(packet.entityId)
            buffer.writeString(packet.title)
            if (version >= ProtocolVersion.MC1_8) buffer.writePosition(Vector3(packet.x, packet.y, packet.z))
            else {
                buffer.writeInt(packet.x)
                buffer.writeInt(packet.y)
                buffer.writeInt(packet.z)
            }
            if (version >= ProtocolVersion.MC1_8) buffer.writeByte(packet.direction.ordinal)
            else buffer.writeInt(packet.direction.ordinal)
        }
    }

    /**
     * Stores the direction values which the painting can be
     * oriented in.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    enum class Direction {
        NEGATIVE_Z,
        NEGATIVE_X,
        POSITIVE_Z,
        POSITIVE_X
    }
}
