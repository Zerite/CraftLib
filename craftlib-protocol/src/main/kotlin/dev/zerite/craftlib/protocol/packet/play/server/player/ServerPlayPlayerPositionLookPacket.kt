package dev.zerite.craftlib.protocol.packet.play.server.player

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.util.delegate.bitBoolean
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to update the client's position and look vectors.
 * Additionally it is used to take the client out of the loading screen upon
 * joining a world.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("UNUSED")
data class ServerPlayPlayerPositionLookPacket(
    var x: Double,
    var y: Double,
    var z: Double,
    var yaw: Float,
    var pitch: Float,
    var onGround: Boolean,
    var relative: Int = 0
) : Packet() {

    /**
     * The relative fields which can change the meaning of the values.
     */
    val relativeX by bitBoolean(this::relative, 0x01)
    val relativeY by bitBoolean(this::relative, 0x02)
    val relativeZ by bitBoolean(this::relative, 0x04)
    val relativeYRotation by bitBoolean(this::relative, 0x08)
    val relativeXRotation by bitBoolean(this::relative, 0x10)

    companion object : PacketIO<ServerPlayPlayerPositionLookPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayPlayerPositionLookPacket(
            buffer.readDouble(),
            buffer.readDouble(),
            buffer.readDouble(),
            buffer.readFloat(),
            buffer.readFloat(),
            if (version >= ProtocolVersion.MC1_8) false else buffer.readBoolean(),
            if (version >= ProtocolVersion.MC1_8) buffer.readByte().toInt() else 0
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayPlayerPositionLookPacket,
            connection: NettyConnection
        ) {
            buffer.writeDouble(packet.x)
            buffer.writeDouble(packet.y)
            buffer.writeDouble(packet.z)
            buffer.writeFloat(packet.yaw)
            buffer.writeFloat(packet.pitch)
            if (version <= ProtocolVersion.MC1_7_6)
                buffer.writeBoolean(packet.onGround)
            if (version >= ProtocolVersion.MC1_8)
                buffer.writeByte(packet.relative)
        }
    }
}
