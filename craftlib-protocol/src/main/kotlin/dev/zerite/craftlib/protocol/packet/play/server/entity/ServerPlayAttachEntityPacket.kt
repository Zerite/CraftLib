package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This packet is sent when a player has been attached to an entity (e.g. Minecart)
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayAttachEntityPacket(
    override var entityId: Int,
    var vehicleId: Int,
    var leash: Boolean
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ServerPlayAttachEntityPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayAttachEntityPacket(
            buffer.readInt(),
            buffer.readInt(),
            buffer.readBoolean()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayAttachEntityPacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.entityId)
            buffer.writeInt(packet.vehicleId)
            buffer.writeBoolean(packet.leash)
        }
    }
}