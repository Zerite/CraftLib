package dev.zerite.craftlib.protocol.packet.play.client.inventory

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.Slot
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * While the user is in the standard inventory (i.e., not a crafting bench) on a creative-mode server then the server will send this packet:
 * If an item is dropped into the quick bar
 * If an item is picked up from the quick bar (item id is -1)
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */
data class ClientPlayCreativeInventoryActionPacket(
    var slot: Int,
    var clickedItem: Slot
) : Packet() {
    companion object : PacketIO<ClientPlayCreativeInventoryActionPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientPlayCreativeInventoryActionPacket(
            buffer.readShort().toInt(),
            buffer.readSlot()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayCreativeInventoryActionPacket,
            connection: NettyConnection
        ) {
            buffer.writeShort(packet.slot)
            buffer.writeSlot(packet.clickedItem)
        }
    }
}
