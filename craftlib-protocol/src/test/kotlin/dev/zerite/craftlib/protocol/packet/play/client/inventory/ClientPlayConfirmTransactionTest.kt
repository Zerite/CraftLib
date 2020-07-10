package dev.zerite.craftlib.protocol.packet.play.client.inventory

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Confirm Transaction
 *
 * @author ChachyDev
 * @since 0.1.0-SNAPSHOT
 */
class ClientPlayConfirmTransactionTest :
    PacketTest<ClientPlayConfirmTransactionPacket>(ClientPlayConfirmTransactionPacket) {
    init {
        example(
            ClientPlayConfirmTransactionPacket(
                0,
                1,
                true
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeByte(0)
                writeShort(1)
                writeBoolean(true)
            }
        }
        example(
            ClientPlayConfirmTransactionPacket(
                0,
                1,
                false
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeByte(0)
                writeShort(1)
                writeBoolean(false)
            }
        }
    }
}
