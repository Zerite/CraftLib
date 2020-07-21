package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicParticleType
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Displays the named particle.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayParticlePacket @JvmOverloads constructor(
    var type: RegistryEntry,
    var longDistance: Boolean,
    var x: Float,
    var y: Float,
    var z: Float,
    var offsetX: Float,
    var offsetY: Float,
    var offsetZ: Float,
    var particleData: Float,
    var count: Int,
    var data: Array<Int> = arrayOf()
) : Packet() {
    companion object : PacketIO<ServerPlayParticlePacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = if (version >= ProtocolVersion.MC1_8) {
            val type = MagicParticleType[version, buffer.readInt()]
            ServerPlayParticlePacket(
                type,
                buffer.readBoolean(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readInt(),
                buffer.readArray({ (type as? MagicParticleType)?.argumentCount ?: 0 }) { readVarInt() }
            )
        } else {
            ServerPlayParticlePacket(
                MagicParticleType[version, buffer.readString()],
                false,
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readFloat(),
                buffer.readInt()
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayParticlePacket,
            connection: NettyConnection
        ) {
            if (version >= ProtocolVersion.MC1_8)
                buffer.writeInt(MagicParticleType[version, packet.type, Int::class.java] ?: 0)
            else buffer.writeString(MagicParticleType[version, packet.type, String::class.java] ?: "")
            if (version >= ProtocolVersion.MC1_8)
                buffer.writeBoolean(packet.longDistance)
            buffer.writeFloat(packet.x)
            buffer.writeFloat(packet.y)
            buffer.writeFloat(packet.z)
            buffer.writeFloat(packet.offsetX)
            buffer.writeFloat(packet.offsetY)
            buffer.writeFloat(packet.offsetZ)
            buffer.writeFloat(packet.particleData)
            buffer.writeInt(packet.count)
            if (version >= ProtocolVersion.MC1_8)
                buffer.writeArray(packet.data, length = {}) { writeVarInt(it) }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServerPlayParticlePacket

        if (type != other.type) return false
        if (longDistance != other.longDistance) return false
        if (x != other.x) return false
        if (y != other.y) return false
        if (z != other.z) return false
        if (offsetX != other.offsetX) return false
        if (offsetY != other.offsetY) return false
        if (offsetZ != other.offsetZ) return false
        if (particleData != other.particleData) return false
        if (count != other.count) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + longDistance.hashCode()
        result = 31 * result + x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        result = 31 * result + offsetX.hashCode()
        result = 31 * result + offsetY.hashCode()
        result = 31 * result + offsetZ.hashCode()
        result = 31 * result + particleData.hashCode()
        result = 31 * result + count
        result = 31 * result + data.contentHashCode()
        return result
    }
}
