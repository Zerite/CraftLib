package dev.zerite.craftlib.protocol.packet.play.client.player

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Confirm Transaction
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */


class ClientPlayPlayerConfirmTransactionTest : PacketTest<ClientPlayPlayerConfirmTransactionPacket>(ClientPlayPlayerConfirmTransactionPacket) {
    init {
        example(ClientPlayPlayerConfirmTransactionPacket(0, 1, true)) {
            ProtocolVersion.MC1_7_2 {
                writeByte(0)
                writeShort(1)
                writeBoolean(true)
            }
        }

        example(ClientPlayPlayerConfirmTransactionPacket(0, 1, false)) {
            ProtocolVersion.MC1_7_2 {
                writeByte(0)
                writeShort(1)
                writeBoolean(false)
            }
        }
    }
}