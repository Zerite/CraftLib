package dev.zerite.craftlib.protocol.packet.login.server

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sets the compression threshold to use throughout this connection.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
class ServerLoginSetCompressionTest : PacketTest<ServerLoginSetCompressionPacket>(ServerLoginSetCompressionPacket) {
    init {
        example(ServerLoginSetCompressionPacket(421)) {
            ProtocolVersion.MC1_8 {
                writeVarInt(421)
            }
        }
    }
}
