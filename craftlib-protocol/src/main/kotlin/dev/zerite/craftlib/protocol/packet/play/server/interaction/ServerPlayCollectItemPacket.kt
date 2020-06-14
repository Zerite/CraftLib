package dev.zerite.craftlib.protocol.packet.play.server.interaction

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server when someone picks up an item lying on the ground - its sole purpose appears
 * to be the animation of the item flying towards you. It doesn't destroy the entity in the client
 * memory, and it doesn't add it to your inventory.
 *
 * The server only checks for items to be picked up after each Player Position and
 * Player Position & Look packet sent by the client.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayCollectItemPacket(
    var itemId: Int,
    override var entityId: Int
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ServerPlayCollectItemPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayCollectItemPacket(
            buffer.readInt(),
            buffer.readInt()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayCollectItemPacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.itemId)
            buffer.writeInt(packet.entityId)
        }
    }
}