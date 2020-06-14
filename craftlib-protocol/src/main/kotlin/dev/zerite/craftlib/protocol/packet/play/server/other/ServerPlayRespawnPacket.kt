package dev.zerite.craftlib.protocol.packet.play.server.other

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.Difficulty
import dev.zerite.craftlib.protocol.data.registry.impl.Dimension
import dev.zerite.craftlib.protocol.data.registry.impl.Gamemode
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * To change the player's dimension (overworld/nether/end/custom), send them a respawn packet
 * with the appropriate dimension, followed by chunks for the new dimension, and finally
 * a position and look packet. You do not need to unload chunks, the client will do
 * it automatically.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayRespawnPacket(
    var dimension: RegistryEntry,
    var difficulty: RegistryEntry,
    var gamemode: RegistryEntry,
    var levelType: String
) : Packet() {
    companion object : PacketIO<ServerPlayRespawnPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayRespawnPacket(
            Dimension[version, buffer.readInt()],
            Difficulty[version, buffer.readUnsignedByte().toInt()],
            Gamemode[version, buffer.readUnsignedByte().toInt()],
            buffer.readString()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayRespawnPacket,
            connection: NettyConnection
        ) {
            buffer.writeInt((Dimension[version, packet.dimension] as? Int?) ?: 0)
            buffer.writeByte((Difficulty[version, packet.difficulty] as? Int) ?: 0)
            buffer.writeByte((Gamemode[version, packet.gamemode] as? Int) ?: 0)
            buffer.writeString(packet.levelType)
        }
    }
}