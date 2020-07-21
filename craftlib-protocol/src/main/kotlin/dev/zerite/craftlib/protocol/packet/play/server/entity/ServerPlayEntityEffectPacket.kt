package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicPotionEffect
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to tell the client that a potion effect has been
 * applied to an entity.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayEntityEffectPacket @JvmOverloads constructor(
    override var entityId: Int,
    var effect: RegistryEntry,
    var amplifier: Int,
    var duration: Int,
    var hideParticles: Boolean = false
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ServerPlayEntityEffectPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayEntityEffectPacket(
            if (version >= ProtocolVersion.MC1_8) buffer.readVarInt() else buffer.readInt(),
            MagicPotionEffect[version, buffer.readByte().toInt()],
            buffer.readByte().toInt(),
            if (version >= ProtocolVersion.MC1_8) buffer.readVarInt() else buffer.readShort().toInt(),
            if (version >= ProtocolVersion.MC1_8) buffer.readBoolean() else false
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayEntityEffectPacket,
            connection: NettyConnection
        ) {
            if (version >= ProtocolVersion.MC1_8) buffer.writeVarInt(packet.entityId)
            else buffer.writeInt(packet.entityId)
            buffer.writeByte(MagicPotionEffect[version, packet.effect, Int::class.java] ?: 0)
            buffer.writeByte(packet.amplifier)
            if (version >= ProtocolVersion.MC1_8) buffer.writeVarInt(packet.duration)
            else buffer.writeShort(packet.duration)
            if (version >= ProtocolVersion.MC1_8)
                buffer.writeBoolean(packet.hideParticles)
        }
    }
}
