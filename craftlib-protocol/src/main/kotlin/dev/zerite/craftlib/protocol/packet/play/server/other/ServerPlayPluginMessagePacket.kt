package dev.zerite.craftlib.protocol.packet.play.server.other

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to provide data to the client, usually regarding
 * a mod or debug info.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayPluginMessagePacket(var channel: String, var data: ByteArray) : Packet() {

    companion object : PacketIO<ServerPlayPluginMessagePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayPluginMessagePacket(
            buffer.readString(),
            buffer.readByteArray { if (version >= ProtocolVersion.MC1_8) readableBytes else readShort().toInt() }
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayPluginMessagePacket,
            connection: NettyConnection
        ) {
            buffer.writeString(packet.channel)
            buffer.writeByteArray(packet.data) { if (version <= ProtocolVersion.MC1_7_6) writeShort(it) }
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
