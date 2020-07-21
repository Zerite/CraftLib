package dev.zerite.craftlib.protocol.packet.play.client.other

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.Vector3
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent when the user presses \[tab] while writing text. The payload contains all text behind the cursor.
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */
data class ClientPlayTabCompletePacket @JvmOverloads constructor(
    var text: String,
    var lookBlock: Vector3? = null
) : Packet() {
    companion object : PacketIO<ClientPlayTabCompletePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientPlayTabCompletePacket(
            buffer.readString(),
            if (version >= ProtocolVersion.MC1_8) buffer.takeIf { it.readBoolean() }?.readPosition() else Vector3(
                0,
                0,
                0
            )
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayTabCompletePacket,
            connection: NettyConnection
        ) {
            buffer.writeString(packet.text)
            if (version >= ProtocolVersion.MC1_8) {
                buffer.writeBoolean(packet.lookBlock != null)
                if (packet.lookBlock != null) buffer.writePosition(packet.lookBlock!!)
            }
        }
    }
}
