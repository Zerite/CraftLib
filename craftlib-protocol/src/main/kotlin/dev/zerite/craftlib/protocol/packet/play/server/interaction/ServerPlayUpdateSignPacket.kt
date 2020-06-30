package dev.zerite.craftlib.protocol.packet.play.server.interaction

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This message is sent from the server to the client whenever a sign is discovered
 * or created. This message is NOT sent when a sign is destroyed or unloaded.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayUpdateSignPacket(
    var x: Int,
    var y: Int,
    var z: Int,
    var first: String,
    var second: String,
    var third: String,
    var forth: String
) : Packet() {
    companion object : PacketIO<ServerPlayUpdateSignPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayUpdateSignPacket(
            buffer.readInt(),
            buffer.readShort().toInt(),
            buffer.readInt(),
            buffer.readString(),
            buffer.readString(),
            buffer.readString(),
            buffer.readString()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayUpdateSignPacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.x)
            buffer.writeShort(packet.y)
            buffer.writeInt(packet.z)
            buffer.writeString(packet.first)
            buffer.writeString(packet.second)
            buffer.writeString(packet.third)
            buffer.writeString(packet.forth)
        }
    }
}