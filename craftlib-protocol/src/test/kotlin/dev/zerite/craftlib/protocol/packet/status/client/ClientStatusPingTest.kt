package dev.zerite.craftlib.protocol.packet.status.client

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the client to calculate the ping to the server.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ClientStatusPingTest : PacketTest<ClientStatusPingPacket>(ClientStatusPingPacket) {
    init {
        example(ClientStatusPingPacket(420L)) {
            ProtocolVersion.MC1_7_2 {
                writeLong(420L)
            }
        }
        example(ClientStatusPingPacket(Long.MAX_VALUE)) {
            ProtocolVersion.MC1_7_2 {
                writeLong(Long.MAX_VALUE)
            }
        }
    }
}
