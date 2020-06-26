package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Used to play a sound effect on the client.
 * Custom sounds may be added by resource packs.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlaySoundEffectTest : PacketTest<ServerPlaySoundEffectPacket>(ServerPlaySoundEffectPacket) {
    init {
        example(ServerPlaySoundEffectPacket("sound", 100, 10, 300, 1f, 1f)) {
            ProtocolVersion.MC1_7_2 {
                writeString("sound")
                writeInt(100)
                writeInt(10)
                writeInt(300)
                writeFloat(1f)
                writeByte(63)
            }
        }
        example(ServerPlaySoundEffectPacket("placeholder", 20, 200, 2000, 0.5f, 0.20634921f)) {
            ProtocolVersion.MC1_7_2 {
                writeString("placeholder")
                writeInt(20)
                writeInt(200)
                writeInt(2000)
                writeFloat(0.5f)
                writeByte(13)
            }
        }
    }
}