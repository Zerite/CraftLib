package dev.zerite.craftlib.protocol.packet.play.server.other

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicDifficulty
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Changes the difficulty setting in the client's option menu.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
data class ServerPlayServerDifficultyPacket(var difficulty: RegistryEntry) : Packet() {
    companion object : PacketIO<ServerPlayServerDifficultyPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayServerDifficultyPacket(MagicDifficulty[version, buffer.readUnsignedByte().toInt()])

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayServerDifficultyPacket,
            connection: NettyConnection
        ) {
            buffer.writeByte(MagicDifficulty[version, packet.difficulty, Int::class.java] ?: 0)
        }
    }
}
