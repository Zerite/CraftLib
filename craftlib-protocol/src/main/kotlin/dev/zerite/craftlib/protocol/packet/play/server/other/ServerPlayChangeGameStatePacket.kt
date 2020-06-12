package dev.zerite.craftlib.protocol.packet.play.server.other

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.GameStateReason
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to update the game state of the client, including
 * showing rain or running the credits.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayChangeGameStatePacket(
    var reason: RegistryEntry,
    var value: Float
) : Packet() {
    companion object : PacketIO<ServerPlayChangeGameStatePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayChangeGameStatePacket(
            GameStateReason[version, buffer.readUnsignedByte().toInt()],
            buffer.readFloat()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayChangeGameStatePacket,
            connection: NettyConnection
        ) {
            buffer.writeByte(GameStateReason[version, packet.reason, Int::class] ?: 0)
            buffer.writeFloat(packet.value)
        }
    }
}