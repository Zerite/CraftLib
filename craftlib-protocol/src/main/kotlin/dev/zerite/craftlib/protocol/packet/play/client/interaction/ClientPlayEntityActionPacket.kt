package dev.zerite.craftlib.protocol.packet.play.client.interaction

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicEntityAction
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent at least when crouching, leaving a bed, or sprinting. To send action animation to client use 0x28.
 * The client will send this with Action ID = 3 when "Leave Bed" is clicked.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ClientPlayEntityActionPacket(
    override var entityId: Int,
    var action: RegistryEntry,
    var jumpBoost: Int
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ClientPlayEntityActionPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientPlayEntityActionPacket(
            buffer.readInt(),
            MagicEntityAction[version, buffer.readByte().toInt()],
            buffer.readInt()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayEntityActionPacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.entityId)
            buffer.writeByte(MagicEntityAction[version, packet.action, Int::class] ?: 0)
            buffer.writeInt(packet.jumpBoost)
        }
    }
}
