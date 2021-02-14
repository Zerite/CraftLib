package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.data.registry.impl.MagicSoundCategory
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Used to play a sound effect on the client.
 * Custom sounds may be added by resource packs.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayNamedSoundEffectTest : PacketTest<ServerPlayNamedSoundEffectPacket>(ServerPlayNamedSoundEffectPacket) {
    init {
        example(ServerPlayNamedSoundEffectPacket("sound", MagicSoundCategory.MASTER, 100, 10, 300, 1f, 1f)) {
            ProtocolVersion.MC1_7_2 {
                writeString("sound")
                writeInt(100)
                writeInt(10)
                writeInt(300)
                writeFloat(1f)
                writeByte(63)
            }
            ProtocolVersion.MC1_9 {
                writeString("sound")
                writeVarInt(0)
                writeInt(100)
                writeInt(10)
                writeInt(300)
                writeFloat(1f)
                writeByte(63)
            }
        }
        example(
            ServerPlayNamedSoundEffectPacket(
                "placeholder",
                MagicSoundCategory.MASTER,
                20,
                200,
                2000,
                0.5f,
                0.20634921f
            ),
            maximumVersion = ProtocolVersion.MC1_8
        ) {
            ProtocolVersion.MC1_7_2 {
                writeString("placeholder")
                writeInt(20)
                writeInt(200)
                writeInt(2000)
                writeFloat(0.5f)
                writeByte(13)
            }
        }
        example(
            ServerPlayNamedSoundEffectPacket(
                "placeholder",
                MagicSoundCategory.MUSIC,
                5632,
                321,
                7345,
                0.4f,
                0.20634921f
            ),
            minimumVersion = ProtocolVersion.MC1_9
        ) {
            ProtocolVersion.MC1_9 {
                writeString("placeholder")
                writeVarInt(1)
                writeInt(5632)
                writeInt(321)
                writeInt(7345)
                writeFloat(0.4f)
                writeByte(13)
            }
        }
    }
}