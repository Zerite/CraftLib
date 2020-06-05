package dev.zerite.mclib.protocol.packet.play.server.display

import dev.zerite.mclib.chat.component.BaseChatComponent
import dev.zerite.mclib.protocol.PacketIO
import dev.zerite.mclib.protocol.ProtocolBuffer
import dev.zerite.mclib.protocol.connection.NettyConnection
import dev.zerite.mclib.protocol.version.ProtocolVersion

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