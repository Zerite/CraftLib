package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to update multiple block changes in a
 * single packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayMultiBlockChangePacket(
    var chunkX: Int,
    var chunkZ: Int,
    var records: Array<Record>
) : Packet() {

    companion object : PacketIO<ServerPlayMultiBlockChangePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayMultiBlockChangePacket(
            buffer.readInt(),
            buffer.readInt(),
            buffer.readArray({
                readShort().toInt().apply { readInt() }
            }) {
                val first = readShort().toInt()
                val second = readShort().toInt()

                Record(
                    second and 15,
                    second shr 4 and 4095,
                    first shr 12 and 15,
                    first and 255,
                    first shr 8 and 15
                )
            }
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayMultiBlockChangePacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.chunkX)
            buffer.writeInt(packet.chunkZ)
            buffer.writeArray(packet.records, {
                writeShort(it).apply { writeInt(it * 4) }
            }) {
                // Position
                writeShort((it.x and 15 shl 12) or (it.z and 15 shl 8) or (it.y and 255))

                // Block data
                writeShort((it.id shl 4 and 4095) or (it.metadata and 15))
            }
        }
    }

    /**
     * Represents a single block change in the multi block change packet.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    data class Record(
        var metadata: Int,
        var id: Int,
        var x: Int,
        var y: Int,
        var z: Int
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServerPlayMultiBlockChangePacket

        if (chunkX != other.chunkX) return false
        if (chunkZ != other.chunkZ) return false
        if (!records.contentEquals(other.records)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = chunkX
        result = 31 * result + chunkZ
        result = 31 * result + records.contentHashCode()
        return result
    }

}