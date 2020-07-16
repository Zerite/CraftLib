package dev.zerite.craftlib.protocol.packet.play.server.other

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicDifficulty
import dev.zerite.craftlib.protocol.data.registry.impl.MagicDimension
import dev.zerite.craftlib.protocol.data.registry.impl.MagicGamemode
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
            MagicDimension[version, buffer.readInt()],
            MagicDifficulty[version, buffer.readUnsignedByte().toInt()],
            MagicGamemode[version, buffer.readUnsignedByte().toInt()],
            buffer.readString()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayRespawnPacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(MagicDimension[version, packet.dimension, Int::class] ?: 0)
            buffer.writeByte(MagicDifficulty[version, packet.difficulty, Int::class] ?: 0)
            buffer.writeByte(MagicGamemode[version, packet.gamemode, Int::class] ?: 0)
            buffer.writeString(packet.levelType)
        }
    }
}
