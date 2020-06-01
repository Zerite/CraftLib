package dev.zerite.mclib.protocol.packet.handshake.client

import dev.zerite.mclib.protocol.PacketIO
import dev.zerite.mclib.protocol.ProtocolBuffer
import dev.zerite.mclib.protocol.connection.NettyConnection
import dev.zerite.mclib.protocol.version.MinecraftProtocol
import dev.zerite.mclib.protocol.version.ProtocolState
import dev.zerite.mclib.protocol.version.ProtocolVersion

/**
 * The very first packet ever sent in all connections, which indicates
 * the next state to proceed with.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ClientHandshakePacket(
    var version: ProtocolVersion,
    var address: String,
    var port: Int,
    var nextState: ProtocolState
) {

    init {
        if (nextState.id.let { it != 1 && it != 2 }) error("Invalid protocol state")
    }

    companion object : PacketIO<ClientHandshakePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientHandshakePacket(
            ProtocolVersion[buffer.readVarInt()],
            buffer.readString(),
            buffer.readUnsignedShort(),
            MinecraftProtocol[buffer.readVarInt()]
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientHandshakePacket,
            connection: NettyConnection
        ) {
            buffer.writeVarInt(packet.version.id)
            buffer.writeString(packet.address)
            buffer.writeShort(packet.port)
            buffer.writeVarInt(packet.nextState.id)
        }
    }
}