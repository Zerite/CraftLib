package dev.zerite.craftlib.protocol.packet.play.server.join

import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.enum.Difficulty
import dev.zerite.craftlib.protocol.data.enum.Dimension
import dev.zerite.craftlib.protocol.data.enum.Gamemode
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.util.delegate.bitBoolean
import dev.zerite.craftlib.protocol.util.delegate.mapEnum
import dev.zerite.craftlib.protocol.util.ext.clearBit
import dev.zerite.craftlib.protocol.util.ext.setBit
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
    var rawGamemode: Int,
    var rawDimension: Int,
    var rawDifficulty: Int,
    var maxPlayers: Int,
    var levelType: String
) : EntityIdPacket {

    companion object : PacketIO<ServerPlayJoinGamePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayJoinGamePacket(
            buffer.readInt(),
            buffer.readUnsignedByte().toInt(),
            buffer.readByte().toInt(),
            buffer.readUnsignedByte().toInt(),
            buffer.readUnsignedByte().toInt(),
            buffer.readString()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayJoinGamePacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.entityId)
            buffer.writeByte(packet.rawGamemode)
            buffer.writeByte(packet.rawDimension)
            buffer.writeByte(packet.rawDifficulty)
            buffer.writeByte(packet.maxPlayers)
            buffer.writeString(packet.levelType)
        }
    }

    constructor(
        entityId: Int,
        gamemode: Gamemode,
        hardcore: Boolean,
        dimension: Dimension,
        difficulty: Difficulty,
        maxPlayers: Int,
        levelType: String
    ) : this(
        entityId,
        gamemode.id.let { if (hardcore) it.setBit(0x8) else it.clearBit(0x8) },
        dimension.id,
        difficulty.id,
        maxPlayers,
        levelType
    )

    /**
     * Simple mapped gamemode which allows for the raw
     * data to be easily changed.
     *
     * NOTE: This value MUST be changed before hardcore as it will
     * clear the hardcore value.
     */
    var gamemode: Gamemode by mapEnum(this::rawGamemode, Gamemode, Gamemode.SURVIVAL)

    /**
     * Returns true if the world is in hardcore. This is taken
     * from the raw gamemode data.
     */
    var hardcore by bitBoolean(this::rawGamemode, 0x8)

    /**
     * Sets the raw dimension to the mapped enum ID.
     */
    var dimension: Dimension by mapEnum(this::rawDimension, Dimension, Dimension.OVERWORLD)

    /**
     * Sets the raw difficulty ID to the enum's value.
     */
    var difficulty: Difficulty by mapEnum(this::rawDifficulty, Difficulty, Difficulty.PEACEFUL)

}