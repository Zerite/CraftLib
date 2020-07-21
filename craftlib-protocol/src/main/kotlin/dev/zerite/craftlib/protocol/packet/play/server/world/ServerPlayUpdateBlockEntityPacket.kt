package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.nbt.impl.CompoundTag
import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.Vector3
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicBlockEntityUpdateAction
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Handles updating a block entity with extra NBT data.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayUpdateBlockEntityPacket(
    var x: Int,
    var y: Int,
    var z: Int,
    var action: RegistryEntry,
    var nbt: CompoundTag?
) : Packet() {
    companion object : PacketIO<ServerPlayUpdateBlockEntityPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = if (version >= ProtocolVersion.MC1_8) {
            val position = buffer.readPosition()
            ServerPlayUpdateBlockEntityPacket(
                position.x,
                position.y,
                position.z,
                MagicBlockEntityUpdateAction[version, buffer.readUnsignedByte().toInt()],
                buffer.readNBT(length = { buffer.readableBytes })?.tag
            )
        } else {
            ServerPlayUpdateBlockEntityPacket(
                buffer.readInt(),
                buffer.readShort().toInt(),
                buffer.readInt(),
                MagicBlockEntityUpdateAction[version, buffer.readUnsignedByte().toInt()],
                buffer.readNBT(compressed = true)?.tag
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayUpdateBlockEntityPacket,
            connection: NettyConnection
        ) {
            if (version >= ProtocolVersion.MC1_8) buffer.writePosition(Vector3(packet.x, packet.y, packet.z))
            else {
                buffer.writeInt(packet.x)
                buffer.writeShort(packet.y)
                buffer.writeInt(packet.z)
            }
            buffer.writeByte(MagicBlockEntityUpdateAction[version, packet.action, Int::class.java] ?: 1)
            buffer.writeNBT(
                packet.nbt,
                compressed = version <= ProtocolVersion.MC1_7_6
            ) { if (version <= ProtocolVersion.MC1_7_6) writeShort(it) }
        }
    }
}
