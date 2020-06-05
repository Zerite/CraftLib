package dev.zerite.craftlib.protocol.packet.play.server.other

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Tests that plugin messages are being read and written correctly across versions.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayPluginMessageTest : PacketTest<ServerPlayPluginMessagePacket>(
    ServerPlayPluginMessagePacket
) {

    init {
        example(
            ServerPlayPluginMessagePacket(
                "example",
                byteArrayOf()
            )
        )
        example(
            ServerPlayPluginMessagePacket(
                "minecraft:register",
                byteArrayOf(4, 8, 12)
            )
        )
        example(
            ServerPlayPluginMessagePacket(
                "minecraft:unregister",
                byteArrayOf(21, 69, 42)
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeString("minecraft:unregister")
                writeByteArray(byteArrayOf(21, 69, 42)) { writeShort(it) }
            }
        }
    }

}