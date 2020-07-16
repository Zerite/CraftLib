package dev.zerite.craftlib.protocol.packet.play.server.inventory

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicInventoryType
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This is sent to the client when it should open an inventory,
 * such as a chest, workbench, or furnace. This message is not
 * sent anywhere for clients opening their own inventory.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayOpenWindowPacket(
    var windowId: Int,
    var type: RegistryEntry,
    var title: String,
    var slots: Int,
    var useWindowTitle: Boolean,
    var entityId: Int? = null
) : Packet() {
    companion object : PacketIO<ServerPlayOpenWindowPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ): ServerPlayOpenWindowPacket {
            val id = buffer.readUnsignedByte().toInt()
            val type = MagicInventoryType[version, buffer.readUnsignedByte().toInt()]
            return ServerPlayOpenWindowPacket(
                id,
                type,
                buffer.readString(),
                buffer.readUnsignedByte().toInt(),
                buffer.readBoolean(),
                buffer.takeIf { type == MagicInventoryType.HORSE }?.readInt()
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayOpenWindowPacket,
            connection: NettyConnection
        ) {
            buffer.writeByte(packet.windowId)
            buffer.writeByte(MagicInventoryType[version, packet.type, Int::class] ?: 0)
            buffer.writeString(packet.title)
            buffer.writeByte(packet.slots)
            buffer.writeBoolean(packet.useWindowTitle)
            if (packet.type == MagicInventoryType.HORSE) buffer.writeInt(packet.entityId ?: 0)
        }
    }
}
