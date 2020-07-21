package dev.zerite.craftlib.protocol.compat.forge.packet.server

import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.compat.forge.ForgePacket
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Causes the client to recomplete the entire handshake from the start.
 * There is no payload beyond the discriminator byte.
 *
 * The normal forge server does not ever use this packet, but it is used
 * when connecting through a BungeeCord instance, specifically when transitioning
 * from a vanilla server to a modded one or from a modded server to another modded server.
 *
 * @author Koding
 * @since  0.1.2
 */
class ServerForgeHandshakeResetPacket : ForgePacket() {
    companion object : PacketIO<ServerForgeHandshakeResetPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerForgeHandshakeResetPacket()

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerForgeHandshakeResetPacket,
            connection: NettyConnection
        ) {
        }
    }

    override fun toString() = "ServerForgeHandshakeResetPacket()"
}
