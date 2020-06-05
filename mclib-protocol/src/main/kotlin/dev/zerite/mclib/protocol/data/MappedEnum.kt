package dev.zerite.mclib.protocol.data

/**
 * Utility class for mapping enums by their IDs and providing utility
 * methods to get them.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
interface MappedEnum {

    /**
     * The ID which each value should be associated with and mapped by.
     */
    val id: Int

    /**
     * Base companion object for mapped enums to implement.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    interface Companion<T : Enum<T>>

}

/**
 * Returns the enum value which matches the ID.
 *
 * @param  id          The ID to lookup and reverse search.
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
inline operator fun <reified T> MappedEnum.Companion<T>.get(id: Int): T?
        where T : Enum<T>, T : MappedEnum = enumValues<T>().find { it.id == id }