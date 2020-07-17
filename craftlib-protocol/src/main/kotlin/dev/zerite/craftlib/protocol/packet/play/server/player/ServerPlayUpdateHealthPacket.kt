package dev.zerite.craftlib.protocol.packet.play.server.player

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to update both the health and food values of
 * the client player.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayUpdateHealthPacket(
    var health: Float,
    var food: Int,
    var saturation: Float
) : Packet() {
    companion object : PacketIO<ServerPlayUpdateHealthPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayUpdateHealthPacket(
            buffer.readFloat(),
            if (version >= ProtocolVersion.MC1_8) buffer.readVarInt() else buffer.readShort().toInt(),
            buffer.readFloat()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayUpdateHealthPacket,
            connection: NettyConnection
        ) {
            buffer.writeFloat(packet.health)
            if (version >= ProtocolVersion.MC1_8) buffer.writeVarInt(packet.food)
            else buffer.writeShort(packet.food)
            buffer.writeFloat(packet.saturation)
        }
    }
}
