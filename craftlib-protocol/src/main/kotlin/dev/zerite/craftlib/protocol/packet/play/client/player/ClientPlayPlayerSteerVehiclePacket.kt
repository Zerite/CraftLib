package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.util.delegate.bitBoolean
import dev.zerite.craftlib.protocol.util.ext.setBit
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
    var flags: Int
) : Packet() {

    /**
     * Controls the flags variable using the raw bit values.
     */
    var jump by bitBoolean(this::flags, 0x1)
    var unmount by bitBoolean(this::flags, 0x2)

    companion object : PacketIO<ClientPlayPlayerSteerVehiclePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ): ClientPlayPlayerSteerVehiclePacket {
            val sideways = buffer.readFloat()
            val forward = buffer.readFloat()

            return if (version >= ProtocolVersion.MC1_8) {
                ClientPlayPlayerSteerVehiclePacket(sideways, forward, buffer.readUnsignedByte().toInt())
            } else {
                var flags = 0
                if (buffer.readBoolean()) flags = flags.setBit(0x1)
                if (buffer.readBoolean()) flags = flags.setBit(0x2)
                ClientPlayPlayerSteerVehiclePacket(
                    sideways,
                    forward,
                    flags
                )
            }
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayPlayerSteerVehiclePacket,
            connection: NettyConnection
        ) {
            buffer.writeFloat(packet.sideways)
            buffer.writeFloat(packet.forward)
            if (version >= ProtocolVersion.MC1_8) buffer.writeByte(packet.flags)
            else {
                buffer.writeBoolean(packet.jump)
                buffer.writeBoolean(packet.unmount)
            }
        }
    }
}
