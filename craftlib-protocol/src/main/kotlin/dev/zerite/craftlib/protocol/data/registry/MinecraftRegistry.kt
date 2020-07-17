package dev.zerite.craftlib.protocol.data.registry

import dev.zerite.craftlib.protocol.version.ProtocolState
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import kotlin.reflect.KClass
import kotlin.reflect.safeCast

/**
 * Stores information about enum mappings across versions, allowing for enum
 * values to adapt to each version.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class MinecraftRegistry<T : RegistryEntry> : IMinecraftRegistry<T> {
    /**
     * Stores the version values for each enums mappings.
     */
    private val rawVersions = hashMapOf<ProtocolVersion, HashMap<T, Any>>()
    private val versions = hashMapOf<ProtocolVersion, Map<Any, T>>()
    private val versionsInt = hashMapOf<ProtocolVersion, Map<T, Any>>()

    /**
     * Creates an enum builder for the provided protocol version for
     * mapping enums.
     *
     * @param  build          Builder function to create the enum mappings.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun ProtocolVersion.invoke(build: VersionEntryBuilder<T>.() -> Unit) {
        rawVersions.computeIfAbsent(this) { hashMapOf() }
        rawVersions[this]?.putAll(VersionEntryBuilder<T>().apply(build).mappings)
    }

    /**
     * Rebuilds the version data for all protocols.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun rebuild() {
        val values = hashMapOf<T, Any>()
        ProtocolState.SideData.runForAllProtocols(
            rawVersions.map { it.key to it.value }.sortedBy { it.first }.toTypedArray()
        ) { version, map ->
            values.putAll(map)
            versions[version] = values.map { it.value to it.key }.toMap()
            versionsInt[version] = values.toMap()
        }
    }

    /**
     * Gets the magic value for the provided key at the
     * specified version.
     *
     * @param  version         The protocol version to lookup.
     * @param  key             The key of this magic from the protocol.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    private fun getEntry(version: ProtocolVersion, key: Any?) =
        key?.let { versions[version]?.get(it) } ?: UnknownRegistryEntry(key.toString())

    /**
     * Gets the magic value for the provided string value.
     *
     * @param  version         The protocol version to lookup.
     * @param  key             The string key of this magic from the protocol.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    override fun get(version: ProtocolVersion, key: String) = getEntry(version, key)

    /**
     * Gets the magic value for the provided integer value.
     *
     * @param  version         The protocol version to lookup.
     * @param  key             The integer key of this magic from the protocol.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    override fun get(version: ProtocolVersion, key: Int) = getEntry(version, key)

    /**
     * Gets the magic value for the provided enum at the
     * specified version.
     *
     * @param  version         The protocol version to lookup.
     * @param  magic           The magic value from packets.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    override operator fun get(version: ProtocolVersion, magic: RegistryEntry) =
        if (magic is UnknownRegistryEntry<*>) magic.raw
        else versionsInt[version]?.get(magic) ?: UnknownRegistryEntry(-1)

    /**
     * Gets the magic value for the provided enum at the
     * specified version and casts it to the provided generic.
     *
     * @param  version         The protocol version to lookup.
     * @param  magic           The magic value from packets.
     * @param  type            The generic's class type.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @ExperimentalStdlibApi
    override fun <T : Any> get(version: ProtocolVersion, magic: RegistryEntry, type: KClass<T>) =
        type.safeCast(this[version, magic])
}

/**
 * Builder class for mapping enum indexes to their enum values.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class VersionEntryBuilder<T : RegistryEntry> {
    /**
     * The mappings for this enum to use.
     */
    val mappings = hashMapOf<T, Any>()

    /**
     * Maps the enum value to the index provided.
     *
     * @param  value       The index key this entry should be associated with.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    infix fun T.to(value: Any) {
        mappings[this] = value
    }
}

/**
 * Base for the Minecraft enum class to inherit, allowing for
 * property delegation in the enum companion objects.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
interface IMinecraftRegistry<T : RegistryEntry> {
    operator fun get(version: ProtocolVersion, key: String): RegistryEntry
    operator fun get(version: ProtocolVersion, key: Int): RegistryEntry
    operator fun get(version: ProtocolVersion, magic: RegistryEntry): Any
    operator fun <T : Any> get(version: ProtocolVersion, magic: RegistryEntry, type: KClass<T>): T?
}

/**
 * Allows for a registry to be lazy loaded and then delegated to a companion object.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class LazyRegistryDelegate<T : RegistryEntry>(get: () -> IMinecraftRegistry<T>) : IMinecraftRegistry<T> {
    /**
     * The lazy value for the registry.
     */
    private val entry by lazy(get)

    override fun get(version: ProtocolVersion, key: String) = entry[version, key]
    override fun get(version: ProtocolVersion, key: Int) = entry[version, key]
    override fun get(version: ProtocolVersion, magic: RegistryEntry) = entry[version, magic]
    override fun <T : Any> get(version: ProtocolVersion, magic: RegistryEntry, type: KClass<T>) =
        entry[version, magic, type]
}
