package dev.zerite.craftlib.protocol.packet.play.client.other

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Mods and plugins can use this to send their data.
 * Minecraft itself uses a number of plugin channels.
 * These internal channels are prefixed with MC|.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ClientPlayPluginMessagePacket(
    var channel: String,
    var data: ByteArray
) : Packet() {
    companion object : PacketIO<ClientPlayPluginMessagePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientPlayPluginMessagePacket(
            buffer.readString(),
            buffer.readByteArray { readShort().toInt() }
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayPluginMessagePacket,
            connection: NettyConnection
        ) {
            buffer.writeString(packet.channel)
            buffer.writeByteArray(packet.data) { writeShort(it) }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ClientPlayPluginMessagePacket

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
