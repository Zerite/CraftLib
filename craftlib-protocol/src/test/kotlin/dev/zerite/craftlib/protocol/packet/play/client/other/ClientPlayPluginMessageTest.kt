package dev.zerite.craftlib.protocol.packet.play.client.other

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Mods and plugins can use this to send their data.
 * Minecraft itself uses a number of plugin channels.
 * These internal channels are prefixed with MC|.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ClientPlayPluginMessageTest : PacketTest<ClientPlayPluginMessagePacket>(ClientPlayPluginMessagePacket) {
    init {
        example(ClientPlayPluginMessagePacket("MC|Example", ByteArray(0))) {
            ProtocolVersion.MC1_7_2 {
                writeString("MC|Example")
                writeShort(0)
            }
        }
        example(ClientPlayPluginMessagePacket("MC|Brand", "Vanilla".toByteArray())) {
            ProtocolVersion.MC1_7_2 {
                writeString("MC|Brand")
                writeShort("Vanilla".toByteArray().size)
                writeBytes("Vanilla".toByteArray())
            }
        }
    }
}
