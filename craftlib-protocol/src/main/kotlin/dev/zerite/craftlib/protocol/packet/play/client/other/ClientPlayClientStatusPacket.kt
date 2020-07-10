package dev.zerite.craftlib.protocol.packet.play.client.other

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.ClientStatus
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent when the client is ready to complete login and when the client is ready to respawn after death.
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */
data class ClientPlayClientStatusPacket(
    var actionId: RegistryEntry
) : Packet() {
    companion object : PacketIO<ClientPlayClientStatusPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientPlayClientStatusPacket(
            ClientStatus[version, buffer.readByte().toInt()]
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayClientStatusPacket,
            connection: NettyConnection
        ) {
            buffer.writeByte(ClientStatus[version, packet.actionId, Int::class] ?: 0)
        }
    }
}
