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
            buffer.readByteArray { if (version >= ProtocolVersion.MC1_8) readableBytes else readShort().toInt() }
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayPluginMessagePacket,
            connection: NettyConnection
        ) {
            buffer.writeString(packet.channel)
            buffer.writeByteArray(packet.data) { if (version <= ProtocolVersion.MC1_7_6) writeShort(it) }
        }

        /**
         * Creates a register packet given the protocol version and plugin channels.
         *
         * @param  version        The version to build the packet for.
         * @param  items          The plugin channels to include.
         *
         * @author Koding
         * @since  0.1.2
         */
        @JvmStatic
        @Suppress("UNUSED")
        fun register(version: ProtocolVersion, vararg items: String) = ClientPlayPluginMessagePacket(
            if (version >= ProtocolVersion.MC1_13) "minecraft:register" else "REGISTER",
            items.joinToString(separator = "\u0000").toByteArray()
        )

        /**
         * Creates a register packet given the protocol version and plugin channels.
         *
         * @param  version        The version to build the packet for.
         * @param  items          The plugin channels to include.
         *
         * @author Koding
         * @since  0.1.2
         */
        @JvmStatic
        @Suppress("UNUSED")
        fun unregister(version: ProtocolVersion, vararg items: String) = ClientPlayPluginMessagePacket(
            if (version >= ProtocolVersion.MC1_13) "minecraft:unregister" else "UNREGISTER",
            items.joinToString(separator = "\u0000").toByteArray()
        )
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
