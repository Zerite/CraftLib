package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.world.ChunkColumn
import dev.zerite.craftlib.protocol.data.world.ChunkMetadata
import dev.zerite.craftlib.protocol.util.ext.dataInput
import dev.zerite.craftlib.protocol.util.ext.dataOutput
import dev.zerite.craftlib.protocol.util.ext.deflated
import dev.zerite.craftlib.protocol.util.ext.inflated
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
        ): ServerPlayMapChunkBulkPacket {
            val columns = buffer.readShort()
            val length = buffer.readInt()
            val skyLight = buffer.readBoolean()
            val data = buffer.readByteArray(length = length).inflated(196864 * columns)

            var readerIndex = 0
            return ServerPlayMapChunkBulkPacket(
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

                    val chunkBytes = ByteArray(dataLength)
                    System.arraycopy(data, readerIndex, chunkBytes, 0, dataLength)
                    readerIndex += dataLength

                    chunkBytes.dataInput { ChunkColumn.read(this, meta, hasSkyLight = skyLight) }
                }
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayMapChunkBulkPacket,
            connection: NettyConnection
        ) {
            val (stream, meta) = dataOutput {
                packet.columns.map {
                    it to ChunkColumn.write(this, it, hasSkyLight = packet.skyLight)
                }.toTypedArray()
            }
            val bytes = stream.toByteArray().deflated()

            buffer.writeShort(packet.columns.size)
            buffer.writeInt(bytes.size)
            buffer.writeBoolean(packet.skyLight)
            buffer.writeBytes(bytes)

            buffer.writeArray(meta, { }) { (col, out) ->
                writeInt(col.x)
                writeInt(col.z)
                writeShort(out.primaryBitmask)
                writeShort(out.addBitmask)
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