package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.world.ChunkColumn
import dev.zerite.craftlib.protocol.data.world.ChunkMetadata
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.util.zip.Deflater
import java.util.zip.Inflater

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
            val rawChunkData = buffer.readByteArray(length = length)

            val data = ByteArray(196864 * columns)
            Inflater().apply { setInput(rawChunkData, 0, length) }.inflate(data)

            var readerIndex = 0
            return ServerPlayMapChunkBulkPacket(
                skyLight,
                Array(columns.toInt()) {
                    val meta = ChunkMetadata(
                        buffer.readInt(),
                        buffer.readInt(),
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

                    ChunkColumn.read(DataInputStream(ByteArrayInputStream(chunkBytes)), meta, hasSkyLight = skyLight)
                }
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayMapChunkBulkPacket,
            connection: NettyConnection
        ) {
            val byteOutput = ByteArrayOutputStream()
            val output = DataOutputStream(byteOutput)
            val meta =
                packet.columns.map { it to ChunkColumn.write(output, it, hasSkyLight = packet.skyLight) }.toTypedArray()

            val bytes = byteOutput.toByteArray()
            val compressed = ByteArray(bytes.size)

            Deflater(-1).apply {
                setInput(bytes, 0, bytes.size)
                finish()
                deflate(compressed)
                end()
            }

            buffer.writeShort(packet.columns.size)
            buffer.writeInt(compressed.size)
            buffer.writeBoolean(packet.skyLight)
            buffer.writeBytes(compressed)

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