package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Tells the client to unload a chunk column.
 *
 * @author Koding
 * @since  0.2.0
 */
data class ServerPlayUnloadChunkPacket constructor(
    var chunkX: Int,
    var chunkZ: Int
) : Packet() {
    companion object : PacketIO<ServerPlayUnloadChunkPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ): ServerPlayUnloadChunkPacket = ServerPlayUnloadChunkPacket(
            buffer.readInt(),
            buffer.readInt()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayUnloadChunkPacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.chunkX)
            buffer.writeInt(packet.chunkZ)
        }
    }
}
