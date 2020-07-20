package dev.zerite.craftlib.protocol.connection

import dev.zerite.craftlib.chat.component.StringChatComponent
import dev.zerite.craftlib.protocol.util.IFlagged
import io.netty.channel.ServerChannel

/**
 * Controls operations regarding a server which has been bound to
 * a socket.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
open class NettyServer : IFlagged {

    /**
     * Stores flags which the user can assign to the connection allowing for
     * custom data to be easily transferred across instances.
     */
    override val flags = hashMapOf<String, Any>()

    /**
     * Reference to the server channel object backing this.
     */
    var channel: ServerChannel? = null

    /**
     * List of all the current connections to the server.
     */
    val connections = arrayListOf<NettyConnection>()

    /**
     * The current handler which we are using to pass events through to.
     */
    var handler: ServerHandler? = null

    /**
     * Called when the server has fully started.
     *
     * @param  channel        The server channel which we are listening on.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun open(channel: ServerChannel) {
        this.channel = channel
        handler?.bound(this)
    }

    /**
     * Fired when the server accepts a new connection.
     *
     * @param  connection      The client which has just connected.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun connected(connection: NettyConnection) {
        connections.add(connection)
        handler?.connected(this, connection)
    }

    /**
     * Fired when the server closes a connection.
     *
     * @param  connection      The client which has just been disconnected.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun disconnected(connection: NettyConnection) {
        handler?.disconnected(this, connection)
        connections.remove(connection)
    }

    /**
     * Closes the server and all current connections.
     *
     * @param  wait      Whether we should block the thread whilst closing this server.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun close(wait: Boolean = true) {
        assert(channel != null)
        handler?.closed(this)
        connections.forEach { it.close(StringChatComponent("Server closed")) }
        channel?.close()
            ?.addListener { handler?.closed(this) }
            ?.let { if (wait) it.syncUninterruptibly() }
    }
}
