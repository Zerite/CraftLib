package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * If the first byte of the array is 0, the next two bytes are X start and Y start and
 * the rest of the bytes are the colors in that column.
 *
 * If the first byte of the array is 1, the rest of the bytes are in groups of
 * three: (data, x, y). The lower half of the data is the type (always 0 under vanilla)
 * and the upper half is the direction.
 *
 * If the first byte of the array is 2, the second byte is the map scale.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayMapsPacket(
    var damage: Int,
    var data: ByteArray
) : Packet() {
    companion object : PacketIO<ServerPlayMapsPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayMapsPacket(
            buffer.readVarInt(),
            buffer.readByteArray { readShort().toInt() }
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayMapsPacket,
            connection: NettyConnection
        ) {
            buffer.writeVarInt(packet.damage)
            buffer.writeByteArray(packet.data) { writeShort(it) }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServerPlayMapsPacket

        if (damage != other.damage) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = damage
        result = 31 * result + data.contentHashCode()
        return result
    }
}