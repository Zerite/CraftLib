package dev.zerite.craftlib.protocol.packet.play.server.other

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * The server responds with a list of auto-completions of the last word sent to it.
 * In the case of regular chat, this is a player username.
 * Command names and parameters are also supported.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayTabCompletePacket(
    var matches: Array<String>
) : Packet() {
    companion object : PacketIO<ServerPlayTabCompletePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayTabCompletePacket(
            buffer.readArray { readString() }
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayTabCompletePacket,
            connection: NettyConnection
        ) {
            buffer.writeArray(packet.matches) { writeString(it) }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServerPlayTabCompletePacket

        if (!matches.contentEquals(other.matches)) return false

        return true
    }

    override fun hashCode(): Int {
        return matches.contentHashCode()
    }
}