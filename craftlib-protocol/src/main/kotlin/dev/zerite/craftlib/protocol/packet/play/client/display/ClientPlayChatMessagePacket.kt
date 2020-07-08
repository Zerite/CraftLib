package dev.zerite.craftlib.protocol.packet.play.client.display

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * The default server will check the message to see if it begins with a '/'.
 * If it doesn't, the username of the sender is prepended and sent to all other clients
 * (including the original sender). If it does, the server assumes it to be a command and attempts to process it.
 *
 * A message longer than 100 characters will cause the server to kick the client.
 * This change was initially done by allowing the client to not slice the message up to 119 (the previous limit),
 * without changes to the server. For this reason, the vanilla server kept the code to cut messages at 119, but
 * this isn't a protocol limitation and can be ignored.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ClientPlayChatMessagePacket(var message: String) : Packet() {
    companion object : PacketIO<ClientPlayChatMessagePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientPlayChatMessagePacket(buffer.readString())

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayChatMessagePacket,
            connection: NettyConnection
        ) {
            buffer.writeString(packet.message)
        }
    }
}
