package dev.zerite.craftlib.protocol.packet.login.client

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.util.Crypto
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import org.junit.Assert
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests the login encryption response packet IO operations.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ClientLoginEncryptionResponseTest :
    PacketTest<ClientLoginEncryptionResponsePacket>(ClientLoginEncryptionResponsePacket) {

    private val pair = Crypto.newKeyPair()
    private val secret = Crypto.newSecretKey()
    private val verify = Random.nextBytes(4)
    private val packet: ClientLoginEncryptionResponsePacket

    init {
        val encodedSecret = Crypto.encrypt(pair.public, secret.encoded)
        val encodedVerify = Crypto.encrypt(pair.public, verify)
        packet = ClientLoginEncryptionResponsePacket(encodedSecret, encodedVerify)

        example(packet) {
            ProtocolVersion.MC1_7_2 {
                writeByteArray(encodedSecret) { writeShort(it) }
                writeByteArray(encodedVerify) { writeShort(it) }
            }
            ProtocolVersion.MC1_8 {
                writeByteArray(encodedSecret) { writeVarInt(it) }
                writeByteArray(encodedVerify) { writeVarInt(it) }
            }
        }
    }

    @Test
    fun `Test Decrypted Packets Match`() {
        assertEquals(secret, packet.getSecretKey(pair.private))
        Assert.assertArrayEquals(verify, packet.getVerifyToken(pair.private))
    }

}
