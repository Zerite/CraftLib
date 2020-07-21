package dev.zerite.craftlib.protocol.packet.play.server.inventory

import dev.zerite.craftlib.chat.component.BaseChatComponent
import dev.zerite.craftlib.chat.component.StringChatComponent
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
data class ServerPlayOpenWindowPacket @JvmOverloads constructor(
    var windowId: Int,
    var type: RegistryEntry,
    var title: BaseChatComponent,
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
            val type = if (version >= ProtocolVersion.MC1_8) MagicInventoryType[version, buffer.readString()]
            else MagicInventoryType[version, buffer.readUnsignedByte().toInt()]
            return ServerPlayOpenWindowPacket(
                id,
                type,
                if (version >= ProtocolVersion.MC1_8) buffer.readChat() else StringChatComponent(buffer.readString()),
                buffer.readUnsignedByte().toInt(),
                if (version >= ProtocolVersion.MC1_8) true else buffer.readBoolean(),
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

            if (version >= ProtocolVersion.MC1_8) {
                buffer.writeString(MagicInventoryType[version, packet.type, String::class.java] ?: "")
                buffer.writeChat(packet.title)
            } else {
                buffer.writeByte(MagicInventoryType[version, packet.type, Int::class.java] ?: 0)
                buffer.writeString(packet.title.unformattedText)
            }

            buffer.writeByte(packet.slots)
            if (version <= ProtocolVersion.MC1_7_6)
                buffer.writeBoolean(packet.useWindowTitle)
            if (packet.type == MagicInventoryType.HORSE) buffer.writeInt(packet.entityId ?: 0)
        }
    }
}
