package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.ScoreboardAction
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This is sent to the client when it should create a new scoreboard or remove one.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayScoreboardObjectivePacket(
    var name: String,
    var value: String,
    var action: RegistryEntry
) : Packet() {
    companion object : PacketIO<ServerPlayScoreboardObjectivePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayScoreboardObjectivePacket(
            buffer.readString(),
            buffer.readString(),
            ScoreboardAction[version, buffer.readByte().toInt()]
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayScoreboardObjectivePacket,
            connection: NettyConnection
        ) {
            buffer.writeString(packet.name)
            buffer.writeString(packet.value)
            buffer.writeByte(ScoreboardAction[version, packet.action, Int::class] ?: 0)
        }
    }
}