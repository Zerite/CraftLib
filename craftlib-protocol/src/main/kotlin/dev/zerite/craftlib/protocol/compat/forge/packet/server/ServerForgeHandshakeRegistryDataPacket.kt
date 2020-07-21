package dev.zerite.craftlib.protocol.compat.forge.packet.server

import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.compat.forge.ForgePacket
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * The server sends several of this packet, one for each registry.
 * It'll keep sending them until the hasMore value is no longer true.
 *
 * @author Koding
 * @since  0.1.2
 */
data class ServerForgeHandshakeRegistryDataPacket(
    var hasMore: Boolean,
    var name: String,
    var mappings: Array<Mapping>,
    var substitutions: Array<String>,
    var dummies: Array<String>
) : ForgePacket() {
    companion object : PacketIO<ServerForgeHandshakeRegistryDataPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerForgeHandshakeRegistryDataPacket(
            buffer.readBoolean(),
            buffer.readString(),
            buffer.readArray { Mapping(readString(), readVarInt()) },
            buffer.readArray { readString() },
            buffer.readArray { readString() }
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerForgeHandshakeRegistryDataPacket,
            connection: NettyConnection
        ) {
            buffer.writeBoolean(packet.hasMore)
            buffer.writeString(packet.name)
            buffer.writeArray(packet.mappings) {
                writeString(it.name)
                writeVarInt(it.id)
            }
            buffer.writeArray(packet.substitutions) { writeString(it) }
            buffer.writeArray(packet.dummies) { writeString(it) }
        }
    }

    /**
     * Instance of a mapping which was read.
     *
     * @author Koding
     * @since  0.1.2
     */
    data class Mapping(
        var name: String,
        var id: Int
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServerForgeHandshakeRegistryDataPacket

        if (hasMore != other.hasMore) return false
        if (name != other.name) return false
        if (!mappings.contentEquals(other.mappings)) return false
        if (!substitutions.contentEquals(other.substitutions)) return false
        if (!dummies.contentEquals(other.dummies)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = hasMore.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + mappings.contentHashCode()
        result = 31 * result + substitutions.contentHashCode()
        result = 31 * result + dummies.contentHashCode()
        return result
    }
}
