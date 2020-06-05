package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.chat.component.BaseChatComponent
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to display a chat message in-game.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayChatMessagePacket(var message: BaseChatComponent) {
    companion object : PacketIO<ServerPlayChatMessagePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayChatMessagePacket(buffer.readChat())

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayChatMessagePacket,
            connection: NettyConnection
        ) {
            buffer.writeChat(packet.message)
        }
    }
}