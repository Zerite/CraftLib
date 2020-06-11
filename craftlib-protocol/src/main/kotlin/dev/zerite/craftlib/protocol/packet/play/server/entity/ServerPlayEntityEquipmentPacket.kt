package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.Slot
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to indicate that an entity has a certain item
 * equipped in their armor slot.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayEntityEquipmentPacket(
    override var entityId: Int,
    var slot: Short,
    var item: Slot
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ServerPlayEntityEquipmentPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayEntityEquipmentPacket(
            buffer.readInt(),
            buffer.readShort(),
            buffer.readSlot()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayEntityEquipmentPacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.entityId)
            buffer.writeShort(packet.slot.toInt())
            buffer.writeSlot(packet.item)
        }
    }
}