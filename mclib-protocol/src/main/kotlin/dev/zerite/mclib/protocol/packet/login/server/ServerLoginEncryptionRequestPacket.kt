package dev.zerite.mclib.protocol.packet.login.server

import dev.zerite.mclib.protocol.PacketIO
import dev.zerite.mclib.protocol.ProtocolBuffer
import dev.zerite.mclib.protocol.connection.NettyConnection
import dev.zerite.mclib.protocol.util.asPublicKey
import dev.zerite.mclib.protocol.version.ProtocolVersion
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
) {
    companion object : PacketIO<ServerLoginEncryptionRequestPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerLoginEncryptionRequestPacket(
            buffer.readString(),
            buffer.readByteArray { readShort().toInt() }.asPublicKey(),
            buffer.readByteArray { readShort().toInt() }
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerLoginEncryptionRequestPacket,
            connection: NettyConnection
        ) {
            buffer.writeString(packet.serverId)
            buffer.writeByteArray(packet.publicKey.encoded) { writeShort(it) }
            buffer.writeByteArray(packet.verifyToken) { writeShort(it) }
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