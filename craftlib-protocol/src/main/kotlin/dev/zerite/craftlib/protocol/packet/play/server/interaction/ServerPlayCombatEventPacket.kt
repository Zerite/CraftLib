package dev.zerite.craftlib.protocol.packet.play.server.interaction

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicCombatEvent
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent to the client to update their combat status.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
data class ServerPlayCombatEventPacket(
    var event: RegistryEntry,
    var duration: Int? = null,
    var entityId: Int? = null,
    var playerId: Int? = null,
    var message: String? = null
) : Packet() {
    companion object : PacketIO<ServerPlayCombatEventPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ): ServerPlayCombatEventPacket {
            val event = MagicCombatEvent[version, buffer.readVarInt()]
            val duration = buffer.takeIf { event == MagicCombatEvent.END_COMBAT }?.readVarInt()
            val playerId = buffer.takeIf { event == MagicCombatEvent.ENTITY_DEAD }?.readVarInt()
            val entityId = buffer.takeIf { event == MagicCombatEvent.END_COMBAT || event == MagicCombatEvent.ENTITY_DEAD }?.readInt()
            return ServerPlayCombatEventPacket(
                event,
                duration = duration,
                playerId = playerId,
                entityId = entityId,
                message = buffer.takeIf { event == MagicCombatEvent.ENTITY_DEAD }?.readString()
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayCombatEventPacket,
            connection: NettyConnection
        ) {
            buffer.writeVarInt(MagicCombatEvent[version, packet.event, Int::class] ?: 0)
            if (packet.event == MagicCombatEvent.END_COMBAT) buffer.writeVarInt(packet.duration ?: 0)
            if (packet.event == MagicCombatEvent.ENTITY_DEAD) buffer.writeVarInt(packet.playerId ?: 0)
            if (packet.event == MagicCombatEvent.ENTITY_DEAD || packet.event == MagicCombatEvent.END_COMBAT) buffer.writeInt(packet.entityId ?: 0)
            if (packet.event == MagicCombatEvent.ENTITY_DEAD) buffer.writeString(packet.message ?: "")
        }
    }
}
