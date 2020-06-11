package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.entity.EntityMetadata
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to tell the client certain metadata values about
 * an entity.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayEntityMetadataPacket(
    override var entityId: Int,
    var metadata: EntityMetadata
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ServerPlayEntityMetadataPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayEntityMetadataPacket(
            buffer.readInt(),
            buffer.readMetadata()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayEntityMetadataPacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.entityId)
            buffer.writeMetadata(packet.metadata)
        }
    }
}