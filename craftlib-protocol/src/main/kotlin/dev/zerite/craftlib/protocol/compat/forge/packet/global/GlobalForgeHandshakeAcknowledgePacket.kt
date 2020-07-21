package dev.zerite.craftlib.protocol.compat.forge.packet.global

import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.compat.forge.ForgePacket
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Confirms that the client is able to use the settings sent previously.
 *
 * @author Koding
 * @since  0.1.2
 */
@Suppress("UNUSED")
data class GlobalForgeHandshakeAcknowledgePacket(var phase: Int) : ForgePacket() {
    companion object : PacketIO<GlobalForgeHandshakeAcknowledgePacket> {

        const val WAITING_SERVER_DATA = 2
        const val WAITING_SERVER_COMPLETE = 3
        const val WAITING_ACK = 2
        const val PENDING_COMPLETE = 4
        const val COMPLETE_SERVER = 3
        const val COMPLETE_CLIENT = 5

        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = GlobalForgeHandshakeAcknowledgePacket(buffer.readByte().toInt())

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: GlobalForgeHandshakeAcknowledgePacket,
            connection: NettyConnection
        ) {
            buffer.writeByte(packet.phase)
        }
    }
}
