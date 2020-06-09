package dev.zerite.craftlib.protocol.packet.play.server.inventory

import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.Slot
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to tell the client which items are currently
 * in a window.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayWindowItemsPacket(
    var windowId: Short,
    var slots: Array<Slot>
) {
    companion object : PacketIO<ServerPlayWindowItemsPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = ServerPlayWindowItemsPacket(
            buffer.readUnsignedByte(),
            buffer.readArray({ readShort().toInt() }) { buffer.readSlot() }
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayWindowItemsPacket,
            connection: NettyConnection
        ) {
            buffer.writeByte(packet.windowId.toInt())
            buffer.writeArray(packet.slots, { writeShort(it) }) { writeSlot(it) }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServerPlayWindowItemsPacket

        if (windowId != other.windowId) return false
        if (!slots.contentEquals(other.slots)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = windowId.toInt()
        result = 31 * result + slots.contentHashCode()
        return result
    }
}