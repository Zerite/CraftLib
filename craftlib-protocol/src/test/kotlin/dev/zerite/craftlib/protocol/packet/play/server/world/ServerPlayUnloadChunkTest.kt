package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Tells the client to unload a chunk column.
 *
 * @author Koding
 * @since  0.2.0
 */
class ServerPlayUnloadChunkTest : PacketTest<ServerPlayUnloadChunkPacket>(ServerPlayUnloadChunkPacket) {
    init {
        example(ServerPlayUnloadChunkPacket(0, 0)) {
            ProtocolVersion.MC1_9 {
                writeInt(0)
                writeInt(0)
            }
        }
        example(ServerPlayUnloadChunkPacket(42, 123)) {
            ProtocolVersion.MC1_9 {
                writeInt(42)
                writeInt(123)
            }
        }
    }
}
