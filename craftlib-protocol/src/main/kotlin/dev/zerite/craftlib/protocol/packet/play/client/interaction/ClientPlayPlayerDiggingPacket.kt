package dev.zerite.craftlib.protocol.packet.play.client.interaction

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.Vector3
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicPlayerDiggingStatus
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent when the player mines a block.
 * A Notchian server only accepts digging packets with coordinates within a 6-unit radius of the player's position.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ClientPlayPlayerDiggingPacket(
    var status: RegistryEntry,
    var x: Int,
    var y: Int,
    var z: Int,
    var face: Int
) : Packet() {
    companion object : PacketIO<ClientPlayPlayerDiggingPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = if (version >= ProtocolVersion.MC1_8) {
            val status = MagicPlayerDiggingStatus[version, buffer.readByte().toInt()]
            val position = buffer.readPosition()
            ClientPlayPlayerDiggingPacket(
                status,
                position.x,
                position.y,
                position.z,
                buffer.readByte().toInt()
            )
        } else {
            ClientPlayPlayerDiggingPacket(
                MagicPlayerDiggingStatus[version, buffer.readByte().toInt()],
                buffer.readInt(),
                buffer.readByte().toInt(),
                buffer.readInt(),
                buffer.readByte().toInt()
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayPlayerDiggingPacket,
            connection: NettyConnection
        ) {
            buffer.writeByte(MagicPlayerDiggingStatus[version, packet.status, Int::class] ?: 0)
            if (version >= ProtocolVersion.MC1_8) buffer.writePosition(Vector3(packet.x, packet.y, packet.z))
            else {
                buffer.writeInt(packet.x)
                buffer.writeByte(packet.y)
                buffer.writeInt(packet.z)
            }
            buffer.writeByte(packet.face)
        }
    }
}
