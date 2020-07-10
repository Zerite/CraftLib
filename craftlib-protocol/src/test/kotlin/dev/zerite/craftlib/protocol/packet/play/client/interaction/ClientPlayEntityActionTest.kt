package dev.zerite.craftlib.protocol.packet.play.client.interaction

import dev.zerite.craftlib.protocol.data.registry.impl.MagicEntityAction
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent at least when crouching, leaving a bed, or sprinting. To send action animation to client use 0x28.
 * The client will send this with Action ID = 3 when "Leave Bed" is clicked.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ClientPlayEntityActionTest : PacketTest<ClientPlayEntityActionPacket>(ClientPlayEntityActionPacket) {
    init {
        example(ClientPlayEntityActionPacket(100, MagicEntityAction.LEAVE_BED, 100)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(100)
                writeByte(3)
                writeInt(100)
            }
        }
        example(ClientPlayEntityActionPacket(1277, MagicEntityAction.START_SPRINTING, 2)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(1277)
                writeByte(4)
                writeInt(2)
            }
        }
    }
}
