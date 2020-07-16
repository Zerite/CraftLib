package dev.zerite.craftlib.protocol.version

/**
 * Utility to allow for different protocol mappings to be created that
 * mimic the Minecraft protocol.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
abstract class AbstractProtocol {

    /**
     * The mapped protocol states for this object.
     */
    private val mapped = hashMapOf<Int, ProtocolState>()

    /**
     * Gets a protocol state given its ID.
     *
     * @param  id        The protocol's ID.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun get(id: Int) = mapped[id] ?: error("Unknown connection state $id")

    /**
     * Builds a protocol state given the parameters.
     *
     * @param  name      The name of this protocol.
     * @param  id        The handshake ID.
     * @param  block     Builder function.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    protected fun protocol(name: String, id: Int, block: ProtocolState.() -> Unit) =
        ProtocolState(name, id).apply(block).apply { mapped[id] = this }

}
