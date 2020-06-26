package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent when an explosion occurs (creepers, TNT, and ghast fireballs).
 * Each block in Records is set to air. Coordinates for each axis in record is int(X) + record.x
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayExplosionPacket(
    var x: Float,
    var y: Float,
    var z: Float,
    var radius: Float,
    var records: Array<Record>,
    var motionX: Float,
    var motionY: Float,
    var motionZ: Float
) : Packet() {

    companion object : PacketIO<ServerPlayExplosionPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayExplosionPacket(
            buffer.readFloat(),
            buffer.readFloat(),
            buffer.readFloat(),
            buffer.readFloat(),
            buffer.readArray({ readInt() }) {
                Record(
                    buffer.readByte().toInt(),
                    buffer.readByte().toInt(),
                    buffer.readByte().toInt()
                )
            },
            buffer.readFloat(),
            buffer.readFloat(),
            buffer.readFloat()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayExplosionPacket,
            connection: NettyConnection
        ) {
            buffer.writeFloat(packet.x)
            buffer.writeFloat(packet.y)
            buffer.writeFloat(packet.z)
            buffer.writeFloat(packet.radius)
            buffer.writeArray(packet.records, { writeInt(it) }) {
                writeByte(it.x)
                writeByte(it.y)
                writeByte(it.z)
            }
            buffer.writeFloat(packet.motionX)
            buffer.writeFloat(packet.motionY)
            buffer.writeFloat(packet.motionZ)
        }
    }

    /**
     * Represents a single broken block in this explosion.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    data class Record(
        var x: Int,
        var y: Int,
        var z: Int
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServerPlayExplosionPacket

        if (x != other.x) return false
        if (y != other.y) return false
        if (z != other.z) return false
        if (radius != other.radius) return false
        if (!records.contentEquals(other.records)) return false
        if (motionX != other.motionX) return false
        if (motionY != other.motionY) return false
        if (motionZ != other.motionZ) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        result = 31 * result + radius.hashCode()
        result = 31 * result + records.contentHashCode()
        result = 31 * result + motionX.hashCode()
        result = 31 * result + motionY.hashCode()
        result = 31 * result + motionZ.hashCode()
        return result
    }

}