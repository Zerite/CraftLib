package dev.zerite.craftlib.protocol.version

import dev.zerite.craftlib.protocol.PacketIO

/**
 * Stores details about packet mappings for a specific connection state.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ProtocolState(val name: String, val id: Any) {

    /**
     * Side data for each packet direction.
     */
    val clientbound = SideData(PacketDirection.CLIENTBOUND)
    val serverbound = SideData(PacketDirection.SERVERBOUND)

    /**
     * Provides the side data for a specific direction.
     *
     * @param  direction   The packet direction.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun get(direction: PacketDirection) =
        if (direction == PacketDirection.CLIENTBOUND) clientbound
        else serverbound

    /**
     * Custom implementation of toString to display a
     * connection state.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    override fun toString() = "$name ($id)"

    /**
     * Simple utility to easily run code inside this class using
     * the apply block.
     *
     * @param  build        The code block to run.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun invoke(build: ProtocolState.() -> Unit) = apply(build)

    /**
     * Contains the individual data for each direction.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    data class SideData(private val direction: PacketDirection) {

        companion object {
            /**
             * Executes a handler for all the supported protocol versions.
             *
             * @param  ids       The list of protocol versions mapped to the packet ID.
             * @param  handler   The handler which should be called for every version.
             *
             * @author Koding
             * @since  0.1.0-SNAPSHOT
             */
            fun <T> runForAllProtocols(
                ids: Array<Pair<ProtocolVersion, T>>,
                handler: (ProtocolVersion, T) -> Unit
            ) = ids.takeIf { it.isNotEmpty() }
                ?.let {
                    var idIndex = 0
                    var id = ids[idIndex]

                    for (protocol in ProtocolVersion.values()) {
                        if (protocol < id.first) continue

                        if (id.first < protocol && idIndex + 1 < it.size) {
                            val next = it[idIndex + 1]

                            if (next.first == protocol) {
                                id = next
                                idIndex++
                            }
                        }

                        handler(protocol, id.second)
                    }
                }
        }

        /**
         * Contains mappings to convert from and to the packet IO handler.
         */
        private val classToData = hashMapOf<ProtocolVersion, MutableMap<Class<*>, PacketData>>()
        private val idToData = hashMapOf<ProtocolVersion, MutableMap<Int, PacketData>>()

        /**
         * Get packet data from the ID specified.
         *
         * @param  version  The protocol version we're reading from.
         * @param  id       The ID of the packet to lookup.
         *
         * @author Koding
         * @since  0.1.0-SNAPSHOT
         */
        operator fun get(version: ProtocolVersion, id: Int) =
            idToData[version]?.get(id)

        /**
         * Get packet data from the packet specified.
         *
         * @param  version  The protocol version we're reading from.
         * @param  packet   The packet class we're reading from.
         *
         * @author Koding
         * @since  0.1.0-SNAPSHOT
         */
        operator fun get(version: ProtocolVersion, packet: Any) =
            classToData[version]?.get(packet::class.java)

        /**
         * Allows for DSL to be used on the raw side data values.
         *
         * @param  build       The code to run inside the apply block.
         * @author Koding
         * @since  0.1.0-SNAPSHOT
         */
        @ProtocolStateDSL
        operator fun invoke(build: SideData.() -> Unit) = apply(build)

        /**
         * Registers all the packet IDs in the provided builder block.
         *
         * @param  block     The builder for mapping IDs.
         * @author Koding
         * @since  0.1.0-SNAPSHOT
         */
        @ProtocolStateDSL
        operator fun PacketIO<*>.invoke(block: IdListBuilder.() -> Unit) {
            val packetType = type
            runForAllProtocols(IdListBuilder().apply(block).ids.toTypedArray()) { version, id ->
                val data = PacketData(id, this)
                classToData.getOrPut(version) { hashMapOf() }[packetType] = data
                idToData.getOrPut(version) { hashMapOf() }[id] = data
            }
        }


        /**
         * Stores information about a packet's data, including the ID
         * and IO class to handle operations.
         *
         * @author Koding
         * @since  0.1.0-SNAPSHOT
         */
        data class PacketData(val id: Int, val io: PacketIO<*>)

        /**
         * Builder class for the ID mapping DSL.
         *
         * @author Koding
         * @since  0.1.0-SNAPSHOT
         */
        data class IdListBuilder(
            val ids: MutableList<Pair<ProtocolVersion, Int>> = arrayListOf()
        ) {
            /**
             * Maps a protocol version to the packet ID.
             *
             * @param  id       The version's packet ID.
             * @author Koding
             * @since  0.1.0-SNAPSHOT
             */
            @ProtocolStateDSL
            infix fun ProtocolVersion.to(id: Int) = ids.add(Pair(this, id))
        }
    }

}

/**
 * Marker for the protocol state DSL.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@DslMarker
annotation class ProtocolStateDSL

/**
 * Stores the direction in which a packet is being sent to.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
enum class PacketDirection(private val value: String) {
    CLIENTBOUND("S->C"),
    SERVERBOUND("C->S");

    /**
     * Flips the packet direction.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun invert() = if (this == CLIENTBOUND) SERVERBOUND else CLIENTBOUND

    /**
     * Provides the display name as the string.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    override fun toString() = value
}
