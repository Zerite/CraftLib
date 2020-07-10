package dev.zerite.craftlib.protocol.packet.play.client.inventory

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.Slot
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This packet is sent by the player when it clicks on a slot in a window.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ClientPlayClickWindowPacket(
    var windowId: Int,
    var slot: Int,
    var button: Int,
    var action: Int,
    var mode: Int,
    var item: Slot
) : Packet() {
    companion object : PacketIO<ClientPlayClickWindowPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientPlayClickWindowPacket(
            buffer.readByte().toInt(),
            buffer.readShort().toInt(),
            buffer.readByte().toInt(),
            buffer.readShort().toInt(),
            buffer.readByte().toInt(),
            buffer.readSlot()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayClickWindowPacket,
            connection: NettyConnection
        ) {
            buffer.writeByte(packet.windowId)
            buffer.writeShort(packet.slot)
            buffer.writeByte(packet.button)
            buffer.writeShort(packet.action)
            buffer.writeByte(packet.mode)
            buffer.writeSlot(packet.item)
        }
    }
}
