package dev.zerite.craftlib.protocol.compat.forge.packet.server

import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.compat.forge.ForgePacket
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Starts the handshake.
 *
 * @author Koding
 * @since  0.1.2
 */
data class ServerForgeHandshakeHelloPacket(
    var version: Int,
    var dimension: Int
) : ForgePacket() {
    companion object : PacketIO<ServerForgeHandshakeHelloPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ): ServerForgeHandshakeHelloPacket {
            val protocolVersion = buffer.readByte().toInt()
            return ServerForgeHandshakeHelloPacket(
                protocolVersion,
                if (protocolVersion > 1) buffer.readInt() else 0
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerForgeHandshakeHelloPacket,
            connection: NettyConnection
        ) {
            buffer.writeByte(packet.version)
            if (packet.version >= 1) buffer.writeInt(packet.dimension)
        }
    }
}
