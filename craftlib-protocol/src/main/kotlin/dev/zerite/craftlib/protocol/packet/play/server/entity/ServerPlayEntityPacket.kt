package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Most entity-related packets are subclasses of this packet. When sent from the server to the client,
 * it may initialize the entry.
 *
 * For player entities, either this packet or any move/look packet is sent every game tick.
 * So the meaning of this packet is basically that the entity did not move/look since the last such packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayEntityPacket(
    override var entityId: Int
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ServerPlayEntityPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayEntityPacket(buffer.readInt())

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayEntityPacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.entityId)
        }
    }
}
