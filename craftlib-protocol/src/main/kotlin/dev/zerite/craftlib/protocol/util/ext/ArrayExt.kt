package dev.zerite.craftlib.protocol.util.ext

import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.util.zip.Deflater
import java.util.zip.Inflater

/**
 * Creates a data output using the given block and returns a pair of
 * the resulting byte output stream and block.
 *
 * @param  block       The block to use in the data output stream.
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("UNUSED")
fun <T> dataOutput(block: DataOutputStream.() -> T) =
    ByteArrayOutputStream().use {
        it to DataOutputStream(it).use(block)
    }

/**
 * Creates a data input using the given block and returns the output
 * of the block.
 *
 * @param  block       The block to use in the data input stream.
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("UNUSED")
fun <T> ByteArray.dataInput(block: DataInputStream.() -> T) =
    inputStream().use { DataInputStream(it).use(block) }

/**
 * Deflates the byte array and returns it.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
fun ByteArray.deflated() =
    ByteArray(size).apply bytes@{
        Deflater(-1).apply {
            setInput(this@deflated, 0, size)
            finish()
            deflate(this@bytes)
            end()
        }
    }

/**
 * Inflates the byte array and returns it.
 *
 * @param  size         The size of the resulting byte array.
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
fun ByteArray.inflated(size: Int) =
    ByteArray(size).also {
        Inflater().apply { setInput(this@inflated, 0, this@inflated.size) }.inflate(it)
    }

/**
 * Trims a byte array to size given the provided max length.
 *
 * @param  length      The maximum length of the byte array.
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
fun ByteArray.trim(length: Int) =
    ByteArray(length).also {
        System.arraycopy(this, 0, it, 0, length)
    }
