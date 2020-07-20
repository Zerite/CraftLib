package dev.zerite.craftlib.protocol.util

import kotlin.reflect.KClass
import kotlin.reflect.safeCast

/**
 * Simple utility class to allow for flags to be assigned
 * to any object.
 *
 * @author Koding
 * @since  0.1.2
 */
interface IFlagged {

    /**
     * Stores flags which the user can assign to the connection allowing for
     * custom data to be easily transferred across instances.
     */
    val flags: HashMap<String, Any>

    /**
     * Removes a flag in this connection.
     *
     * @param  key        The key to insert into the flags.
     * @author Koding
     * @since  0.1.2
     */
    operator fun minusAssign(key: String) {
        flags.remove(key)
    }

    /**
     * Sets a flag in this connection.
     *
     * @param  key        The key to insert into the flags.
     * @param  value      The flag value.
     *
     * @author Koding
     * @since  0.1.2
     */
    operator fun set(key: String, value: Any) {
        flags[key] = value
    }

    /**
     * Gets a flag from the connection with the given type.
     *
     * @param  key         The key for the flags map.
     * @param  type        The type of expected value which we are reading.
     *
     * @author Koding
     * @since  0.1.2
     */
    @OptIn(ExperimentalStdlibApi::class)
    operator fun <T : Any> get(key: String, type: KClass<T>) =
        type.safeCast(flags[key])

}

/**
 * Gets a flag from the connection with the given type.
 *
 * @param  key         The key for the map which are retrieving from.
 * @author Koding
 * @since  0.1.2
 */
inline operator fun <reified T : Any> IFlagged.get(key: String) = get(key, T::class)
