package dev.zerite.craftlib.protocol.packet.login.server

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.util.asPublicKey
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import java.security.PublicKey

/**
 * Sent from the server to request that the client begins using encryption
 * through a public and private key exchange.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerLoginEncryptionRequestPacket(
    var serverId: String,
    var publicKey: PublicKey,
    var verifyToken: ByteArray
) : Packet() {
    companion object : PacketIO<ServerLoginEncryptionRequestPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerLoginEncryptionRequestPacket(
            buffer.readString(),
            buffer.readByteArray { if (version >= ProtocolVersion.MC1_8) readVarInt() else readShort().toInt() }.asPublicKey(),
            buffer.readByteArray { if (version >= ProtocolVersion.MC1_8) readVarInt() else readShort().toInt() }
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerLoginEncryptionRequestPacket,
            connection: NettyConnection
        ) {
            buffer.writeString(packet.serverId)
            buffer.writeByteArray(packet.publicKey.encoded) { if (version >= ProtocolVersion.MC1_8) writeVarInt(it) else writeShort(it) }
            buffer.writeByteArray(packet.verifyToken) { if (version >= ProtocolVersion.MC1_8) writeVarInt(it) else writeShort(it) }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServerLoginEncryptionRequestPacket

        if (serverId != other.serverId) return false
        if (publicKey != other.publicKey) return false
        if (!verifyToken.contentEquals(other.verifyToken)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = serverId.hashCode()
        result = 31 * result + publicKey.hashCode()
        result = 31 * result + verifyToken.contentHashCode()
        return result
    }

}
