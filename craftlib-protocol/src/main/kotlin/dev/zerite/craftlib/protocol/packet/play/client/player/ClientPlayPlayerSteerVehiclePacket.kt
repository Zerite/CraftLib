package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Steer Vehicle
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */
data class ClientPlayPlayerSteerVehiclePacket(
    var sideways: Float,
    var forward: Float,
    var jump: Boolean,
    var unmount: Boolean
) : Packet() {
    companion object : PacketIO<ClientPlayPlayerSteerVehiclePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientPlayPlayerSteerVehiclePacket(
            buffer.readFloat(),
            buffer.readFloat(),
            buffer.readBoolean(),
            buffer.readBoolean()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayPlayerSteerVehiclePacket,
            connection: NettyConnection
        ) {
            buffer.writeFloat(packet.sideways)
            buffer.writeFloat(packet.forward)
            buffer.writeBoolean(packet.jump)
            buffer.writeBoolean(packet.unmount)
        }
    }
}
