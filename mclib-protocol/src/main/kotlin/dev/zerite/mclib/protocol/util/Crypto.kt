package dev.zerite.mclib.protocol.util

import java.security.Key
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

/**
 * Utility which will handle all the major cryptography operations, such as
 * generating key-pairs and creating ciphers.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
object Crypto {

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