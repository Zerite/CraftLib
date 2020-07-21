package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Reads map data containing info about players and landmarks.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
data class ServerPlayMapsPacket @JvmOverloads constructor(
    var damage: Int,
    var scale: Int,
    var players: Array<PlayerIcon>,
    var columns: Int,
    var rows: Int? = null,
    var x: Int? = null,
    var y: Int? = null,
    var data: ByteArray? = ByteArray(0)
) : Packet() {
    companion object : PacketIO<ServerPlayMapsPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ): ServerPlayMapsPacket {
            val damage = buffer.readVarInt()
            val scale = if (version >= ProtocolVersion.MC1_8) buffer.readByte().toInt() else 1
            val players = if (version >= ProtocolVersion.MC1_8) buffer.readArray {
                val byte = readByte().toInt()
                PlayerIcon(
                    byte and 0xF,
                    (byte shr 4) and 0xF,
                    readByte().toInt(),
                    readByte().toInt()
                )
            } else arrayOf()
            val columns = if (version >= ProtocolVersion.MC1_8) buffer.readUnsignedByte().toInt() else 0

            return ServerPlayMapsPacket(
                damage,
                scale,
                players,
                columns,
                if (columns > 0) buffer.readUnsignedByte().toInt() else null,
                if (columns > 0) buffer.readUnsignedByte().toInt() else null,
                if (columns > 0) buffer.readUnsignedByte().toInt() else null,
                when {
                    columns > 0 -> buffer.readByteArray()
                    version <= ProtocolVersion.MC1_7_6 -> buffer.readByteArray { readShort().toInt() }
                    else -> ByteArray(0)
                }
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayMapsPacket,
            connection: NettyConnection
        ) {
            buffer.writeVarInt(packet.damage)
            if (version >= ProtocolVersion.MC1_8) {
                buffer.writeByte(packet.scale)
                buffer.writeArray(packet.players) {
                    writeByte(((it.direction and 0xF) shl 4) or (it.type and 0xF))
                    writeByte(it.x)
                    writeByte(it.y)
                }
                buffer.writeByte(packet.columns)

                if (packet.columns > 0) {
                    buffer.writeByte(packet.rows ?: 0)
                    buffer.writeByte(packet.x ?: 0)
                    buffer.writeByte(packet.y ?: 0)
                    buffer.writeByteArray(packet.data ?: ByteArray(0))
                }
            } else buffer.writeByteArray(packet.data ?: ByteArray(0)) { writeShort(it) }
        }
    }

    /**
     * Instance of a single player icon on the map.
     *
     * @author Koding
     * @since  0.1.1-SNAPSHOT
     */
    data class PlayerIcon(
        var direction: Int,
        var type: Int,
        var x: Int,
        var y: Int
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServerPlayMapsPacket

        if (damage != other.damage) return false
        if (scale != other.scale) return false
        if (!players.contentEquals(other.players)) return false
        if (columns != other.columns) return false
        if (rows != other.rows) return false
        if (x != other.x) return false
        if (y != other.y) return false
        if (data != null) {
            if (other.data == null) return false
            if (!data!!.contentEquals(other.data!!)) return false
        } else if (other.data != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = damage
        result = 31 * result + scale
        result = 31 * result + players.contentHashCode()
        result = 31 * result + columns
        result = 31 * result + (rows ?: 0)
        result = 31 * result + (x ?: 0)
        result = 31 * result + (y ?: 0)
        result = 31 * result + (data?.contentHashCode() ?: 0)
        return result
    }
}
