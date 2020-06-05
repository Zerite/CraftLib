package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Tests long writing is correctly working and tests the time update packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayTimeUpdateTest : PacketTest<ServerPlayTimeUpdatePacket>(ServerPlayTimeUpdatePacket) {

    init {
        example(ServerPlayTimeUpdatePacket(0L, 0L))
        example(ServerPlayTimeUpdatePacket(Long.MAX_VALUE, Long.MAX_VALUE))
        example(ServerPlayTimeUpdatePacket(1000L, 1000L)) {
            ProtocolVersion.MC1_7_2 {
                writeLong(1000L)
                writeLong(1000L)
            }
        }
    }

}