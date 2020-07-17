package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.Vector3
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent when a client is to play a sound or particle effect.
 *
 * By default, the minecraft client adjusts the volume of sound effects based on
 * distance. The final boolean field is used to disable this, and instead the effect
 * is played from 2 blocks away in the correct direction.
 *
 * Currently this is only used for effect 1013 (mob.wither.spawn), and is ignored for
 * any other value by the client.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayEffectPacket(
    var id: Int,
    var x: Int,
    var y: Int,
    var z: Int,
    var data: Int,
    var broadcast: Boolean
) : Packet() {
    companion object : PacketIO<ServerPlayEffectPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = if (version >= ProtocolVersion.MC1_8) {
            val id = buffer.readInt()
            val position = buffer.readPosition()
            ServerPlayEffectPacket(
                id,
                position.x,
                position.y,
                position.z,
                buffer.readInt(),
                buffer.readBoolean()
            )
        } else {
            ServerPlayEffectPacket(
                buffer.readInt(),
                buffer.readInt(),
                buffer.readByte().toInt(),
                buffer.readInt(),
                buffer.readInt(),
                buffer.readBoolean()
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayEffectPacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.id)
            if (version >= ProtocolVersion.MC1_8) buffer.writePosition(Vector3(packet.x, packet.y, packet.z))
            else {
                buffer.writeInt(packet.x)
                buffer.writeByte(packet.y)
                buffer.writeInt(packet.z)
            }
            buffer.writeInt(packet.data)
            buffer.writeBoolean(packet.broadcast)
        }
    }
}
