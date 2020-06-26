package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to indicate a single block change to
 * the client.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayBlockChangePacket(
    var x: Int,
    var y: Int,
    var z: Int,
    var type: Int,
    var metadata: Int
) : Packet() {
    companion object : PacketIO<ServerPlayBlockChangePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayBlockChangePacket(
            buffer.readInt(),
            buffer.readUnsignedByte().toInt(),
            buffer.readInt(),
            buffer.readVarInt(),
            buffer.readUnsignedByte().toInt()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayBlockChangePacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.x)
            buffer.writeByte(packet.y)
            buffer.writeInt(packet.z)
            buffer.writeVarInt(packet.type)
            buffer.writeByte(packet.metadata)
        }
    }
}