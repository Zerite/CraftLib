package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.PotionEffect
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to tell the client that a potion effect has been
 * applied to an entity.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayEntityEffectPacket(
    override var entityId: Int,
    var effect: RegistryEntry,
    var amplifier: Int,
    var duration: Int
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ServerPlayEntityEffectPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayEntityEffectPacket(
            buffer.readInt(),
            PotionEffect[version, buffer.readByte().toInt()],
            buffer.readByte().toInt(),
            buffer.readShort().toInt()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayEntityEffectPacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.entityId)
            buffer.writeByte(PotionEffect[version, packet.effect, Int::class] ?: 0)
            buffer.writeByte(packet.amplifier)
            buffer.writeShort(packet.duration)
        }
    }
}