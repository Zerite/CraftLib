package dev.zerite.craftlib.protocol.packet.play.client.other

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicDifficulty
import dev.zerite.craftlib.protocol.data.registry.impl.MagicViewDistance
import dev.zerite.craftlib.protocol.util.delegate.bitBoolean
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent when the player connects, or when settings are changed.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("UNUSED")
data class ClientPlayClientSettingsPacket(
    var locale: String,
    var viewDistance: RegistryEntry,
    var chatFlags: Int,
    var difficulty: RegistryEntry,
    var skinParts: Int = 0
) : Packet() {

    /**
     * Stores the flags for the skin parts integer.
     */
    var cape by bitBoolean(this::skinParts, 0x1)
    var jacket by bitBoolean(this::skinParts, 0x2)
    var leftSleeve by bitBoolean(this::skinParts, 0x4)
    var rightSleeve by bitBoolean(this::skinParts, 0x8)
    var leftLeg by bitBoolean(this::skinParts, 0x10)
    var rightLeg by bitBoolean(this::skinParts, 0x20)
    var hat by bitBoolean(this::skinParts, 0x40)

    companion object : PacketIO<ClientPlayClientSettingsPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientPlayClientSettingsPacket(
            buffer.readString(),
            MagicViewDistance[version, buffer.readByte().toInt()],
            buffer.readByte().toInt().apply { buffer.readBoolean() },
            if (version >= ProtocolVersion.MC1_8) MagicDifficulty.PEACEFUL else MagicDifficulty[version, buffer.readByte().toInt()],
            if (version >= ProtocolVersion.MC1_8) buffer.readUnsignedByte().toInt() else if (buffer.readBoolean()) 0x1 else 0x0
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
            if (version <= ProtocolVersion.MC1_7_6) {
                buffer.writeByte(MagicDifficulty[version, packet.difficulty, Int::class] ?: 0)
                buffer.writeBoolean(packet.cape)
            } else {
                buffer.writeByte(packet.skinParts)
            }
        }
    }
}
