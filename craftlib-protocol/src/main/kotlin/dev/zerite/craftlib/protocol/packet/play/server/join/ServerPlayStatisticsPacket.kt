package dev.zerite.craftlib.protocol.packet.play.server.join

import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to initialize the client's statistics menu.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayStatisticsPacket(var statistics: Array<Statistic>) {

    companion object : PacketIO<ServerPlayStatisticsPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayStatisticsPacket(buffer.readArray { Statistic(readString(), readVarInt()) })

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayStatisticsPacket,
            connection: NettyConnection
        ) {
            buffer.writeArray(packet.statistics) {
                writeString(it.name)
                writeVarInt(it.value)
            }
        }
    }

    /**
     * Single statistic which has a name and int value.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    data class Statistic(
        var name: String,
        var value: Int
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServerPlayStatisticsPacket

        if (!statistics.contentEquals(other.statistics)) return false

        return true
    }

    override fun hashCode(): Int {
        return statistics.contentHashCode()
    }

}