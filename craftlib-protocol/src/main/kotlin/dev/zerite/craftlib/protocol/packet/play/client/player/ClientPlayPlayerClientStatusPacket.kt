package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

data class ClientPlayPlayerClientStatusPacket(
        var actionId: Int
) : Packet() {
    companion object : PacketIO<ClientPlayPlayerClientStatusPacket> {
        override fun read(
                buffer: ProtocolBuffer,
                version: ProtocolVersion,
                connection: NettyConnection
        ) = ClientPlayPlayerClientStatusPacket(
                buffer.readByte().toInt()
        )

        override fun write(
                buffer: ProtocolBuffer,
                version: ProtocolVersion,
                packet: ClientPlayPlayerClientStatusPacket,
                connection: NettyConnection
        ) {
            buffer.writeByte(packet.actionId)
        }
    }
}