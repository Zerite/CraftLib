package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Updates the players XYZ position on the server. If Stance - Y is less than 0.1 or greater than 1.65,
 * the stance is illegal and the client will be kicked with the message “Illegal Stance”.
 *
 * If the distance between the last known position of the player on the server and the new position set by
 * this packet is greater than 100 units will result in the client being kicked for
 * "You moved too quickly :( (Hacking?)"
 *
 * Also if the fixed-point number of X or Z is set greater than 3.2E7D the client
 * will be kicked for "Illegal position"
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ClientPlayPlayerPositionPacket(
    var x: Double,
    var y: Double,
    var stance: Double,
    var z: Double,
    var onGround: Boolean
) : Packet() {
    companion object : PacketIO<ClientPlayPlayerPositionPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ): ClientPlayPlayerPositionPacket {
            val x = buffer.readDouble()
            val y = buffer.readDouble()
            return ClientPlayPlayerPositionPacket(
                x,
                y,
                if (version >= ProtocolVersion.MC1_8) y + 1.5 else buffer.readDouble(),
                buffer.readDouble(),
                buffer.readBoolean()
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayPlayerPositionPacket,
            connection: NettyConnection
        ) {
            buffer.writeDouble(packet.x)
            buffer.writeDouble(packet.y)
            if (version <= ProtocolVersion.MC1_7_6)
                buffer.writeDouble(packet.stance)
            buffer.writeDouble(packet.z)
            buffer.writeBoolean(packet.onGround)
        }
    }
}
