package dev.zerite.mclib.protocol.version

import dev.zerite.mclib.protocol.connection.NettyConnection
import dev.zerite.mclib.protocol.connection.PacketHandler
import dev.zerite.mclib.protocol.connection.io.LengthCodec
import dev.zerite.mclib.protocol.connection.io.PacketCodec
import dev.zerite.mclib.protocol.packet.handshake.client.ClientHandshakePacket
import dev.zerite.mclib.protocol.packet.login.client.ClientLoginStartPacket
import io.netty.bootstrap.Bootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.epoll.Epoll
import io.netty.channel.epoll.EpollEventLoopGroup
import io.netty.channel.epoll.EpollSocketChannel
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.timeout.ReadTimeoutHandler
import java.net.InetAddress

/**
 * Contains the mappings for packet identifiers.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
object MinecraftProtocol {

    /**
     * The mapped protocol states for this object.
     */
    private val mapped = hashMapOf<Int, ProtocolState>()

    /**
     * The initial state for all new connections, only listening
     * for a packet from the client.
     */
    val HANDSHAKE = protocol("Handshake", -1) {
        serverbound {
            ClientHandshakePacket {
                ProtocolVersion.MC1_7_2 to 0x00
            }
        }
    }

    /**
     * State for when the player is successfully authenticated and should
     * be receiving game updates.
     */
    val INGAME = protocol("In-game", 0) {
        serverbound {}
        clientbound {}
    }

    /**
     * Handles packets relating to providing server list info.
     */
    val STATUS = protocol("Status", 1) {
        serverbound {}
        clientbound {}
    }

    /**
     * First state after handshake to begin authenticating with the server and
     * start play.
     */
    val LOGIN = protocol("Login", 2) {
        serverbound {
            ClientLoginStartPacket {
                ProtocolVersion.MC1_7_2 to 0x00
            }
        }
    }

    /**
     * Connect to a socket with the given address and port.
     *
     * @param  address     The address to connect to.
     * @param  port        The port of the remote address.
     * @param  build       Builds the config values.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun connect(address: InetAddress, port: Int, build: ConnectConfig.() -> Unit = {}): NettyConnection {
        // Create the config
        val config = ConnectConfig(address, port).apply(build)

        // Create a connection
        val connection = config.connectionFactory().apply { handler = config.handler }

        // Create the bootstrap
        Bootstrap()
            .group(config.eventLoopGroup)
            .channel(config.channel)
            .handler(object : ChannelInitializer<SocketChannel>() {
                /**
                 * Initialize the channel by registering the pipeline.
                 *
                 * @param  ch      The channel being initialized.
                 * @author Koding
                 * @since  0.1.0-SNAPSHOT
                 */
                override fun initChannel(ch: SocketChannel) {
                    // Build the additional logic
                    config.build(ch)

                    // Set TCP No Delay
                    ch.config().isTcpNoDelay = config.noDelay

                    // Check if there's a timeout
                    config.timeout.takeIf { it > 0 }
                        ?.let { ch.pipeline().addLast("readTimeout", ReadTimeoutHandler(it)) }

                    // Add to the pipeline
                    ch.pipeline()
                        .addLast("length", LengthCodec(connection))
                        .addLast("packet", PacketCodec(connection))
                        .apply { if (config.debug) addLast("logger", LoggingHandler(LogLevel.INFO)) }
                        .addLast("connection", connection)
                }
            })
            .connect(config.address, config.port)
            .let { if (config.connectSync) it.syncUninterruptibly() }

        // Return the connection
        return connection
    }

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
    private fun protocol(name: String, id: Int, block: ProtocolState.() -> Unit) =
        ProtocolState(name, id).apply(block).apply { mapped[id] = this }

    /**
     * Configuration for a connection being built.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    data class ConnectConfig(var address: InetAddress, var port: Int) {
        /**
         * Defines the initial packet handler for the connection.
         */
        var handler: PacketHandler? = null

        /**
         * Default timeout for inactivity, causing termination of the pipeline.
         */
        var timeout = 30

        /**
         * Whether we should block the current thread of the connection
         * whilst it is being initialized.
         */
        var connectSync = true

        /**
         * Set the TCP No Delay value in the builder.
         */
        var noDelay = true

        /**
         * Configures if we should add the logging handler to the pipeline.
         */
        var debug = false

        /**
         * The event loop group we should be using as a parent for all our
         * event processing for this connection.
         */
        var eventLoopGroup = if (Epoll.isAvailable()) EpollEventLoopGroup() else NioEventLoopGroup()

        /**
         * The channel type which should be associated with the event loop
         * group type.
         */
        var channel = if (Epoll.isAvailable()) EpollSocketChannel::class.java else NioSocketChannel::class.java

        /**
         * The connection factory which builds a default connection.
         */
        var connectionFactory = { NettyConnection(PacketDirection.SERVERBOUND) }

        /**
         * Sets the connection factory variable.
         *
         * @param  build       The connection builder.
         * @author Koding
         * @since  0.1.0-SNAPSHOT
         */
        @Suppress("UNUSED")
        fun connectionFactory(build: () -> NettyConnection) {
            // Set the factory
            connectionFactory = build
        }

        /**
         * The builder for adding additional calls to the channel.
         */
        var build: SocketChannel.() -> Unit = {}

        /**
         * Sets the build variable.
         *
         * @param  build      The new build function.
         * @author Koding
         * @since  0.1.0-SNAPSHOT
         */
        @Suppress("UNUSED")
        fun build(build: SocketChannel.() -> Unit) {
            // Set the build value
            this.build = build
        }
    }
}