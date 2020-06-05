package dev.zerite.craftlib.protocol.connection

import dev.zerite.craftlib.chat.component.BaseChatComponent
import dev.zerite.craftlib.protocol.util.Cancellable

@Suppress("UNUSED")
interface PacketHandler {

    /**
     * Called when this packet handler was assigned to a connection.
     *
     * @param  connection    Reference to the connection which was assigned.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun assigned(connection: NettyConnection) {}

    /**
     * Called when a connection has been successfully initiated.
     *
     * @param  connection    Reference to the connection which has been successfully connected.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun connected(connection: NettyConnection) {}

    /**
     * Fired upon a packet being received from a connection.
     *
     * @param  connection    Reference to the connection which the packet originated from.
     * @param  packet        The packet which was received.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun received(connection: NettyConnection, packet: Any) {}

    /**
     * Fired before a packet is sent to the remote connection.
     *
     * @param  connection    Reference to the connection instance.
     * @param  event         Contains the packet data.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun sending(connection: NettyConnection, event: PacketSendingEvent) {}

    /**
     * Fired when a packet has been written and flushed to the server.
     *
     * @param  connection    Reference to the connection this packet was sent from.
     * @param  packet        The packet which we sent.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun sent(connection: NettyConnection, packet: Any) {}

    /**
     * Called when this connection has disconnected from the host.
     *
     * @param  connection    Reference to the connection which was disconnected.
     * @param  reason        The reason for disconnecting.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun disconnected(connection: NettyConnection, reason: BaseChatComponent) {}

    /**
     * Fired when this connection encounters an error.
     *
     * @param  connection    Reference to the connection which encountered an error.
     * @param  cause         The exception which was thrown.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun exception(connection: NettyConnection, cause: Throwable) {}

}

/**
 * Allows for the packet to be changed as well as the event
 * being cancelled.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class PacketSendingEvent(val connection: NettyConnection, var packet: Any) : Cancellable()