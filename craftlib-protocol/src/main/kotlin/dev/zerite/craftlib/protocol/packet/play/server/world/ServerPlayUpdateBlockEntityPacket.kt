package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.nbt.impl.CompoundTag
import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.BlockEntityUpdateAction
import dev.zerite.craftlib.protocol.version.ProtocolVersion

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
        ) = ServerPlayUpdateBlockEntityPacket(
            buffer.readInt(),
            buffer.readShort().toInt(),
            buffer.readInt(),
            BlockEntityUpdateAction[version, buffer.readUnsignedByte().toInt()],
            buffer.readNBT(compressed = true)?.tag
        )

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayUpdateBlockEntityPacket,
            connection: NettyConnection
        ) {
            buffer.writeInt(packet.x)
            buffer.writeShort(packet.y)
            buffer.writeInt(packet.z)
            buffer.writeByte(BlockEntityUpdateAction[version, packet.action, Int::class] ?: 1)
            buffer.writeNBT(packet.nbt, compressed = true)
        }
    }
}