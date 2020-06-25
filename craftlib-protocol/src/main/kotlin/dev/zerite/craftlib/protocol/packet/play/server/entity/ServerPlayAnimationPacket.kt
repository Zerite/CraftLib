package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.Animation
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent whenever an entity should change animation.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayAnimationPacket(
    override var entityId: Int,
    var animation: RegistryEntry
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ServerPlayAnimationPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayAnimationPacket(
            buffer.readVarInt(),
            Animation[version, buffer.readUnsignedByte().toInt()]
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayAnimationPacket,
            connection: NettyConnection
        ) {
            buffer.writeVarInt(packet.entityId)
            buffer.writeByte(Animation[version, packet.animation, Int::class] ?: 0)
        }
    }
}