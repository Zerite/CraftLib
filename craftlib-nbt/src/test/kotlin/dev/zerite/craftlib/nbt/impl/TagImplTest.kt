package dev.zerite.craftlib.nbt.impl

import dev.zerite.craftlib.nbt.NBTTag
import dev.zerite.craftlib.nbt.TagIO
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream

/**
 * Base class for all tag tests.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
abstract class TagImplTest<T : NBTTag> {
    protected fun TagIO<T>.read(bytes: ByteArray) = DataInputStream(ByteArrayInputStream(bytes)).use {
        read(it)
    }

    protected fun T.write(): ByteArray =
        ByteArrayOutputStream().also { DataOutputStream(it).use { out -> write(out) } }.toByteArray()
}
