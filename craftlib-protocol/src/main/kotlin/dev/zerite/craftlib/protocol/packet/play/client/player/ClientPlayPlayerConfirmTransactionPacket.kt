package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Confirm Transaction
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */

data class ClientPlayPlayerConfirmTransactionPacket(
        var windowId: Int,
        var actionNumber: Int,
        var accepted: Boolean
) : Packet() {
    companion object : PacketIO<ClientPlayPlayerConfirmTransactionPacket> {
        override fun read(
                buffer: ProtocolBuffer,
                version: ProtocolVersion,
                connection: NettyConnection
        ) = ClientPlayPlayerConfirmTransactionPacket(
                buffer.readByte().toInt(),
                buffer.readShort().toInt(),
                buffer.readBoolean()
        )

        override fun write(
                buffer: ProtocolBuffer,
                version: ProtocolVersion,
                packet: ClientPlayPlayerConfirmTransactionPacket,
                connection: NettyConnection
        ) {
            buffer.writeByte(packet.windowId)
            buffer.writeShort(packet.actionNumber)
            buffer.writeBoolean(packet.accepted)
        }
    }
}