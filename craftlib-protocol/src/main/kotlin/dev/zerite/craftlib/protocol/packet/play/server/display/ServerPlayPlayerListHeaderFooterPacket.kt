package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.chat.component.BaseChatComponent
import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Updates the player list header and footer for the client.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
data class ServerPlayPlayerListHeaderFooterPacket(
    var header: BaseChatComponent,
    var footer: BaseChatComponent
) : Packet() {
    companion object : PacketIO<ServerPlayPlayerListHeaderFooterPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayPlayerListHeaderFooterPacket(
            buffer.readChat(),
            buffer.readChat()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayPlayerListHeaderFooterPacket,
            connection: NettyConnection
        ) {
            buffer.writeChat(packet.header)
            buffer.writeChat(packet.footer)
        }
    }
}
