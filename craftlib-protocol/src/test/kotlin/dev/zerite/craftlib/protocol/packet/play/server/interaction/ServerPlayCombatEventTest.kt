package dev.zerite.craftlib.protocol.packet.play.server.interaction

import dev.zerite.craftlib.protocol.data.registry.impl.MagicCombatEvent
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent to the client to update their combat status.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
class ServerPlayCombatEventTest : PacketTest<ServerPlayCombatEventPacket>(ServerPlayCombatEventPacket) {
    init {
        example(
            ServerPlayCombatEventPacket(
                MagicCombatEvent.ENTITY_DEAD,
                playerId = 2,
                entityId = 1,
                message = "message"
            )
        ) {
            ProtocolVersion.MC1_8 {
                writeVarInt(2)
                writeVarInt(2)
                writeInt(1)
                writeString("message")
            }
        }
        example(ServerPlayCombatEventPacket(MagicCombatEvent.END_COMBAT, duration = 60, entityId = 10)) {
            ProtocolVersion.MC1_8 {
                writeVarInt(1)
                writeVarInt(60)
                writeInt(10)
            }
        }
    }
}
