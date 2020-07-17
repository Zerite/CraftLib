package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.nbt.impl.CompoundTag
import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Updates NBT data about an entity on the client.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
data class ServerPlayUpdateEntityNBTPacket(
    override var entityId: Int,
    var tag: CompoundTag
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ServerPlayUpdateEntityNBTPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayUpdateEntityNBTPacket(
            buffer.readVarInt(),
            buffer.readNBT(compressed = true)?.tag ?: CompoundTag()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayUpdateEntityNBTPacket,
            connection: NettyConnection
        ) {
            buffer.writeVarInt(packet.entityId)
            buffer.writeNBT(packet.tag, compressed = true)
        }
    }
}
