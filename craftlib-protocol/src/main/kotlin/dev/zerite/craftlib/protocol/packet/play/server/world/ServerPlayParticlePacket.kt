package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Displays the named particle.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayParticlePacket(
    var name: String,
    var x: Float,
    var y: Float,
    var z: Float,
    var offsetX: Float,
    var offsetY: Float,
    var offsetZ: Float,
    var data: Float,
    var count: Int
) : Packet() {
    companion object : PacketIO<ServerPlayParticlePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayParticlePacket(
                buffer.readString(),
            buffer.readFloat(),
            buffer.readFloat(),
            buffer.readFloat(),
            buffer.readFloat(),
            buffer.readFloat(),
            buffer.readFloat(),
            buffer.readFloat(),
            buffer.readInt()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayParticlePacket,
            connection: NettyConnection
        ) {
            buffer.writeString(packet.name)
            buffer.writeFloat(packet.x)
            buffer.writeFloat(packet.y)
            buffer.writeFloat(packet.z)
            buffer.writeFloat(packet.offsetX)
            buffer.writeFloat(packet.offsetY)
            buffer.writeFloat(packet.offsetZ)
            buffer.writeFloat(packet.data)
            buffer.writeInt(packet.count)
        }
    }
}