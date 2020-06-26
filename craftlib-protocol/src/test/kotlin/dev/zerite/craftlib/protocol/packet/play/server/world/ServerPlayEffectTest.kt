package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent when a client is to play a sound or particle effect.
 *
 * By default, the minecraft client adjusts the volume of sound effects based on
 * distance. The final boolean field is used to disable this, and instead the effect
 * is played from 2 blocks away in the correct direction.
 *
 * Currently this is only used for effect 1013 (mob.wither.spawn), and is ignored for
 * any other value by the client.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayEffectTest : PacketTest<ServerPlayEffectPacket>(ServerPlayEffectPacket) {
    init {
        example(ServerPlayEffectPacket(1000, 100, 120, 1000, 1, true)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(1000)
                writeInt(100)
                writeByte(120)
                writeInt(1000)
                writeInt(1)
                writeBoolean(true)
            }
        }
        example(ServerPlayEffectPacket(20, 20, 20, 20, 20, false)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(20)
                writeInt(20)
                writeByte(20)
                writeInt(20)
                writeInt(20)
                writeBoolean(false)
            }
        }
    }
}