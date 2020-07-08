package dev.zerite.craftlib.protocol.packet.play.client.interaction

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.packet.base.EntityIdPacket
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This packet is sent from the client to the server when the client attacks or
 * right-clicks another entity (a player, minecart, etc).
 *
 * A Notchian server only accepts this packet if the entity being attacked/used is visible
 * without obstruction and within a 4-unit radius of the player's position.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ClientPlayUseEntityPacket(
    override var entityId: Int,
    var mouse: Int
) : EntityIdPacket, Packet() {
    companion object : PacketIO<ClientPlayUseEntityPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ClientPlayUseEntityPacket(
            buffer.readInt(),
            buffer.readByte().toInt()
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ClientPlayUseEntityPacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.entityId)
            buffer.writeByte(packet.mouse)
        }
    }
}
