package dev.zerite.craftlib.protocol.compat.forge.packet.client

import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.compat.forge.ForgePacket
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Response from the client to the ServerHello packet.
 *
 * @author Koding
 * @since  0.1.2
 */
data class ClientForgeHandshakeHelloPacket(var version: Int) : ForgePacket() {
    companion object : PacketIO<ClientForgeHandshakeHelloPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientForgeHandshakeHelloPacket(buffer.readByte().toInt())

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientForgeHandshakeHelloPacket,
            connection: NettyConnection
        ) {
            buffer.writeByte(packet.version)
        }
    }
}
