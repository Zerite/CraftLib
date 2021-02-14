package dev.zerite.craftlib.protocol.packet.play.server.interaction

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Applies a cooldown period to an item type.
 *
 * @author Koding
 * @since  0.2.0
 */
data class ServerPlaySetCooldownPacket constructor(
    var itemId: Int,
    var cooldownTicks: Int
) : Packet() {
    companion object : PacketIO<ServerPlaySetCooldownPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ): ServerPlaySetCooldownPacket = ServerPlaySetCooldownPacket(
            buffer.readVarInt(),
            buffer.readVarInt()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlaySetCooldownPacket,
            connection: NettyConnection
        ) {
            buffer.writeVarInt(packet.itemId)
            buffer.writeVarInt(packet.cooldownTicks)
        }
    }
}
