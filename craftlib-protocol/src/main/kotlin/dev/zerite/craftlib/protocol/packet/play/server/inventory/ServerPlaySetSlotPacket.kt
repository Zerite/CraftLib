package dev.zerite.craftlib.protocol.packet.play.server.inventory

import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.Slot
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to update the data in an inventory slot.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlaySetSlotPacket(
    var windowId: Short,
    var slot: Short,
    var data: Slot
) {
    companion object : PacketIO<ServerPlaySetSlotPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlaySetSlotPacket(
            buffer.readUnsignedByte(),
            buffer.readShort(),
            buffer.readSlot()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlaySetSlotPacket,
            connection: NettyConnection
        ) {
            buffer.writeByte(packet.windowId.toInt())
            buffer.writeShort(packet.slot.toInt())
            buffer.writeSlot(packet.data)
        }
    }
}