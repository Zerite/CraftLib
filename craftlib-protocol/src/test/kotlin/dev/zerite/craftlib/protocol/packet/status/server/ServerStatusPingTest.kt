package dev.zerite.craftlib.protocol.packet.status.server

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent to the client to calculate their ping to the server.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerStatusPingTest : PacketTest<ServerStatusPingPacket>(ServerStatusPingPacket) {
    init {
        example(ServerStatusPingPacket(Long.MAX_VALUE)) {
            ProtocolVersion.MC1_7_2 {
                writeLong(Long.MAX_VALUE)
            }
        }
        example(ServerStatusPingPacket(6023L)) {
            ProtocolVersion.MC1_7_2 {
                writeLong(6023L)
            }
        }
    }
}
