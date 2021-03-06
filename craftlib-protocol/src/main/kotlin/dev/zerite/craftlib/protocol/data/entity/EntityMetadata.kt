package dev.zerite.craftlib.protocol.data.entity

import dev.zerite.craftlib.protocol.Slot
import dev.zerite.craftlib.protocol.Vector3

/**
 * Stores metadata values about an entity.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class EntityMetadata @JvmOverloads constructor(private val values: HashMap<Int, MetadataValue<out Any>> = hashMapOf()) {
    /**
     * Gets a list of the entries in the values map.
     */
    val entries: Array<Pair<Int, MetadataValue<out Any>>>
        get() = values.entries.map { (key, value) -> key to value }.toTypedArray()

    /**
     * Gets a value from the metadata.
     *
     * @param  key           The name of the metadata value.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    inline operator fun <reified T : MetadataValue<out Any>> get(key: Int) =
        get(key, T::class.java)

    /**
     * Gets a value from the metadata.
     *
     * @param  key           The name of the metadata value.
     * @param  type          The class of the value we're looking for.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun <T : MetadataValue<out Any>> get(key: Int, type: Class<T>): T? =
        try {
            type.cast(values[key])
        } catch (e: Throwable) {
            null
        }

    /**
     * Adds a value to the metadata and returns a new instance.
     *
     * @param  value         The value to add to this metadata.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun plus(value: MetadataValue<out Any>) = EntityMetadata(HashMap(values).apply { this[value.id] = value })

    /**
     * Adds a value to the metadata.
     *
     * @param  value         The value to add to this metadata.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun plusAssign(value: MetadataValue<out Any>) {
        values[value.id] = value
    }

    /**
     * Removes a value from the list.
     *
     * @param  value       The key to remove.
     * @author Koding
     * @since  0.1.1-SNAPSHOT
     */
    operator fun minusAssign(value: Int) {
        values.remove(value)
    }
}

/**
 * Single value of metadata.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class MetadataValue<T>(val id: Int, var value: T) {
    /**
     * Returns the integer value of the type.
     */
    @JvmField
    val type = when (value) {
        is Byte -> 0
        is Short -> 1
        is Int -> 2
        is Float -> 3
        is String -> 4
        is Slot -> 5
        is Vector3 -> 6
        is RotationData -> 7
        else -> null
    }
}

/**
 * Stores rotation information for entity metadata.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
data class RotationData(
    var pitch: Float,
    var yaw: Float,
    var roll: Float
)
