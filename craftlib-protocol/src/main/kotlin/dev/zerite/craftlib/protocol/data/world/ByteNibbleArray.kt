package dev.zerite.craftlib.protocol.data.world

/**
 * Stores an array of bytes, with each storing two integer values spanning
 * 4 bits.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ByteNibbleArray(var data: ByteArray, private val desired: Int = 16 * 16 * 8) {

    /**
     * Gets a nibble byte from the array with the given index.
     *
     * @param  index       The index of the nibble byte.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun get(index: Int) = data[index / 2].toInt() ushr (if (index % 2 == 0) 0 else 4) and 0x0F

    /**
     * Gets a nibble byte from the array, otherwise falling back
     * to the provided default.
     *
     * @param  index       The index of the nibble byte to lookup.
     * @param  default     The fallback value if the index is out of bounds.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun get(index: Int, default: Int) = if (index < 0 || (index / 2) >= data.size - 1 || data.isEmpty()) default else this[index]

    /**
     * Sets the data at the given index to the provided value.
     *
     * @param  index       The index of the nibble byte we are changing.
     * @param  value       The value which we are setting.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun set(index: Int, value: Int) {
        if (data.size < desired) data = ByteArray(desired) { data.getOrNull(it) ?: 0 }
        val i = index shr 1
        if (index and 1 == 0) data[i] = (data[i].toInt() and 240 or value and 15).toByte()
        else data[i] = (data[i].toInt() and 15 or (value and 15) shl 4).toByte()
    }

    override fun toString() = data.contentToString()
    override fun hashCode() = data.contentHashCode()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as ByteNibbleArray
        if (!data.contentEquals(other.data)) return false
        return true
    }

}