package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server when an list of entities is to be destroyed on the client.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayDestroyEntitiesPacket(
    var entities: IntArray
) : Packet() {

    companion object : PacketIO<ServerPlayDestroyEntitiesPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayDestroyEntitiesPacket(
            buffer.readArray({
                if (version >= ProtocolVersion.MC1_8) readVarInt() else readByte().toInt()
            }) { if (version >= ProtocolVersion.MC1_8) readVarInt() else readInt() }.toIntArray()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayDestroyEntitiesPacket,
            connection: NettyConnection
        ) {
            buffer.writeArray(
                packet.entities.toTypedArray(),
                { if (version >= ProtocolVersion.MC1_8) writeVarInt(it) else writeByte(it) }
            ) { if (version >= ProtocolVersion.MC1_8) writeVarInt(it) else writeInt(it) }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServerPlayDestroyEntitiesPacket

        if (!entities.contentEquals(other.entities)) return false

        return true
    }

    override fun hashCode(): Int {
        return entities.contentHashCode()
    }
}
