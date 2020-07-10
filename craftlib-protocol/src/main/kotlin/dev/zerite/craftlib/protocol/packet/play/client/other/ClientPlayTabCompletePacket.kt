package dev.zerite.craftlib.protocol.packet.play.client.other

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent when the user presses \[tab] while writing text. The payload contains all text behind the cursor.
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */
data class ClientPlayTabCompletePacket(
        var text: String
) : Packet() {
    companion object : PacketIO<ClientPlayTabCompletePacket> {
        override fun read(
                buffer: ProtocolBuffer,
                version: ProtocolVersion,
                connection: NettyConnection
        ) = ClientPlayTabCompletePacket(
            buffer.readString()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayTabCompletePacket,
            connection: NettyConnection
        ) {
            buffer.writeString(packet.text)
        }
    }
}
