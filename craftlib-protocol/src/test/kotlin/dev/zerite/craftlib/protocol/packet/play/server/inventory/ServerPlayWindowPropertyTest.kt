package dev.zerite.craftlib.protocol.packet.play.server.inventory

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server whilst in a window to update a specific value.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayWindowPropertyTest : PacketTest<ServerPlayWindowPropertyPacket>(ServerPlayWindowPropertyPacket) {
    init {
        example(ServerPlayWindowPropertyPacket(40, 0, 50)) {
            ProtocolVersion.MC1_7_2 {
                writeByte(40)
                writeShort(0)
                writeShort(50)
            }
        }
        example(ServerPlayWindowPropertyPacket(100, 2, 30)) {
            ProtocolVersion.MC1_7_2 {
                writeByte(100)
                writeShort(2)
                writeShort(30)
            }
        }
    }
}