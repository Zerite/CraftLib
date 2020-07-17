package dev.zerite.craftlib.protocol.packet.play.server.join

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicDifficulty
import dev.zerite.craftlib.protocol.data.registry.impl.MagicDimension
import dev.zerite.craftlib.protocol.data.registry.impl.MagicGamemode
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent from the server to indicate that we should begin play
 * and start handling game-related packets. This provides additional
 * information about the world and server.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("UNUSED")
data class ServerPlayJoinGamePacket(
    override var entityId: Int,
    var hardcore: Boolean,
    var gamemode: RegistryEntry,
    var dimension: RegistryEntry,
    var difficulty: RegistryEntry,
    var maxPlayers: Int,
    var levelType: String,
    var reducedDebugInfo: Boolean
) : EntityIdPacket, Packet() {

    companion object : PacketIO<ServerPlayJoinGamePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ): ServerPlayJoinGamePacket {
            val entityId = buffer.readInt()
            val gamemode = buffer.readUnsignedByte().toInt()
            return ServerPlayJoinGamePacket(
                entityId,
                gamemode and 0x8 == 0x8,
                MagicGamemode[version, gamemode and 0x7],
                MagicDimension[version, buffer.readByte().toInt()],
                MagicDifficulty[version, buffer.readUnsignedByte().toInt()],
                buffer.readUnsignedByte().toInt(),
                buffer.readString(),
                if (version >= ProtocolVersion.MC1_8) buffer.readBoolean() else false
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayJoinGamePacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.entityId)
            buffer.writeByte(
                (MagicGamemode[version, packet.gamemode, Int::class] ?: 0) or
                        (if (packet.hardcore) 0x8 else 0x0)
            )
            buffer.writeByte(MagicDimension[version, packet.dimension, Int::class] ?: 0)
            buffer.writeByte(MagicDifficulty[version, packet.difficulty, Int::class] ?: 0)
            buffer.writeByte(packet.maxPlayers)
            buffer.writeString(packet.levelType)
            if (version >= ProtocolVersion.MC1_8)
                buffer.writeBoolean(packet.reducedDebugInfo)
        }
    }

}
