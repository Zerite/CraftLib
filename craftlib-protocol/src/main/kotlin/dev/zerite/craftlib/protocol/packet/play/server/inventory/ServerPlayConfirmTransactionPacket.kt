package dev.zerite.craftlib.protocol.packet.play.server.inventory

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * A packet from the server indicating whether a request from the client was accepted,
 * or whether there was a conflict (due to lag). This packet is also sent from the
 * client to the server in response to a server transaction rejection packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayConfirmTransactionPacket(
    var windowId: Int,
    var actionNumber: Int,
    var accepted: Boolean
) : Packet() {
    companion object : PacketIO<ServerPlayConfirmTransactionPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayConfirmTransactionPacket(
            if (version >= ProtocolVersion.MC1_8) buffer.readByte().toInt() else buffer.readUnsignedByte().toInt(),
            buffer.readShort().toInt(),
            buffer.readBoolean()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayConfirmTransactionPacket,
            connection: NettyConnection
        ) {
            buffer.writeByte(packet.windowId)
            buffer.writeShort(packet.actionNumber)
            buffer.writeBoolean(packet.accepted)
        }
    }
}
