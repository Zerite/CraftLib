package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.data.registry.impl.MagicPotionEffect
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to remove a potion effect from an entity.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayRemoveEntityEffectTest :
    PacketTest<ServerPlayRemoveEntityEffectPacket>(ServerPlayRemoveEntityEffectPacket) {
    init {
        example(ServerPlayRemoveEntityEffectPacket(255, MagicPotionEffect.HASTE)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(255)
                writeByte(3)
            }
            ProtocolVersion.MC1_8 {
                writeVarInt(255)
                writeByte(3)
            }
        }
        example(ServerPlayRemoveEntityEffectPacket(127, MagicPotionEffect.FIRE_RESISTANCE)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(127)
                writeByte(12)
            }
            ProtocolVersion.MC1_8 {
                writeVarInt(127)
                writeByte(12)
            }
        }
    }
}
