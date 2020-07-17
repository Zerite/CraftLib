package dev.zerite.craftlib.protocol.packet.play.client.interaction

import dev.zerite.craftlib.protocol.data.registry.impl.MagicPlayerDiggingStatus
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent when the player mines a block.
 * A Notchian server only accepts digging packets with coordinates within a 6-unit radius of the player's position.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ClientPlayPlayerDiggingTest : PacketTest<ClientPlayPlayerDiggingPacket>(ClientPlayPlayerDiggingPacket) {
    init {
        example(ClientPlayPlayerDiggingPacket(MagicPlayerDiggingStatus.DROP_ITEM, 0, 0, 0, 127)) {
            ProtocolVersion.MC1_7_2 {
                writeByte(4)
                writeInt(0)
                writeByte(0)
                writeInt(0)
                writeByte(127)
            }
            ProtocolVersion.MC1_8 {
                writeByte(4)
                writeLong(((0L and 0x3FFFFFFL) shl 38) or ((0L and 0x3FFFFFFL) shl 12) or (0L and 0xFFFL))
                writeByte(127)
            }
        }
        example(ClientPlayPlayerDiggingPacket(MagicPlayerDiggingStatus.STARTED_DIGGING, 50, 64, 1072, 0)) {
            ProtocolVersion.MC1_7_2 {
                writeByte(0)
                writeInt(50)
                writeByte(64)
                writeInt(1072)
                writeByte(0)
            }
            ProtocolVersion.MC1_8 {
                writeByte(0)
                writeLong(((50L and 0x3FFFFFFL) shl 38) or ((1072L and 0x3FFFFFFL) shl 12) or (64L and 0xFFFL))
                writeByte(0)
            }
        }
    }
}
