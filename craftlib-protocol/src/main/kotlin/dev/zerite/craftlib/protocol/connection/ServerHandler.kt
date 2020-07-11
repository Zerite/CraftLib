package dev.zerite.craftlib.protocol.connection

/**
 * Controls key events regarding the server's state.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("UNUSED")
interface ServerHandler {

    /**
     * Fired when this server has been successfully bound to the socket
     * and is accepting connections.
     *
     * @param  server     The server reference.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun bound(server: NettyServer) {}

    /**
     * Fired upon the server quitting.
     *
     * @param  server      Reference to the backing server object.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun closing(server: NettyServer) {}

    /**
     * Fired when the server has fully closed.
     *
     * @param  server      Reference to the backing server object.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun closed(server: NettyServer) {}

    /**
     * Fired when the server has accepted a new client connection.
     *
     * @param  server     Reference to the server object which has been connected to.
     * @param  client     The connection which has been initiated.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun connected(server: NettyServer, client: NettyConnection) {}

    /**
     * Fired when the server closes an existing client connection.
     *
     * @param  server     Reference to the server object which has been quit from.
     * @param  client     The connection which has been closed.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun disconnected(server: NettyServer, client: NettyConnection) {}

}
