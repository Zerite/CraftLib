package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * The latter 2 bytes are used to indicate the walking and flying speeds respectively, while the first byte is used to determine the value of 4 booleans.
 *
 * The flags are whether damage is disabled (god mode, 8, bit 3), whether the player can fly (4, bit 2), whether the player is flying (2, bit 1), and whether the player is in creative mode (1, bit 0).
 *
 * To get the values of these booleans, simply AND (&) the byte with 1,2,4 and 8 respectively, to get the 0 or 1 bitwise value.
 * To set them OR (|) them with their respective masks.
 * The vanilla client sends this packet when the player starts/stops flying with the second parameter changed accordingly.
 * All other parameters are ignored by the vanilla server.
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */

data class ClientPlayPlayerAbilitiesPacket(
        var flags: Int,
        var flyingSpeed: Float,
        var walkingSpeed: Float
) : Packet() {
    companion object : PacketIO<ClientPlayPlayerAbilitiesPacket> {
        override fun read(
                buffer: ProtocolBuffer,
                version: ProtocolVersion,
                connection: NettyConnection
        ) = ClientPlayPlayerAbilitiesPacket(
                buffer.readByte().toInt(),
                buffer.readFloat(),
                buffer.readFloat()
        )

        override fun write(
                buffer: ProtocolBuffer,
                version: ProtocolVersion,
                packet: ClientPlayPlayerAbilitiesPacket,
                connection: NettyConnection
        ) {
            buffer.writeByte(packet.flags)
            buffer.writeFloat(packet.flyingSpeed)
            buffer.writeFloat(packet.walkingSpeed)
        }
    }
}