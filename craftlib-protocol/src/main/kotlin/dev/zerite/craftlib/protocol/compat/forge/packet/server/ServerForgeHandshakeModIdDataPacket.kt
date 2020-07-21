package dev.zerite.craftlib.protocol.compat.forge.packet.server

import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.compat.forge.ForgePacket
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Contains numeric ID mapping for blocks and items.
 *
 * @author Koding
 * @since  0.1.2
 */
data class ServerForgeHandshakeModIdDataPacket(
    var mappings: Array<Mapping>,
    var blockSubstitutions: Array<String>,
    var itemSubstitutions: Array<String>
) : ForgePacket() {
    companion object : PacketIO<ServerForgeHandshakeModIdDataPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerForgeHandshakeModIdDataPacket(
            buffer.readArray { Mapping(readString(), readVarInt()) },
            buffer.readArray { readString() },
            buffer.readArray { readString() }
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerForgeHandshakeModIdDataPacket,
            connection: NettyConnection
        ) {
            buffer.writeArray(packet.mappings) {
                writeString(it.name)
                writeVarInt(it.id)
            }
            buffer.writeArray(packet.blockSubstitutions) { writeString(it) }
            buffer.writeArray(packet.itemSubstitutions) { writeString(it) }
        }
    }

    /**
     * Contains a single mapping that was parsed.
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

        other as ServerForgeHandshakeModIdDataPacket

        if (!mappings.contentEquals(other.mappings)) return false
        if (!blockSubstitutions.contentEquals(other.blockSubstitutions)) return false
        if (!itemSubstitutions.contentEquals(other.itemSubstitutions)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = mappings.contentHashCode()
        result = 31 * result + blockSubstitutions.contentHashCode()
        result = 31 * result + itemSubstitutions.contentHashCode()
        return result
    }
}
