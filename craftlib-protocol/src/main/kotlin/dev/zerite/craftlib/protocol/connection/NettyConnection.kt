package dev.zerite.craftlib.protocol.connection

import dev.zerite.craftlib.chat.component.BaseChatComponent
import dev.zerite.craftlib.chat.component.StringChatComponent
import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.connection.io.CompressionCodec
import dev.zerite.craftlib.protocol.connection.io.EncryptionCodec
import dev.zerite.craftlib.protocol.util.IFlagged
import dev.zerite.craftlib.protocol.version.MinecraftProtocol
import dev.zerite.craftlib.protocol.version.PacketDirection
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import io.netty.channel.Channel
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPipeline
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.util.AttributeKey
import javax.crypto.SecretKey

/**
 * Instance of a connection to a remote server using Netty.
 * The direction refers to which direction packets will be encoded in.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("UNUSED")
open class NettyConnection(val direction: PacketDirection) : SimpleChannelInboundHandler<Packet>(), IFlagged {

    companion object {
        /**
         * The key which references a connection.
         */
        val attribute: AttributeKey<NettyConnection> = AttributeKey.valueOf("connection")
    }

    /**
     * Stores flags which the user can assign to the connection allowing for
     * custom data to be easily transferred across instances.
     */
    override val flags = hashMapOf<String, Any>()

    /**
     * True when this connection has been terminated.
     */
    @Suppress("UNUSED")
    var disconnected = false
        private set

    /**
     * Reference to the current channel.
     */
    @Suppress("UNUSED")
    lateinit var channel: Channel

    /**
     * The current packet handler which should process any major events
     * in this connection.
     */
    @Suppress("UNUSED")
    var handler: PacketHandler? = null
        set(value) {
            value?.assigned(this)
            field = value
        }

    /**
     * The current protocol state for this connection.
     */
    var state = MinecraftProtocol.HANDSHAKE

    /**
     * The current protocol version for this connection.
     * Used for encoding and decoding packets.
     */
    var version = ProtocolVersion.UNKNOWN

    /**
     * If applicable, the server which this connection is associated with.
     */
    var server: NettyServer? = null

    /**
     * The threshold which indicates at which point packets will be compressed.
     */
    var compressionThreshold: Int = -1
        set(value) {
            field = value

            channel.pipeline().apply {
                if (value >= 0) (get("compression") as? CompressionCodec)?.let { it.threshold = value }
                    ?: run { addBefore("packet", "compression", CompressionCodec(this@NettyConnection, value)) }
                else remove("compression")
            }
        }

    /**
     * Send a packet to the remote connection and optionally listen
     * for when it was sent.
     *
     * TODO: Add packet queue / bulk writes
     *
     * @param  packet      The packet we're sending.
     * @param  listener    Callback function for when the packet was sent.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun <T : Packet> send(packet: T, listener: T.() -> Unit = {}) =
        PacketSendingEvent(this, packet).let {
            handler?.sending(this, it)
            if (it.cancelled) return@let null
            if (!this::channel.isInitialized) return@let null

            channel.writeAndFlush(it.packet)
                ?.addListener { _ ->
                    handler?.sent(this, it.packet)
                    @Suppress("UNCHECKED_CAST")
                    listener(it.packet as T)
                }
        }

    /**
     * Enables encryption in the pipeline and will begin encrypting and
     * decrypting packets from now on.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @Suppress("UNUSED")
    fun enableEncryption(secret: SecretKey): ChannelPipeline =
        channel.pipeline().addBefore("length", "crypto", EncryptionCodec(this, secret))

    /**
     * Closes the connection and disconnects from the host.
     *
     * @param  reason     The reason for closing this connection.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @Suppress("UNUSED")
    fun close(reason: BaseChatComponent) =
        channel
            .takeIf { !disconnected }
            ?.apply { disconnected = true }
            ?.close()
            ?.addListener { handler?.disconnected(this, reason) }

    /**
     * Fired when the channel becomes active and begins connecting.
     *
     * @param  ctx        The channel context.
     * @author Netty
     */
    override fun channelActive(ctx: ChannelHandlerContext) {
        channel = ctx.channel()
        channel.attr(attribute).set(this)
        server?.connected(this)
        handler?.connected(this)
    }

    /**
     * Fired when the channel becomes inactive after a disconnect.
     *
     * @param  ctx     The context for this channel.
     * @author Netty
     */
    override fun channelInactive(ctx: ChannelHandlerContext) {
        // Run the handler
        close(StringChatComponent("Disconnected"))
        server?.disconnected(this)
    }

    /**
     * Handles reading messages from the channel.
     *
     * @param  ctx     The context for this channel.
     * @param  packet  The packet which was read.
     * @author Netty
     */
    override fun channelRead0(ctx: ChannelHandlerContext, packet: Packet) {
        handler?.received(this, packet)
    }

    /**
     * Fired when an exception is encountered in this connection.
     *
     * @param  ctx       The context for this channel.
     * @param  cause     The cause for this exception.
     * @author Netty
     */
    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        handler?.exception(this, cause)
    }
}

/**
 * Gets the connection reference from this channel handler context.
 */
val ChannelHandlerContext.connection: NettyConnection?
    get() = channel().attr(NettyConnection.attribute).get()
