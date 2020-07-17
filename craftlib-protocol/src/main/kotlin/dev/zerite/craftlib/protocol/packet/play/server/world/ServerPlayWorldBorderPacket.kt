package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicWorldBorderAction
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to update the world border visually on the client.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
data class ServerPlayWorldBorderPacket(
    var action: RegistryEntry,
    var x: Double = 0.0,
    var z: Double = 0.0,
    var oldRadius: Double? = null,
    var newRadius: Double? = null,
    var speed: Long? = null,
    var portalTeleportBoundary: Int? = null,
    var warningTime: Int? = null,
    var warningBlocks: Int? = null
) : Packet() {
    companion object : PacketIO<ServerPlayWorldBorderPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ): ServerPlayWorldBorderPacket {
            val action = MagicWorldBorderAction[version, buffer.readVarInt()]
            return ServerPlayWorldBorderPacket(
                action,
                x = buffer.takeIf { action == MagicWorldBorderAction.INITIALIZE || action == MagicWorldBorderAction.SET_CENTER }
                    ?.readDouble() ?: 0.0,
                z = buffer.takeIf { action == MagicWorldBorderAction.INITIALIZE || action == MagicWorldBorderAction.SET_CENTER }
                    ?.readDouble() ?: 0.0,
                oldRadius = buffer.takeIf { action == MagicWorldBorderAction.INITIALIZE || action == MagicWorldBorderAction.LERP_SIZE }
                    ?.readDouble(),
                newRadius = buffer.takeIf { action == MagicWorldBorderAction.INITIALIZE || action == MagicWorldBorderAction.LERP_SIZE || action == MagicWorldBorderAction.SET_SIZE }
                    ?.readDouble(),
                speed = buffer.takeIf { action == MagicWorldBorderAction.INITIALIZE || action == MagicWorldBorderAction.LERP_SIZE }
                    ?.readVarLong(),
                portalTeleportBoundary = buffer.takeIf { action == MagicWorldBorderAction.INITIALIZE }
                    ?.readVarInt(),
                warningTime = buffer.takeIf { action == MagicWorldBorderAction.INITIALIZE || action == MagicWorldBorderAction.SET_WARNING_TIME }
                    ?.readVarInt(),
                warningBlocks = buffer.takeIf { action == MagicWorldBorderAction.INITIALIZE || action == MagicWorldBorderAction.SET_WARNING_BLOCKS }
                    ?.readVarInt()
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayWorldBorderPacket,
            connection: NettyConnection
        ) {
            buffer.writeVarInt(MagicWorldBorderAction[version, packet.action, Int::class] ?: 0)

            if (packet.action == MagicWorldBorderAction.INITIALIZE || packet.action == MagicWorldBorderAction.SET_CENTER) {
                buffer.writeDouble(packet.x)
                buffer.writeDouble(packet.z)
            }

            if (packet.action == MagicWorldBorderAction.INITIALIZE || packet.action == MagicWorldBorderAction.LERP_SIZE)
                buffer.writeDouble(packet.oldRadius ?: 0.0)
            if (packet.action == MagicWorldBorderAction.INITIALIZE || packet.action == MagicWorldBorderAction.LERP_SIZE || packet.action == MagicWorldBorderAction.SET_SIZE)
                buffer.writeDouble(packet.newRadius ?: 0.0)
            if (packet.action == MagicWorldBorderAction.INITIALIZE || packet.action == MagicWorldBorderAction.LERP_SIZE)
                buffer.writeVarLong(packet.speed ?: 0L)

            if (packet.action == MagicWorldBorderAction.INITIALIZE)
                buffer.writeVarInt(packet.portalTeleportBoundary ?: 0)

            if (packet.action == MagicWorldBorderAction.INITIALIZE || packet.action == MagicWorldBorderAction.SET_WARNING_TIME)
                buffer.writeVarInt(packet.warningTime ?: 0)
            if (packet.action == MagicWorldBorderAction.INITIALIZE || packet.action == MagicWorldBorderAction.SET_WARNING_BLOCKS)
                buffer.writeVarInt(packet.warningBlocks ?: 0)
        }
    }
}
