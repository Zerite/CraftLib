package dev.zerite.craftlib.protocol.packet.play.client.display

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicClientAnimation
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the client to display an animation to other players.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ClientPlayAnimationPacket(
    override var entityId: Int,
    var animation: RegistryEntry
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ClientPlayAnimationPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientPlayAnimationPacket(
            if (version >= ProtocolVersion.MC1_8) 0 else buffer.readInt(),
            if (version >= ProtocolVersion.MC1_8) MagicClientAnimation.SWING_ARM else MagicClientAnimation[version, buffer.readByte().toInt()]
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayAnimationPacket,
            connection: NettyConnection
        ) {
            if (version <= ProtocolVersion.MC1_7_6) {
                buffer.writeInt(packet.entityId)
                buffer.writeByte(MagicClientAnimation[version, packet.animation, Int::class] ?: 0)
            }
        }
    }
}
