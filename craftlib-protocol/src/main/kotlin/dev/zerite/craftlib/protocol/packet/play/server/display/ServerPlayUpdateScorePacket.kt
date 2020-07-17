package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This is sent to the client when it should update a scoreboard item.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayUpdateScorePacket(
    var name: String,
    var update: Boolean,
    var score: String? = null,
    var value: Int? = null
) : Packet() {
    companion object : PacketIO<ServerPlayUpdateScorePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ): ServerPlayUpdateScorePacket {
            val name = buffer.readString()
            val update = buffer.readByte() == 0.toByte()

            return ServerPlayUpdateScorePacket(
                name,
                update,
                buffer.takeIf { if (version >= ProtocolVersion.MC1_8) true else update }
                    ?.readString(),
                buffer.takeIf { update }
                    ?.let { if (version >= ProtocolVersion.MC1_8) it.readVarInt() else it.readInt() }
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayUpdateScorePacket,
            connection: NettyConnection
        ) {
            buffer.writeString(packet.name)
            buffer.writeByte(if (packet.update) 0 else 1)

            if (version >= ProtocolVersion.MC1_8) buffer.writeString(packet.score ?: "")
            if (packet.update) {
                if (version >= ProtocolVersion.MC1_8) buffer.writeVarInt(packet.value ?: 0)
                else {
                    buffer.writeString(packet.score ?: "")
                    buffer.writeInt(packet.value ?: 0)
                }
            }
        }
    }
}
