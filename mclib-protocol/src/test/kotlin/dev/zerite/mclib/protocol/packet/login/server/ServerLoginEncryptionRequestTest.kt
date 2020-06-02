package dev.zerite.mclib.protocol.packet.login.server

import dev.zerite.mclib.protocol.packet.PacketTest
import dev.zerite.mclib.protocol.util.Crypto
import dev.zerite.mclib.protocol.version.ProtocolVersion
import kotlin.random.Random

/**
 * Tests the login encryption request packet from the server.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerLoginEncryptionRequestTest :
    PacketTest<ServerLoginEncryptionRequestPacket>(ServerLoginEncryptionRequestPacket) {

    init {
        val pair = Crypto.newKeyPair()
        val secret = Random.nextBytes(4)

        example(ServerLoginEncryptionRequestPacket("example", pair.public, secret)) {
            ProtocolVersion.MC1_7_2 {
                writeString("example")
                writeByteArray(pair.public.encoded) { writeShort(it) }
                writeByteArray(secret) { writeShort(it) }
            }
        }
    }

}