package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Velocity is believed to be in units of 1/8000 of a block per server tick (50ms);
 * for example, -1343 would move (-1343 / 8000) = −0.167875 blocks per tick
 * (or −3,3575 blocks per second).
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayEntityVelocityPacket(
    override var entityId: Int,
    var x: Double,
    var y: Double,
    var z: Double
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ServerPlayEntityVelocityPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayEntityVelocityPacket(
            buffer.readInt(),
            buffer.readVelocity(),
            buffer.readVelocity(),
            buffer.readVelocity()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayEntityVelocityPacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.entityId)
            buffer.writeVelocity(packet.x)
            buffer.writeVelocity(packet.y)
            buffer.writeVelocity(packet.z)
        }
    }
}
