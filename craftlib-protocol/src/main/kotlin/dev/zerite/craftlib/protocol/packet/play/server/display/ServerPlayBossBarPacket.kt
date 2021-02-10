package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.chat.component.BaseChatComponent
import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicBossBarAction
import dev.zerite.craftlib.protocol.data.registry.impl.MagicBossBarColor
import dev.zerite.craftlib.protocol.data.registry.impl.MagicBossBarDivision
import dev.zerite.craftlib.protocol.util.delegate.bitBoolean
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import java.util.*

/**
 * Allows the server to send multiple custom boss bars without the need to
 * use an invisible ender dragon alike versions before 1.9.
 *
 * @author Koding
 * @since  0.2.0
 */
@Suppress("UNUSED")
data class ServerPlayBossBarPacket(
    var uuid: UUID,
    var action: RegistryEntry,
    var title: BaseChatComponent? = null,
    var health: Float? = null,
    var color: RegistryEntry? = null,
    var division: RegistryEntry? = null,
    var flags: Int = 0
) : Packet() {

    companion object : PacketIO<ServerPlayBossBarPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ): ServerPlayBossBarPacket {
            val uuid = buffer.readUUID(mode = ProtocolBuffer.UUIDMode.RAW)
            val action = MagicBossBarAction[version, buffer.readVarInt()]

            return ServerPlayBossBarPacket(
                uuid,
                action,
                buffer.takeIf { action == MagicBossBarAction.ADD || action == MagicBossBarAction.UPDATE_TITLE }
                    ?.readChat(),
                buffer.takeIf { action == MagicBossBarAction.ADD || action == MagicBossBarAction.UPDATE_HEALTH }
                    ?.readFloat(),
                buffer.takeIf { action == MagicBossBarAction.ADD || action == MagicBossBarAction.UPDATE_STYLE }
                    ?.readVarInt()?.let { MagicBossBarColor[version, it] },
                buffer.takeIf { action == MagicBossBarAction.ADD || action == MagicBossBarAction.UPDATE_STYLE }
                    ?.readVarInt()?.let { MagicBossBarDivision[version, it] },
                buffer.takeIf { action == MagicBossBarAction.ADD || action == MagicBossBarAction.UPDATE_FLAGS }
                    ?.readUnsignedByte()?.toInt() ?: 0
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayBossBarPacket,
            connection: NettyConnection
        ) {
            buffer.writeUUID(packet.uuid, mode = ProtocolBuffer.UUIDMode.RAW)
            buffer.writeVarInt(
                MagicBossBarAction[version, packet.action, Int::class.java] ?: error("Invalid boss bar action")
            )

            if (packet.action == MagicBossBarAction.ADD || packet.action == MagicBossBarAction.UPDATE_TITLE)
                buffer.writeChat(packet.title ?: error("Title not specified"))
            if (packet.action == MagicBossBarAction.ADD || packet.action == MagicBossBarAction.UPDATE_HEALTH)
                buffer.writeFloat(packet.health ?: error("Health not specified"))

            if (packet.action == MagicBossBarAction.ADD || packet.action == MagicBossBarAction.UPDATE_STYLE) {
                buffer.writeVarInt(packet.color?.let { MagicBossBarColor[version, it, Int::class.java] }
                    ?: error("Color not specified"))
                buffer.writeVarInt(packet.division?.let { MagicBossBarDivision[version, it, Int::class.java] } ?: error(
                    "Division not specified"
                ))
            }

            if (packet.action == MagicBossBarAction.ADD || packet.action == MagicBossBarAction.UPDATE_FLAGS)
                buffer.writeByte(packet.flags)
        }
    }

    /**
     * Whether the sky should be darkened when this boss bar is being displayed.
     */
    var darkenSky by bitBoolean(this::flags, 0x1)

    /**
     * Only set as true for the ender dragon, used to play the boss music.
     */
    var playMusic by bitBoolean(this::flags, 0x2)

}