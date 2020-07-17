package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to change the clients camera position.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
class ServerPlayCameraTest : PacketTest<ServerPlayCameraPacket>(ServerPlayCameraPacket) {
    init {
        example(ServerPlayCameraPacket(3432)) {
            ProtocolVersion.MC1_8 {
                writeVarInt(3432)
            }
        }
    }
}
