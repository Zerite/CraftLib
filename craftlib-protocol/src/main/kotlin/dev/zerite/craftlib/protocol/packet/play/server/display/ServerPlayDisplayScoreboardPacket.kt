package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicScoreboardPosition
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This is sent to the client when it should display a scoreboard.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayDisplayScoreboardPacket(
    var position: RegistryEntry,
    var name: String
) : Packet() {
    companion object : PacketIO<ServerPlayDisplayScoreboardPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayDisplayScoreboardPacket(
            MagicScoreboardPosition[version, buffer.readByte().toInt()],
            buffer.readString()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayDisplayScoreboardPacket,
            connection: NettyConnection
        ) {
            buffer.writeByte(MagicScoreboardPosition[version, packet.position, Int::class] ?: 0)
            buffer.writeString(packet.name)
        }
    }
}
