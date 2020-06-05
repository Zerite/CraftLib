package dev.zerite.mclib.protocol.packet.play.server.other

import dev.zerite.mclib.protocol.PacketIO
import dev.zerite.mclib.protocol.ProtocolBuffer
import dev.zerite.mclib.protocol.connection.NettyConnection
import dev.zerite.mclib.protocol.version.ProtocolVersion

/**
 * Sent by the server to provide data to the client, usually regarding
 * a mod or debug info.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayPluginMessagePacket(var channel: String, var data: ByteArray) {

    companion object : PacketIO<ServerPlayPluginMessagePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayPluginMessagePacket(
            buffer.readString(),
            buffer.readByteArray { readShort().toInt() }
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayPluginMessagePacket,
            connection: NettyConnection
        ) {
            buffer.writeString(packet.channel)
            buffer.writeByteArray(packet.data) { writeShort(it) }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServerPlayPluginMessagePacket

        if (channel != other.channel) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = channel.hashCode()
        result = 31 * result + data.contentHashCode()
        return result
    }
}