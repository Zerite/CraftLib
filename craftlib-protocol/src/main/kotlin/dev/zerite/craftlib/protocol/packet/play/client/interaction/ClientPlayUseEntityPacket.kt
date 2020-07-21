package dev.zerite.craftlib.protocol.packet.play.client.interaction

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicUseEntityType
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This packet is sent from the client to the server when the client attacks or
 * right-clicks another entity (a player, minecart, etc).
 *
 * A Notchian server only accepts this packet if the entity being attacked/used is visible
 * without obstruction and within a 4-unit radius of the player's position.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ClientPlayUseEntityPacket @JvmOverloads constructor(
    override var entityId: Int,
    var type: RegistryEntry,
    var targetX: Float = 0f,
    var targetY: Float = 0f,
    var targetZ: Float = 0f
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ClientPlayUseEntityPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ): ClientPlayUseEntityPacket {
            val target = if (version >= ProtocolVersion.MC1_8) buffer.readVarInt() else buffer.readInt()
            val type =
                MagicUseEntityType[version, if (version >= ProtocolVersion.MC1_8) buffer.readVarInt() else buffer.readByte()
                    .toInt()]
            return ClientPlayUseEntityPacket(
                target,
                type,
                buffer.takeIf { version >= ProtocolVersion.MC1_8 && type == MagicUseEntityType.INTERACT_AT }
                    ?.readFloat() ?: 0f,
                buffer.takeIf { version >= ProtocolVersion.MC1_8 && type == MagicUseEntityType.INTERACT_AT }
                    ?.readFloat() ?: 0f,
                buffer.takeIf { version >= ProtocolVersion.MC1_8 && type == MagicUseEntityType.INTERACT_AT }
                    ?.readFloat() ?: 0f
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayUseEntityPacket,
            connection: NettyConnection
        ) {
            if (version >= ProtocolVersion.MC1_8) buffer.writeVarInt(packet.entityId)
            else buffer.writeInt(packet.entityId)

            if (version >= ProtocolVersion.MC1_8)
                buffer.writeVarInt(MagicUseEntityType[version, packet.type, Int::class.java] ?: 0)
            else buffer.writeByte(MagicUseEntityType[version, packet.type, Int::class.java] ?: 0)

            if (version >= ProtocolVersion.MC1_8 && packet.type == MagicUseEntityType.INTERACT_AT) {
                buffer.writeFloat(packet.targetX)
                buffer.writeFloat(packet.targetY)
                buffer.writeFloat(packet.targetZ)
            }
        }
    }
}
