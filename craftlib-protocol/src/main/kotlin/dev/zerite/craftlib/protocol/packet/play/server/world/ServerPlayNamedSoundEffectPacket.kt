package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicSoundCategory
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import kotlin.math.roundToInt

/**
 * Used to play a sound effect on the client.
 * Custom sounds may be added by resource packs.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayNamedSoundEffectPacket(
    var name: String,
    var soundCategory: RegistryEntry,
    var x: Int,
    var y: Int,
    var z: Int,
    var volume: Float,
    var pitch: Float
) : Packet() {
    companion object : PacketIO<ServerPlayNamedSoundEffectPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayNamedSoundEffectPacket(
            buffer.readString(),
            if (version >= ProtocolVersion.MC1_9) MagicSoundCategory[version, buffer.readVarInt()] else MagicSoundCategory.MASTER,
            buffer.readInt(),
            buffer.readInt(),
            buffer.readInt(),
            buffer.readFloat(),
            buffer.readUnsignedByte() / 63f
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayNamedSoundEffectPacket,
            connection: NettyConnection
        ) {
            buffer.writeString(packet.name)

            if (version >= ProtocolVersion.MC1_9)
                buffer.writeVarInt(
                    MagicSoundCategory[version, packet.soundCategory, Int::class.java]
                        ?: error("Invalid mapping for sound category")
                )

            buffer.writeInt(packet.x)
            buffer.writeInt(packet.y)
            buffer.writeInt(packet.z)
            buffer.writeFloat(packet.volume)
            buffer.writeByte((packet.pitch * 63).roundToInt())
        }
    }
}