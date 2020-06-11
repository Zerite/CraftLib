package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to indicate that the tab list should be updated
 * with a new player, or one should be removed. If online is true, a new
 * player is added to the list or has their ping updated if they're already present.
 * If false, it will remove the player from the list.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayPlayerListItemPacket(var name: String, var online: Boolean, var ping: Int) : Packet() {
    companion object : PacketIO<ServerPlayPlayerListItemPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayPlayerListItemPacket(
            buffer.readString(),
            buffer.readBoolean(),
            buffer.readShort().toInt()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayPlayerListItemPacket,
            connection: NettyConnection
        ) {
            buffer.writeString(packet.name)
            buffer.writeBoolean(packet.online)
            buffer.writeShort(packet.ping)
        }
    }

    init {
        if (ping > Short.MAX_VALUE || ping < Short.MIN_VALUE) error("Ping is out of bounds")
    }
}