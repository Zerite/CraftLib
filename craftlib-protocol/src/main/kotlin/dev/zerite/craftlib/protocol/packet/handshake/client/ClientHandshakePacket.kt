package dev.zerite.craftlib.protocol.packet.handshake.client

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.compat.forge.forge
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.MinecraftProtocol
import dev.zerite.craftlib.protocol.version.ProtocolState
import dev.zerite.craftlib.protocol.version.ProtocolVersion

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
) : Packet() {

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
            buffer.readVarInt().let { MinecraftProtocol[it] ?: error("Unknown connection state $it") }
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientHandshakePacket,
            connection: NettyConnection
        ) {
            buffer.writeVarInt(packet.version.id)
            buffer.writeString(packet.address.let {
                if (connection.forge) "$it\u0000FML\u0000" else it
            })
            buffer.writeShort(packet.port)
            buffer.writeVarInt((packet.nextState.id as? Int) ?: return)
        }
    }
}
