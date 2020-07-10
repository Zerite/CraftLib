package dev.zerite.craftlib.protocol.packet.play.client.other

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This message is sent from the client to the server when the "Done" button is pushed after placing a sign.
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */
data class ClientPlayUpdateSignPacket(
    var x: Int,
    var y: Int,
    var z: Int,
    var line1: String,
    var line2: String,
    var line3: String,
    var line4: String
) : Packet() {
    companion object : PacketIO<ClientPlayUpdateSignPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientPlayUpdateSignPacket(
            buffer.readInt(),
            buffer.readShort().toInt(),
            buffer.readInt(),
            buffer.readString(),
            buffer.readString(),
            buffer.readString(),
            buffer.readString()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayUpdateSignPacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.x)
            buffer.writeShort(packet.y)
            buffer.writeInt(packet.z)
            buffer.writeString(packet.line1)
            buffer.writeString(packet.line2)
            buffer.writeString(packet.line3)
            buffer.writeString(packet.line4)
        }
    }
}
