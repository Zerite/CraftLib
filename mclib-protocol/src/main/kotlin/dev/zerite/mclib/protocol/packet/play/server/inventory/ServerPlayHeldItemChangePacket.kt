package dev.zerite.mclib.protocol.packet.play.server.inventory

import dev.zerite.mclib.protocol.PacketIO
import dev.zerite.mclib.protocol.ProtocolBuffer
import dev.zerite.mclib.protocol.connection.NettyConnection
import dev.zerite.mclib.protocol.version.ProtocolVersion

/**
 * Sent by the server to change the currently held slot of the
 * client player.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayHeldItemChangePacket(var slot: Int) {

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