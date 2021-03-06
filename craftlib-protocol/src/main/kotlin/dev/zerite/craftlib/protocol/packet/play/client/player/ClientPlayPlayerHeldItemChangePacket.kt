package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent when the player changes the slot selection
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */
data class ClientPlayPlayerHeldItemChangePacket(
    var slot: Int
) : Packet() {
    companion object : PacketIO<ClientPlayPlayerHeldItemChangePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientPlayPlayerHeldItemChangePacket(
            buffer.readShort().toInt()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayPlayerHeldItemChangePacket,
            connection: NettyConnection
        ) {
            buffer.writeShort(packet.slot)
        }
    }
}