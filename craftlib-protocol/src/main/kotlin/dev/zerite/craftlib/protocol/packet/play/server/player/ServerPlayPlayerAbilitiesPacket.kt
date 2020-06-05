package dev.zerite.craftlib.protocol.packet.play.server.player

import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.util.delegate.bitBoolean
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to tell the client what operations (particularly with movement)
 * are valid, including flying and whether it should take damage.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("UNUSED")
data class ServerPlayPlayerAbilitiesPacket(
    var flags: Int,
    var flyingSpeed: Float,
    var walkingSpeed: Float
) {
    companion object : PacketIO<ServerPlayPlayerAbilitiesPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayPlayerAbilitiesPacket(
            buffer.readByte().toInt(),
            buffer.readFloat(),
            buffer.readFloat()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayPlayerAbilitiesPacket,
            connection: NettyConnection
        ) {
            buffer.writeByte(packet.flags)
            buffer.writeFloat(packet.flyingSpeed)
            buffer.writeFloat(packet.walkingSpeed)
        }
    }

    /**
     * Whether this player has damage disabled.
     */
    var god by bitBoolean(this::flags, 0x8)

    /**
     * Whether the player is allowed to fly, such as when in
     * creative mode.
     */
    var canFly by bitBoolean(this::flags, 0x4)

    /**
     * Whether the player is currently flying.
     */
    var flying by bitBoolean(this::flags, 0x2)

    /**
     * Whether this player is in creative mode. This changes the
     * GUI display and opens the creative inventory.
     */
    var creative by bitBoolean(this::flags, 0x1)
}