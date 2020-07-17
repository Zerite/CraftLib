package dev.zerite.craftlib.protocol.packet.play.client.interaction

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import java.util.*

/**
 * Sent to the server to indicate that the player is now spectating another player.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
data class ClientPlaySpectatePacket(var target: UUID) : Packet() {
    companion object : PacketIO<ClientPlaySpectatePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientPlaySpectatePacket(buffer.readUUID(mode = ProtocolBuffer.UUIDMode.RAW))

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlaySpectatePacket,
            connection: NettyConnection
        ) {
            buffer.writeUUID(packet.target, mode = ProtocolBuffer.UUIDMode.RAW)
        }
    }
}
