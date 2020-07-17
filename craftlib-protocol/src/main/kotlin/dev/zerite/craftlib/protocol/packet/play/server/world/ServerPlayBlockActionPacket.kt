package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.Vector3
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This packet is used for a number of things:
 *  - Chests opening and closing
 *  - Pistons pushing and pulling
 *  - Note blocks playing
 *
 *  @author Koding
 *  @since  0.1.0-SNAPSHOT
 */
data class ServerPlayBlockActionPacket(
    var x: Int,
    var y: Int,
    var z: Int,
    var first: Int,
    var second: Int,
    var type: Int
) : Packet() {
    companion object : PacketIO<ServerPlayBlockActionPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = if (version >= ProtocolVersion.MC1_8) {
            val position = buffer.readPosition()
            ServerPlayBlockActionPacket(
                position.x,
                position.y,
                position.z,
                buffer.readUnsignedByte().toInt(),
                buffer.readUnsignedByte().toInt(),
                buffer.readVarInt()
            )
        } else {
            ServerPlayBlockActionPacket(
                buffer.readInt(),
                buffer.readShort().toInt(),
                buffer.readInt(),
                buffer.readUnsignedByte().toInt(),
                buffer.readUnsignedByte().toInt(),
                buffer.readVarInt()
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayBlockActionPacket,
            connection: NettyConnection
        ) {
            if (version >= ProtocolVersion.MC1_8) buffer.writePosition(Vector3(packet.x, packet.y, packet.z))
            else {
                buffer.writeInt(packet.x)
                buffer.writeShort(packet.y)
                buffer.writeInt(packet.z)
            }
            buffer.writeByte(packet.first)
            buffer.writeByte(packet.second)
            buffer.writeVarInt(packet.type)
        }
    }
}
