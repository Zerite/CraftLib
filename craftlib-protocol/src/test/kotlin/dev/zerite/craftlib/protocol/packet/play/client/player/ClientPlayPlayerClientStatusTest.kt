package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

class ClientPlayPlayerClientStatusTest : PacketTest<ClientPlayPlayerClientStatusPacket>(ClientPlayPlayerClientStatusPacket) {
    init {
        example(ClientPlayPlayerClientStatusPacket(0)) {
            ProtocolVersion.MC1_7_2 {
                writeByte(0)
            }
        }
    }
}