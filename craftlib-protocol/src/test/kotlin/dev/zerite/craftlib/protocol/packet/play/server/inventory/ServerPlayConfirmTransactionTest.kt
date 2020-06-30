package dev.zerite.craftlib.protocol.packet.play.server.inventory

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * A packet from the server indicating whether a request from the client was accepted,
 * or whether there was a conflict (due to lag). This packet is also sent from the
 * client to the server in response to a server transaction rejection packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayConfirmTransactionTest :
    PacketTest<ServerPlayConfirmTransactionPacket>(ServerPlayConfirmTransactionPacket) {
    init {
        example(ServerPlayConfirmTransactionPacket(10, 20, true)) {
            ProtocolVersion.MC1_7_2 {
                writeByte(10)
                writeShort(20)
                writeBoolean(true)
            }
        }
        example(ServerPlayConfirmTransactionPacket(74, 14, false)) {
            ProtocolVersion.MC1_7_2 {
                writeByte(74)
                writeShort(14)
                writeBoolean(false)
            }
        }
    }
}