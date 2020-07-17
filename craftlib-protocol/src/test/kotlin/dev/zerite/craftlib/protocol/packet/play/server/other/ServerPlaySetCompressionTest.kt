package dev.zerite.craftlib.protocol.packet.play.server.other

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Controls the max size of a packet before it is compressed.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
class ServerPlaySetCompressionTest : PacketTest<ServerPlaySetCompressionPacket>(ServerPlaySetCompressionPacket) {
    init {
        example(ServerPlaySetCompressionPacket(53)) {
            ProtocolVersion.MC1_8 {
                writeVarInt(53)
            }
        }
    }
}
