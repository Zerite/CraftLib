package dev.zerite.craftlib.protocol.packet.login.server

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import java.util.*

/**
 * Sent from the server to the client to indicate that a login was successful,
 * also indicating a protocol state change from {@code LOGIN} to {@code PLAY}.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerLoginSuccessPacket(var uuid: UUID, var username: String) : Packet() {

    companion object : PacketIO<ServerLoginSuccessPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerLoginSuccessPacket(
            buffer.readUUID(mode = ProtocolBuffer.UUIDMode.DASHES.takeIf { version < ProtocolVersion.MC1_7_6 } ?: ProtocolBuffer.UUIDMode.STRING),
            buffer.readString()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerLoginSuccessPacket,
            connection: NettyConnection
        ) {
            buffer.writeUUID(packet.uuid, mode = ProtocolBuffer.UUIDMode.DASHES.takeIf { version < ProtocolVersion.MC1_7_6 } ?: ProtocolBuffer.UUIDMode.STRING)
            buffer.writeString(packet.username)
        }
    }

}
