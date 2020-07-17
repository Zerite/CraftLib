package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.data.registry.impl.MagicPotionEffect
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to tell the client that a potion effect has been
 * applied to an entity.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayEntityEffectTest : PacketTest<ServerPlayEntityEffectPacket>(ServerPlayEntityEffectPacket) {
    init {
        example(ServerPlayEntityEffectPacket(10, MagicPotionEffect.BLINDNESS, 1, 20)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(10)
                writeByte(15)
                writeByte(1)
                writeShort(20)
            }
            ProtocolVersion.MC1_8 {
                writeVarInt(10)
                writeByte(15)
                writeByte(1)
                writeVarInt(20)
                writeBoolean(false)
            }
        }
        example(ServerPlayEntityEffectPacket(50, MagicPotionEffect.HASTE, 20, 40)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(50)
                writeByte(3)
                writeByte(20)
                writeShort(40)
            }
            ProtocolVersion.MC1_8 {
                writeVarInt(50)
                writeByte(3)
                writeByte(20)
                writeVarInt(40)
                writeBoolean(false)
            }
        }
    }
}
