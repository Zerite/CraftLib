package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent when the user presses [tab] while writing text. The payload contains all text behind the cursor.
 *
 * @author chachy
 * @since 0.1.0-SNAPSHOT
 */
data class ClientPlayPlayerTabCompletePacket(
        var text: String
) : Packet() {
    companion object : PacketIO<ClientPlayPlayerTabCompletePacket> {
        override fun read(
                buffer: ProtocolBuffer,
                version: ProtocolVersion,
                connection: NettyConnection
        ) = ClientPlayPlayerTabCompletePacket(
                buffer.readString()
        )

        override fun write(
                buffer: ProtocolBuffer,
                version: ProtocolVersion,
                packet: ClientPlayPlayerTabCompletePacket,
                connection: NettyConnection
        ) {
            buffer.writeString(packet.text)
        }
    }
}