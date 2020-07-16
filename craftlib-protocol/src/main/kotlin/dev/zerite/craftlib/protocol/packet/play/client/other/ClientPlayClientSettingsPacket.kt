package dev.zerite.craftlib.protocol.packet.play.client.other

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicDifficulty
import dev.zerite.craftlib.protocol.data.registry.impl.MagicViewDistance
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent when the player connects, or when settings are changed.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ClientPlayClientSettingsPacket(
    var locale: String,
    var viewDistance: RegistryEntry,
    var chatFlags: Int,
    var difficulty: RegistryEntry,
    var showCape: Boolean
) : Packet() {
    companion object : PacketIO<ClientPlayClientSettingsPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientPlayClientSettingsPacket(
            buffer.readString(),
            MagicViewDistance[version, buffer.readByte().toInt()],
            buffer.readByte().toInt().apply { buffer.readBoolean() },
            MagicDifficulty[version, buffer.readByte().toInt()],
            buffer.readBoolean()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayClientSettingsPacket,
            connection: NettyConnection
        ) {
            buffer.writeString(packet.locale)
            buffer.writeByte(MagicViewDistance[version, packet.viewDistance, Int::class] ?: 0)
            buffer.writeByte(packet.chatFlags)
            buffer.writeBoolean(true)
            buffer.writeByte(MagicDifficulty[version, packet.difficulty, Int::class] ?: 0)
            buffer.writeBoolean(packet.showCape)
        }
    }
}
