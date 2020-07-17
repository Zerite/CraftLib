package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.data.world.Block
import dev.zerite.craftlib.protocol.data.world.Chunk
import dev.zerite.craftlib.protocol.data.world.ChunkColumn
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.util.ext.deflated
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Tests that chunk data is being properly read and written, and that all
 * map chunks are being properly formatted.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayMapChunkBulkTest : PacketTest<ServerPlayMapChunkBulkPacket>(ServerPlayMapChunkBulkPacket) {

    init {
        example(
            ServerPlayMapChunkBulkPacket(
                false,
                arrayOf(ChunkColumn(42, 42, Array(16) { Chunk() }, byteArrayOf()))
            )
        )
        example(
            ServerPlayMapChunkBulkPacket(
                true,
                arrayOf(
                    ChunkColumn(
                        16,
                        16,
                        Array(16) {
                            Chunk().apply {
                                this[0, 0, 0] = Block(0, 7, 7, 8)
                                this[0, 1, 0] = Block(1, 2, 3, 4)
                            }
                        },
                        ByteArray(16 * 16) { 7 }
                    )
                )
            )
        )

        val column = ChunkColumn(20, 21, Array(16) { Chunk() }, byteArrayOf())
        example(ServerPlayMapChunkBulkPacket(true, arrayOf(column))) {
            ProtocolVersion.MC1_7_2 {
                val compressed = ChunkColumn.writeOneSeven(column, hasSkyLight = true).output.deflated()

                writeShort(1)
                writeInt(compressed.size)
                writeBoolean(true)
                writeBytes(compressed)

                writeInt(20)
                writeInt(21)
                writeShort(0)
                writeShort(0)
            }
            ProtocolVersion.MC1_8 {
                val bytes = ChunkColumn.writeOneEight(column, hasSkyLight = true).output
                writeBoolean(true)
                writeVarInt(1)

                writeInt(20)
                writeInt(21)
                writeShort(0)

                writeBytes(bytes)
            }
        }
    }

}
