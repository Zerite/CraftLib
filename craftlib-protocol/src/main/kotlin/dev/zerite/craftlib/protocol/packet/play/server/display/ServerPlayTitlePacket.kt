package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.chat.component.BaseChatComponent
import dev.zerite.craftlib.chat.component.StringChatComponent
import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicTitleAction
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent to the client to display a title to the player.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
data class ServerPlayTitlePacket(
    var action: RegistryEntry,
    var text: BaseChatComponent? = null,
    var fadeIn: Int = 0,
    var stay: Int = 0,
    var fadeOut: Int = 0
) : Packet() {
    companion object : PacketIO<ServerPlayTitlePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ): ServerPlayTitlePacket {
            val action = MagicTitleAction[version, buffer.readVarInt()]
            return ServerPlayTitlePacket(
                action,
                buffer.takeIf { action == MagicTitleAction.TITLE || action == MagicTitleAction.SUBTITLE }?.readChat(),
                buffer.takeIf { action == MagicTitleAction.TIMES }?.readInt() ?: 0,
                buffer.takeIf { action == MagicTitleAction.TIMES }?.readInt() ?: 0,
                buffer.takeIf { action == MagicTitleAction.TIMES }?.readInt() ?: 0
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayTitlePacket,
            connection: NettyConnection
        ) {
            buffer.writeVarInt(MagicTitleAction[version, packet.action, Int::class] ?: 0)
            if (packet.action == MagicTitleAction.TITLE || packet.action == MagicTitleAction.SUBTITLE)
                buffer.writeChat(packet.text ?: StringChatComponent(""))
            if (packet.action == MagicTitleAction.TIMES) {
                buffer.writeInt(packet.fadeIn)
                buffer.writeInt(packet.stay)
                buffer.writeInt(packet.fadeOut)
            }
        }
    }
}
