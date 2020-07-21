package dev.zerite.craftlib.protocol.compat.forge.packet.global

import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.compat.forge.ForgePacket
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Contains a list of all mods installed on the server or client.
 * Sent from the client to the server first, and then the server responds with its mod list.
 * The server's mod list matches the one sent in the ping.
 *
 * @author Koding
 * @since  0.1.2
 */
data class GlobalForgeHandshakeModListPacket(var mods: Array<Mod>) : ForgePacket() {
    companion object : PacketIO<GlobalForgeHandshakeModListPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = GlobalForgeHandshakeModListPacket(
            buffer.readArray { Mod(readString(), readString()) }
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: GlobalForgeHandshakeModListPacket,
            connection: NettyConnection
        ) {
            buffer.writeArray(packet.mods) {
                writeString(it.name)
                writeString(it.version)
            }
        }
    }

    /**
     * Stores data about a single mod in this mod list.
     *
     * @author Koding
     * @since  0.1.2
     */
    data class Mod(
        var name: String,
        var version: String
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GlobalForgeHandshakeModListPacket

        if (!mods.contentEquals(other.mods)) return false

        return true
    }

    override fun hashCode(): Int {
        return mods.contentHashCode()
    }
}
