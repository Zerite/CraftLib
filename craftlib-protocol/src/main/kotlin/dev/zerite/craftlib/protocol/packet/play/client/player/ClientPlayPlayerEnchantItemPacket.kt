package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Enchant Item
 *
 * @author chachy
 * @since 0.1.0-SNAPSHOT
 */


data class ClientPlayPlayerEnchantItemPacket(
        var windowId: Int,
        var enchantment: Int
) : Packet() {
    companion object : PacketIO<ClientPlayPlayerEnchantItemPacket> {
        override fun read(
                buffer: ProtocolBuffer,
                version: ProtocolVersion,
                connection: NettyConnection
        ) = ClientPlayPlayerEnchantItemPacket(
                buffer.readByte().toInt(),
                buffer.readByte().toInt()
        )

        override fun write(
                buffer: ProtocolBuffer,
                version: ProtocolVersion,
                packet: ClientPlayPlayerEnchantItemPacket,
                connection: NettyConnection
        ) {
            buffer.writeByte(packet.windowId)
            buffer.writeByte(packet.enchantment)
        }
    }
}