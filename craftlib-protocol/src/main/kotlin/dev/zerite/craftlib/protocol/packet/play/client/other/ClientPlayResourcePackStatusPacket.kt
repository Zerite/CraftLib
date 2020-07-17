package dev.zerite.craftlib.protocol.packet.play.client.other

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicResourcePackResult
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the client to tell the server the status of loading the
 * resource pack.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
data class ClientPlayResourcePackStatusPacket(
    var hash: String,
    var result: RegistryEntry
) : Packet() {
    companion object : PacketIO<ClientPlayResourcePackStatusPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientPlayResourcePackStatusPacket(
            buffer.readString(),
            MagicResourcePackResult[version, buffer.readVarInt()]
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayResourcePackStatusPacket,
            connection: NettyConnection
        ) {
            buffer.writeString(packet.hash)
            buffer.writeVarInt(MagicResourcePackResult[version, packet.result, Int::class] ?: 0)
        }
    }
}
