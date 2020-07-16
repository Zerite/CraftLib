package dev.zerite.craftlib.protocol.packet.status.server

import dev.zerite.craftlib.protocol.data.other.StatusResponse
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to players to display specific data in the
 * server list.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerStatusResponseTest : PacketTest<ServerStatusResponsePacket>(ServerStatusResponsePacket) {
    init {
        example(ServerStatusResponsePacket(StatusResponse())) {
            ProtocolVersion.MC1_7_2 {
                writeString("{\"version\":{\"name\":\"CraftLib\",\"protocol\":4},\"players\":{\"max\":1,\"online\":0},\"description\":{\"text\":\"\"}}")
            }
        }
    }
}
