package dev.zerite.craftlib.protocol.packet.play.client.inventory

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This packet is sent by the client when closing a window.
 *
 * Note, notchian clients send a close window message with window id 0 to close their inventory even though there is never an Open Window message for inventory.
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */
data class ClientPlayCloseWindowPacket(
    var windowId: Int
) : Packet() {
    companion object : PacketIO<ClientPlayCloseWindowPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientPlayCloseWindowPacket(
            buffer.readByte().toInt()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayCloseWindowPacket,
            connection: NettyConnection
        ) {
            buffer.writeByte(packet.windowId)
        }
    }
}
