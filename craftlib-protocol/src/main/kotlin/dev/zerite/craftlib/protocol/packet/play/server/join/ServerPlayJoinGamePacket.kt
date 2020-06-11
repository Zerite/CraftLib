package dev.zerite.craftlib.protocol.packet.play.server.join

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.Difficulty
import dev.zerite.craftlib.protocol.data.registry.impl.Dimension
import dev.zerite.craftlib.protocol.data.registry.impl.Gamemode
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
    var levelType: String
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
                Gamemode[version, gamemode and 0x7],
                Dimension[version, buffer.readByte().toInt()],
                Difficulty[version, buffer.readUnsignedByte().toInt()],
                buffer.readUnsignedByte().toInt(),
                buffer.readString()
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
                (Gamemode[version, packet.gamemode, Int::class] ?: 0) or
                        (if (packet.hardcore) 0x8 else 0x0)
            )
            buffer.writeByte(Dimension[version, packet.dimension, Int::class] ?: 0)
            buffer.writeByte(Difficulty[version, packet.difficulty, Int::class] ?: 0)
            buffer.writeByte(packet.maxPlayers)
            buffer.writeString(packet.levelType)
        }
    }

}