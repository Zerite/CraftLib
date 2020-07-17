package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicScoreboardAction
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This is sent to the client when it should create a new scoreboard or remove one.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayScoreboardObjectivePacket(
    var name: String,
    var action: RegistryEntry,
    var value: String? = "",
    var type: String? = "integer"
) : Packet() {
    companion object : PacketIO<ServerPlayScoreboardObjectivePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = if (version >= ProtocolVersion.MC1_8) {
            val name = buffer.readString()
            val mode = MagicScoreboardAction[version, buffer.readByte().toInt()]
            ServerPlayScoreboardObjectivePacket(
                name,
                mode,
                if (mode == MagicScoreboardAction.CREATE_SCOREBOARD || mode == MagicScoreboardAction.UPDATE_TEXT) buffer.readString() else "",
                if (mode == MagicScoreboardAction.CREATE_SCOREBOARD || mode == MagicScoreboardAction.UPDATE_TEXT) buffer.readString() else "integer"
            )
        } else {
            val name = buffer.readString()
            val value = buffer.readString()
            val mode = MagicScoreboardAction[version, buffer.readByte().toInt()]
            ServerPlayScoreboardObjectivePacket(
                name,
                mode,
                value
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayScoreboardObjectivePacket,
            connection: NettyConnection
        ) {
            buffer.writeString(packet.name)

            if (version >= ProtocolVersion.MC1_8) {
                buffer.writeByte(MagicScoreboardAction[version, packet.action, Int::class] ?: 0)
                if (packet.action == MagicScoreboardAction.CREATE_SCOREBOARD || packet.action == MagicScoreboardAction.UPDATE_TEXT) {
                    buffer.writeString(packet.value ?: "")
                    buffer.writeString(packet.type ?: "integer")
                }
            } else {
                buffer.writeString(packet.value ?: "")
                buffer.writeByte(MagicScoreboardAction[version, packet.action, Int::class] ?: 0)
            }
        }
    }
}
