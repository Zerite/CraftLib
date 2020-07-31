package dev.zerite.craftlib.protocol.packet.play.server.other

import dev.zerite.craftlib.chat.component.BaseChatComponent
import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server before it disconnects a client. The server assumes that the sender has
 * already closed the connection by the time the packet arrives.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayDisconnectPacket(var reason: BaseChatComponent) : Packet() {
    companion object : PacketIO<ServerPlayDisconnectPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayDisconnectPacket(buffer.readChat())

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayDisconnectPacket,
            connection: NettyConnection
        ) {
            buffer.writeChat(packet.reason)
        }
    }
}
