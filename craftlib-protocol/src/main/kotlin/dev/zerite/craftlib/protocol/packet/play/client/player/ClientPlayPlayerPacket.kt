package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This packet is used to indicate whether the player is on ground (walking/swimming), or airborne (jumping/falling).
 * When dropping from sufficient height, fall damage is applied when this state goes from False to True.
 * The amount of damage applied is based on the point where it last changed from True to False.
 * Note that there are several movement related packets containing this state.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ClientPlayPlayerPacket(var onGround: Boolean) : Packet() {
    companion object : PacketIO<ClientPlayPlayerPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientPlayPlayerPacket(buffer.readBoolean())

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayPlayerPacket,
            connection: NettyConnection
        ) {
            buffer.writeBoolean(packet.onGround)
        }
    }
}
