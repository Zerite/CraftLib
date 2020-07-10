package dev.zerite.craftlib.protocol.packet.play.client.interaction

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.Slot
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * In normal operation (ie placing a block), this packet is sent once, with the values set normally.
 *
 * This packet has a special case where X, Y, Z, and Direction are all -1. (Note that Y is unsigned so set to 255.)
 * This special packet indicates that the currently held item for the player should have its state updated such as
 * eating food, shooting bows, using buckets, etc.
 *
 * In a Notchian Beta client, the block or item ID corresponds to whatever the client is currently holding,
 * and the client sends one of these packets any time a right-click is issued on a surface, so no
 * assumptions can be made about the safety of the ID. However, with the implementation of server-side inventory,
 * a Notchian server seems to ignore the item ID, instead operating on server-side inventory information and holding selection.
 * The client has been observed (1.2.5 and 1.3.2) to send both real item IDs and -1 in a single session.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ClientPlayPlayerBlockPlacementPacket(
    var x: Int,
    var y: Int,
    var z: Int,
    var direction: Int,
    var heldItem: Slot,
    var cursorX: Int,
    var cursorY: Int,
    var cursorZ: Int
) : Packet() {
    companion object : PacketIO<ClientPlayPlayerBlockPlacementPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientPlayPlayerBlockPlacementPacket(
            buffer.readInt(),
            buffer.readUnsignedByte().toInt(),
            buffer.readInt(),
            buffer.readByte().toInt(),
            buffer.readSlot(),
            buffer.readByte().toInt(),
            buffer.readByte().toInt(),
            buffer.readByte().toInt()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayPlayerBlockPlacementPacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.x)
            buffer.writeByte(packet.y)
            buffer.writeInt(packet.z)
            buffer.writeByte(packet.direction)
            buffer.writeSlot(packet.heldItem)
            buffer.writeByte(packet.cursorX)
            buffer.writeByte(packet.cursorY)
            buffer.writeByte(packet.cursorZ)
        }
    }
}
