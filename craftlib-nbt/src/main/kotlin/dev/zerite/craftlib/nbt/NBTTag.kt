package dev.zerite.craftlib.nbt

import java.io.DataInput
import java.io.DataOutput

/**
 * Allows for tags to be read from a data input.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
interface TagIO<T : NBTTag> {
    /**
     * Reads a tag's data from the input and returns the
     * parsed data.
     *
     * @param  input      The data input which we are reading from.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun read(input: DataInput): T
}

/**
 * Parent for all NBT tags to implement.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
interface NBTTag {
    /**
     * The ID of this tag's type.
     */
    val id: Int

    /**
     * Writes the tag's data into the output.
     *
     * @param  output      The output we are writing the tag to.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun write(output: DataOutput)
}
