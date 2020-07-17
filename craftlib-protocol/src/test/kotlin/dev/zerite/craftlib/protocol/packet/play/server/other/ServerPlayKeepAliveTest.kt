package dev.zerite.craftlib.protocol.packet.play.server.other

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Tests that the keep alive packet is working.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayKeepAliveTest : PacketTest<ServerPlayKeepAlivePacket>(ServerPlayKeepAlivePacket) {

    init {
        example(ServerPlayKeepAlivePacket(0))
        example(ServerPlayKeepAlivePacket(Int.MAX_VALUE))
        example(ServerPlayKeepAlivePacket(512)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(512)
            }
            ProtocolVersion.MC1_8 {
                writeVarInt(512)
            }
        }
    }

}
