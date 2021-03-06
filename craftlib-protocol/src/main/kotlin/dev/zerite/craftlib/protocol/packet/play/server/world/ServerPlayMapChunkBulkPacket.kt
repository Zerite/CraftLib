package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.world.ChunkColumn
import dev.zerite.craftlib.protocol.data.world.ChunkMetadata
import dev.zerite.craftlib.protocol.util.ext.deflated
import dev.zerite.craftlib.protocol.util.ext.inflated
import dev.zerite.craftlib.protocol.util.ext.trim
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Reads and writes multiple chunk columns into a packet, sent from the server when
 * multiple new chunk columns need to be loaded at once.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayMapChunkBulkPacket(
    var skyLight: Boolean,
    var columns: Array<ChunkColumn>
) : Packet() {

    companion object : PacketIO<ServerPlayMapChunkBulkPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = if (version >= ProtocolVersion.MC1_8) {
            val skyLight = buffer.readBoolean()
            val meta = buffer.readArray {
                ChunkMetadata(
                    readInt(),
                    readInt(),
                    true,
                    readUnsignedShort(),
                    0
                )
            }

            ServerPlayMapChunkBulkPacket(
                skyLight,
                Array(meta.size) {
                    val metadata = meta[it]

                    var primarySize = 0
                    for (chunkIndex in 0..15)
                        primarySize += metadata.primaryBitmap shr chunkIndex and 1

                    var dataLength = 10240 * primarySize + 256
                    if (skyLight) dataLength += 2048 * primarySize

                    val chunkBytes = buffer.readByteArray(length = dataLength)
                    ChunkColumn.readOneEight(chunkBytes, metadata, hasSkyLight = skyLight)
                }
            )
        } else {
            val columns = buffer.readShort()
            val length = buffer.readInt()
            val skyLight = buffer.readBoolean()
            val data = buffer.readByteArray(length = length).inflated(196864 * columns)

            var readerIndex = 0
            ServerPlayMapChunkBulkPacket(
                skyLight,
                Array(columns.toInt()) {
                    val meta = ChunkMetadata(
                        buffer.readInt(),
                        buffer.readInt(),
                        true,
                        buffer.readShort().toInt(),
                        buffer.readShort().toInt()
                    )

                    var primarySize = 0
                    var secondarySize = 0
                    for (chunkIndex in 0..15) {
                        primarySize += meta.primaryBitmap shr chunkIndex and 1
                        secondarySize += meta.addBitmap shr chunkIndex and 1
                    }

                    var dataLength = 2048 * 4 * primarySize + 256
                    dataLength += 2048 * secondarySize
                    if (skyLight) dataLength += 2048 * primarySize
                    if (meta.addBitmap and (1 shl it) != 0) dataLength += 2048 * secondarySize

                    val chunkBytes = ByteArray(dataLength)
                    System.arraycopy(data, readerIndex, chunkBytes, 0, dataLength)
                    readerIndex += dataLength

                    ChunkColumn.readOneSeven(chunkBytes, meta, hasSkyLight = skyLight)
                }
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayMapChunkBulkPacket,
            connection: NettyConnection
        ) {
            val bytes = ByteArray(196864 * packet.columns.size)
            var bytesPosition = 0

            val output = packet.columns.map {
                it to (if (version >= ProtocolVersion.MC1_8) ChunkColumn.writeOneEight(it, hasSkyLight = packet.skyLight)
                else ChunkColumn.writeOneSeven(it, hasSkyLight = packet.skyLight)).apply {
                    System.arraycopy(output, 0, bytes, bytesPosition, output.size)
                    bytesPosition += output.size
                }
            }.toTypedArray()

            if (version >= ProtocolVersion.MC1_8) {
                buffer.writeBoolean(packet.skyLight)
                buffer.writeArray(output) { (col, out) ->
                    writeInt(col.x)
                    writeInt(col.z)
                    writeShort(out.primaryBitmask)
                }
                buffer.writeBytes(bytes.trim(bytesPosition))
            } else {
                buffer.writeShort(packet.columns.size)
                buffer.writeInt(bytesPosition)
                buffer.writeBoolean(packet.skyLight)
                buffer.writeBytes(bytes.trim(bytesPosition).deflated())

                buffer.writeArray(output, { }) { (col, out) ->
                    writeInt(col.x)
                    writeInt(col.z)
                    writeShort(out.primaryBitmask)
                    writeShort(out.addBitmask)
                }
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServerPlayMapChunkBulkPacket

        if (skyLight != other.skyLight) return false
        if (!columns.contentEquals(other.columns)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = skyLight.hashCode()
        result = 31 * result + columns.contentHashCode()
        return result
    }

}
