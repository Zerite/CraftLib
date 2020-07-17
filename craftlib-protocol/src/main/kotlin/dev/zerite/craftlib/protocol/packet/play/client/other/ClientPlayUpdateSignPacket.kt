package dev.zerite.craftlib.protocol.packet.play.client.other

import dev.zerite.craftlib.chat.component.BaseChatComponent
import dev.zerite.craftlib.chat.component.StringChatComponent
import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.Vector3
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This message is sent from the client to the server when the "Done" button is pushed after placing a sign.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ClientPlayUpdateSignPacket(
    var x: Int,
    var y: Int,
    var z: Int,
    var first: BaseChatComponent,
    var second: BaseChatComponent,
    var third: BaseChatComponent,
    var forth: BaseChatComponent
) : Packet() {
    companion object : PacketIO<ClientPlayUpdateSignPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = if (version >= ProtocolVersion.MC1_8) {
            val position = buffer.readPosition()
            ClientPlayUpdateSignPacket(
                position.x,
                position.y,
                position.z,
                buffer.readChat(),
                buffer.readChat(),
                buffer.readChat(),
                buffer.readChat()
            )
        } else {
            ClientPlayUpdateSignPacket(
                buffer.readInt(),
                buffer.readShort().toInt(),
                buffer.readInt(),
                StringChatComponent(buffer.readString()),
                StringChatComponent(buffer.readString()),
                StringChatComponent(buffer.readString()),
                StringChatComponent(buffer.readString())
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayUpdateSignPacket,
            connection: NettyConnection
        ) {
            if (version >= ProtocolVersion.MC1_8) {
                buffer.writePosition(Vector3(packet.x, packet.y, packet.z))
                buffer.writeChat(packet.first)
                buffer.writeChat(packet.second)
                buffer.writeChat(packet.third)
                buffer.writeChat(packet.forth)
            } else {
                buffer.writeInt(packet.x)
                buffer.writeShort(packet.y)
                buffer.writeInt(packet.z)
                buffer.writeString(packet.first.unformattedText)
                buffer.writeString(packet.second.unformattedText)
                buffer.writeString(packet.third.unformattedText)
                buffer.writeString(packet.forth.unformattedText)
            }
        }
    }
}
