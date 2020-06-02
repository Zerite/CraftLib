package dev.zerite.mclib.protocol.util

import java.security.*
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Utility which will handle all the major cryptography operations, such as
 * generating key-pairs and creating ciphers.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
object Crypto {

    /**
     * Generates a new key pair using a size of 1024 bits.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun newKeyPair(): KeyPair = KeyPairGenerator.getInstance("RSA")
        .apply { initialize(1024) }
        .genKeyPair()

    /**
     * Generates a new secret key using a size of 128 bits.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun newSecretKey(): SecretKey = KeyGenerator.getInstance("AES")
        .apply { init(128) }
        .generateKey()

    /**
     * Encrypts the given byte array with the provided key.
     *
     * @param  key       The key to encrypt the data with.
     * @param  data      The data to encrypt.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun encrypt(key: Key, data: ByteArray): ByteArray = data[Cipher.ENCRYPT_MODE, key]

    /**
     * Decrypts the given byte array with the provided key.
     *
     * @param  key       The key to decrypt the data with.
     * @param  data      The data to decrypt.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun decrypt(key: Key, data: ByteArray): ByteArray = data[Cipher.DECRYPT_MODE, key]

    /**
     * Processes this byte array and either encrypts or decrypts
     * it with the given key.
     *
     * @param  opcode     The operation to use for this cipher.
     * @param  key        The key to use when initializing the cipher.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    private operator fun ByteArray.get(opcode: Int, key: Key) =
        Cipher.getInstance(key.algorithm)
            .apply { init(opcode, key) }
            .doFinal(this)

    /**
     * Creates a cipher with the given opcode and key.
     *
     * @param  opcode      The opcode to use for this cipher.
     * @param  key         The key to use to initialize the cipher.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun get(opcode: Int, key: Key): Cipher =
        Cipher.getInstance("AES/CFB8/NoPadding")
            .apply { init(opcode, key, IvParameterSpec(key.encoded)) }

}

/**
 * Converts the byte array into a public key.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
fun ByteArray.asPublicKey(): PublicKey = KeyFactory.getInstance("RSA")
    .generatePublic(X509EncodedKeySpec(this))

/**
 * Converts the byte array into a secret key.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
fun ByteArray.asSecretKey(key: PrivateKey) = SecretKeySpec(Crypto.decrypt(key, this), "AES")