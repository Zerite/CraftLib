package dev.zerite.craftlib.protocol.packet.play.server.inventory

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to change the currently held slot of the
 * client player.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayHeldItemChangePacket(var slot: Int) : Packet() {

    companion object : PacketIO<ServerPlayHeldItemChangePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayHeldItemChangePacket(
            buffer.readByte().toInt()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayHeldItemChangePacket,
            connection: NettyConnection
        ) {
            buffer.writeByte(packet.slot)
        }
    }

    init {
        if (slot > 8) error("Slot too large")
    }
}