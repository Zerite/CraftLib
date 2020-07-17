package dev.zerite.craftlib.protocol.packet.base

import dev.zerite.craftlib.protocol.Packet

/**
 * Contains the raw bytes and packet ID for packets which
 * cannot be parsed.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class RawPacket(var id: Int, var data: ByteArray) : Packet() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RawPacket

        if (id != other.id) return false
        if (!data.contentEquals(other.data)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + data.contentHashCode()
        return result
    }
}
