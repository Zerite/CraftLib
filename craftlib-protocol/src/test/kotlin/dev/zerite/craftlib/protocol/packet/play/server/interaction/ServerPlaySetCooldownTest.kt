package dev.zerite.craftlib.protocol.packet.play.server.interaction

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Applies a cooldown period to an item type.
 *
 * @author Koding
 * @since  0.2.0
 */
class ServerPlaySetCooldownTest : PacketTest<ServerPlaySetCooldownPacket>(ServerPlaySetCooldownPacket) {
    init {
        example(
            ServerPlaySetCooldownPacket(
                0,
                0
            )
        ) {
            ProtocolVersion.MC1_9 {
                writeVarInt(0)
                writeVarInt(0)
            }
        }
        example(
            ServerPlaySetCooldownPacket(
                1,
                20
            )
        ) {
            ProtocolVersion.MC1_9 {
                writeVarInt(1)
                writeVarInt(20)
            }
        }
    }
}
