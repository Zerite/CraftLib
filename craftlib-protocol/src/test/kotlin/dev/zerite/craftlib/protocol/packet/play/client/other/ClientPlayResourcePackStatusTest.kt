package dev.zerite.craftlib.protocol.packet.play.client.other

import dev.zerite.craftlib.protocol.data.registry.impl.MagicResourcePackResult
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the client to tell the server the status of loading the
 * resource pack.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
class ClientPlayResourcePackStatusTest :
    PacketTest<ClientPlayResourcePackStatusPacket>(ClientPlayResourcePackStatusPacket) {
    init {
        example(ClientPlayResourcePackStatusPacket("hash", MagicResourcePackResult.ACCEPTED)) {
            ProtocolVersion.MC1_8 {
                writeString("hash")
                writeVarInt(3)
            }
        }
        example(ClientPlayResourcePackStatusPacket("exampleHash", MagicResourcePackResult.LOADED)) {
            ProtocolVersion.MC1_8 {
                writeString("exampleHash")
                writeVarInt(0)
            }
        }
    }
}
