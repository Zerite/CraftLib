package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.data.registry.impl.MagicParticleType
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Displays the named particle.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayParticleTest : PacketTest<ServerPlayParticlePacket>(ServerPlayParticlePacket) {
    init {
        example(
            ServerPlayParticlePacket(
                MagicParticleType.SMOKE_NORMAL,
                false,
                500f,
                250f,
                500f,
                1f,
                1f,
                1f,
                1f,
                100
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeString("smoke")
                writeFloat(500f)
                writeFloat(250f)
                writeFloat(500f)
                writeFloat(1f)
                writeFloat(1f)
                writeFloat(1f)
                writeFloat(1f)
                writeInt(100)
            }
            ProtocolVersion.MC1_8 {
                writeInt(11)
                writeBoolean(false)
                writeFloat(500f)
                writeFloat(250f)
                writeFloat(500f)
                writeFloat(1f)
                writeFloat(1f)
                writeFloat(1f)
                writeFloat(1f)
                writeInt(100)
            }
        }
        example(ServerPlayParticlePacket(MagicParticleType.DRIP_LAVA, false, 200f, 200f, 200f, 0f, 0f, 1f, 0f, 1)) {
            ProtocolVersion.MC1_7_2 {
                writeString("dripLava")
                writeFloat(200f)
                writeFloat(200f)
                writeFloat(200f)
                writeFloat(0f)
                writeFloat(0f)
                writeFloat(1f)
                writeFloat(0f)
                writeInt(1)
            }
            ProtocolVersion.MC1_8 {
                writeInt(19)
                writeBoolean(false)
                writeFloat(200f)
                writeFloat(200f)
                writeFloat(200f)
                writeFloat(0f)
                writeFloat(0f)
                writeFloat(1f)
                writeFloat(0f)
                writeInt(1)
            }
        }
    }
}
