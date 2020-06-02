package dev.zerite.mclib.protocol.packet.login.server

import dev.zerite.mclib.protocol.PacketIO
import dev.zerite.mclib.protocol.ProtocolBuffer
import dev.zerite.mclib.protocol.connection.NettyConnection
import dev.zerite.mclib.protocol.version.ProtocolVersion
import java.util.*

/**
 * Sent from the server to the client to indicate that a login was successful,
 * also indicating a protocol state change from {@code LOGIN} to {@code INGAME}.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerLoginSuccessPacket(var uuid: UUID, var username: String) {

    companion object : PacketIO<ServerLoginSuccessPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerLoginSuccessPacket(
            buffer.readUUID(mode = ProtocolBuffer.UUIDMode.DASHES),
            buffer.readString()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerLoginSuccessPacket,
            connection: NettyConnection
        ) {
            buffer.writeUUID(packet.uuid, mode = ProtocolBuffer.UUIDMode.DASHES)
            buffer.writeString(packet.username)
        }
    }

}