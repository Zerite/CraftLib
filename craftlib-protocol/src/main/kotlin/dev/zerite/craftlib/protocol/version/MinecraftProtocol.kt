package dev.zerite.craftlib.protocol.version

import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.connection.PacketHandler
import dev.zerite.craftlib.protocol.connection.io.LengthCodec
import dev.zerite.craftlib.protocol.connection.io.PacketCodec
import dev.zerite.craftlib.protocol.packet.handshake.client.ClientHandshakePacket
import dev.zerite.craftlib.protocol.packet.login.client.ClientLoginEncryptionResponsePacket
import dev.zerite.craftlib.protocol.packet.login.client.ClientLoginStartPacket
import dev.zerite.craftlib.protocol.packet.login.server.ServerLoginDisconnectPacket
import dev.zerite.craftlib.protocol.packet.login.server.ServerLoginEncryptionRequestPacket
import dev.zerite.craftlib.protocol.packet.login.server.ServerLoginSuccessPacket
import dev.zerite.craftlib.protocol.packet.play.client.display.ClientPlayAnimationPacket
import dev.zerite.craftlib.protocol.packet.play.client.display.ClientPlayChatMessagePacket
import dev.zerite.craftlib.protocol.packet.play.client.interaction.ClientPlayEntityActionPacket
import dev.zerite.craftlib.protocol.packet.play.client.interaction.ClientPlayPlayerBlockPlacementPacket
import dev.zerite.craftlib.protocol.packet.play.client.interaction.ClientPlayPlayerDiggingPacket
import dev.zerite.craftlib.protocol.packet.play.client.interaction.ClientPlayUseEntityPacket
import dev.zerite.craftlib.protocol.packet.play.client.inventory.*
import dev.zerite.craftlib.protocol.packet.play.client.other.*
import dev.zerite.craftlib.protocol.packet.play.client.player.*
import dev.zerite.craftlib.protocol.packet.play.server.display.*
import dev.zerite.craftlib.protocol.packet.play.server.entity.*
import dev.zerite.craftlib.protocol.packet.play.server.entity.movement.*
import dev.zerite.craftlib.protocol.packet.play.server.interaction.ServerPlayCollectItemPacket
import dev.zerite.craftlib.protocol.packet.play.server.interaction.ServerPlaySignEditorOpenPacket
import dev.zerite.craftlib.protocol.packet.play.server.interaction.ServerPlayUpdateSignPacket
import dev.zerite.craftlib.protocol.packet.play.server.interaction.ServerPlayUseBedPacket
import dev.zerite.craftlib.protocol.packet.play.server.inventory.*
import dev.zerite.craftlib.protocol.packet.play.server.join.ServerPlayJoinGamePacket
import dev.zerite.craftlib.protocol.packet.play.server.player.ServerPlayPlayerAbilitiesPacket
import dev.zerite.craftlib.protocol.packet.play.server.join.ServerPlaySpawnPositionPacket
import dev.zerite.craftlib.protocol.packet.play.server.join.ServerPlayStatisticsPacket
import dev.zerite.craftlib.protocol.packet.play.server.other.*
import dev.zerite.craftlib.protocol.packet.play.server.player.ServerPlayPlayerPositionLookPacket
import dev.zerite.craftlib.protocol.packet.play.server.player.ServerPlaySetExperiencePacket
import dev.zerite.craftlib.protocol.packet.play.server.player.ServerPlayUpdateHealthPacket
import dev.zerite.craftlib.protocol.packet.play.server.world.*
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
    val PLAY = protocol("Play", 0) {
        serverbound {
            ClientPlayKeepAlivePacket {
                ProtocolVersion.MC1_7_2 to 0x00
            }
            ClientPlayChatMessagePacket {
                ProtocolVersion.MC1_7_2 to 0x01
            }
            ClientPlayUseEntityPacket {
                ProtocolVersion.MC1_7_2 to 0x02
            }
            ClientPlayPlayerPacket {
                ProtocolVersion.MC1_7_2 to 0x03
            }
            ClientPlayPlayerPositionPacket {
                ProtocolVersion.MC1_7_2 to 0x04
            }
            ClientPlayPlayerLookPacket {
                ProtocolVersion.MC1_7_2 to 0x05
            }
            ClientPlayPlayerPositionLookPacket {
                ProtocolVersion.MC1_7_2 to 0x06
            }
            ClientPlayPlayerDiggingPacket {
                ProtocolVersion.MC1_7_2 to 0x07
            }
            ClientPlayPlayerBlockPlacementPacket {
                ProtocolVersion.MC1_7_2 to 0x08
            }
            ClientPlayPlayerHeldItemChangePacket {
                ProtocolVersion.MC1_7_2 to 0x09
            }
            ClientPlayAnimationPacket {
                ProtocolVersion.MC1_7_2 to 0x0A
            }
            ClientPlayEntityActionPacket {
                ProtocolVersion.MC1_7_2 to 0x0B
            }
            ClientPlayPlayerSteerVehiclePacket {
                ProtocolVersion.MC1_7_2 to 0x0C
            }
            ClientPlayCloseWindowPacket {
                ProtocolVersion.MC1_7_2 to 0x0D
            }
            ClientPlayClickWindowPacket {
                ProtocolVersion.MC1_7_2 to 0x0E
            }
            ClientPlayConfirmTransactionPacket {
                ProtocolVersion.MC1_7_2 to 0x0F
            }
            ClientPlayCreativeInventoryActionPacket {
                ProtocolVersion.MC1_7_2 to 0x10
            }
            ClientPlayEnchantItemPacket {
                ProtocolVersion.MC1_7_2 to 0x11
            }
            ClientPlayUpdateSignPacket {
                ProtocolVersion.MC1_7_2 to 0x12
            }
            ClientPlayPlayerAbilitiesPacket {
                ProtocolVersion.MC1_7_2 to 0x13
            }
            ClientPlayTabCompletePacket {
                ProtocolVersion.MC1_7_2 to 0x14
            }
            ClientPlayClientSettingsPacket {
                ProtocolVersion.MC1_7_2 to 0x15
            }
            ClientPlayClientStatusPacket {
                ProtocolVersion.MC1_7_2 to 0x16
            }
            ClientPlayPluginMessagePacket {
                ProtocolVersion.MC1_7_2 to 0x17
            }
        }
        clientbound {
            ServerPlayKeepAlivePacket {
                ProtocolVersion.MC1_7_2 to 0x00
            }
            ServerPlayJoinGamePacket {
                ProtocolVersion.MC1_7_2 to 0x01
            }
            ServerPlayChatMessagePacket {
                ProtocolVersion.MC1_7_2 to 0x02
            }
            ServerPlayTimeUpdatePacket {
                ProtocolVersion.MC1_7_2 to 0x03
            }
            ServerPlayEntityEquipmentPacket {
                ProtocolVersion.MC1_7_2 to 0x04
            }
            ServerPlaySpawnPositionPacket {
                ProtocolVersion.MC1_7_2 to 0x05
            }
            ServerPlayUpdateHealthPacket {
                ProtocolVersion.MC1_7_2 to 0x06
            }
            ServerPlayRespawnPacket {
                ProtocolVersion.MC1_7_2 to 0x07
            }
            ServerPlayPlayerPositionLookPacket {
                ProtocolVersion.MC1_7_2 to 0x08
            }
            ServerPlayHeldItemChangePacket {
                ProtocolVersion.MC1_7_2 to 0x09
            }
            ServerPlayUseBedPacket {
                ProtocolVersion.MC1_7_2 to 0x0A
            }
            ServerPlayAnimationPacket {
                ProtocolVersion.MC1_7_2 to 0x0B
            }
            ServerPlaySpawnPlayerPacket {
                ProtocolVersion.MC1_7_2 to 0x0C
            }
            ServerPlayCollectItemPacket {
                ProtocolVersion.MC1_7_2 to 0x0D
            }
            ServerPlaySpawnObjectPacket {
                ProtocolVersion.MC1_7_2 to 0x0E
            }
            ServerPlaySpawnMobPacket {
                ProtocolVersion.MC1_7_2 to 0x0F
            }
            ServerPlaySpawnPaintingPacket {
                ProtocolVersion.MC1_7_2 to 0x10
            }
            ServerPlaySpawnExperienceOrbPacket {
                ProtocolVersion.MC1_7_2 to 0x11
            }
            ServerPlayEntityVelocityPacket {
                ProtocolVersion.MC1_7_2 to 0x12
            }
            ServerPlayDestroyEntitiesPacket {
                ProtocolVersion.MC1_7_2 to 0x13
            }
            ServerPlayEntityPacket {
                ProtocolVersion.MC1_7_2 to 0x14
            }
            ServerPlayEntityRelativeMovePacket {
                ProtocolVersion.MC1_7_2 to 0x15
            }
            ServerPlayEntityLookPacket {
                ProtocolVersion.MC1_7_2 to 0x16
            }
            ServerPlayEntityLookRelativeMovePacket {
                ProtocolVersion.MC1_7_2 to 0x17
            }
            ServerPlayEntityTeleportPacket {
                ProtocolVersion.MC1_7_2 to 0x18
            }
            ServerPlayEntityHeadLookPacket {
                ProtocolVersion.MC1_7_2 to 0x19
            }
            ServerPlayEntityStatusPacket {
                ProtocolVersion.MC1_7_2 to 0x1A
            }
            ServerPlayAttachEntityPacket {
                ProtocolVersion.MC1_7_2 to 0x1B
            }
            ServerPlayEntityMetadataPacket {
                ProtocolVersion.MC1_7_2 to 0x1C
            }
            ServerPlayEntityEffectPacket {
                ProtocolVersion.MC1_7_2 to 0x1D
            }
            ServerPlayRemoveEntityEffectPacket {
                ProtocolVersion.MC1_7_2 to 0x1E
            }
            ServerPlaySetExperiencePacket {
                ProtocolVersion.MC1_7_2 to 0x1F
            }
            ServerPlayEntityPropertiesPacket {
                ProtocolVersion.MC1_7_2 to 0x20
            }
            ServerPlayChunkDataPacket {
                ProtocolVersion.MC1_7_2 to 0x21
            }
            ServerPlayMultiBlockChangePacket {
                ProtocolVersion.MC1_7_2 to 0x22
            }
            ServerPlayBlockChangePacket {
                ProtocolVersion.MC1_7_2 to 0x23
            }
            ServerPlayBlockActionPacket {
                ProtocolVersion.MC1_7_2 to 0x24
            }
            ServerPlayBlockBreakAnimationPacket {
                ProtocolVersion.MC1_7_2 to 0x25
            }
            ServerPlayMapChunkBulkPacket {
                ProtocolVersion.MC1_7_2 to 0x26
            }
            ServerPlayExplosionPacket {
                ProtocolVersion.MC1_7_2 to 0x27
            }
            ServerPlayEffectPacket {
                ProtocolVersion.MC1_7_2 to 0x28
            }
            ServerPlaySoundEffectPacket {
                ProtocolVersion.MC1_7_2 to 0x29
            }
            ServerPlayParticlePacket {
                ProtocolVersion.MC1_7_2 to 0x2A
            }
            ServerPlayChangeGameStatePacket {
                ProtocolVersion.MC1_7_2 to 0x2B
            }
            ServerPlaySpawnGlobalEntityPacket {
                ProtocolVersion.MC1_7_2 to 0x2C
            }
            ServerPlayOpenWindowPacket {
                ProtocolVersion.MC1_7_2 to 0x2D
            }
            ServerPlayCloseWindowPacket {
                ProtocolVersion.MC1_7_2 to 0x2E
            }
            ServerPlaySetSlotPacket {
                ProtocolVersion.MC1_7_2 to 0x2F
            }
            ServerPlayWindowItemsPacket {
                ProtocolVersion.MC1_7_2 to 0x30
            }
            ServerPlayWindowPropertyPacket {
                ProtocolVersion.MC1_7_2 to 0x31
            }
            ServerPlayConfirmTransactionPacket {
                ProtocolVersion.MC1_7_2 to 0x32
            }
            ServerPlayUpdateSignPacket {
                ProtocolVersion.MC1_7_2 to 0x33
            }
            ServerPlayMapsPacket {
                ProtocolVersion.MC1_7_2 to 0x34
            }
            ServerPlayUpdateBlockEntityPacket {
                ProtocolVersion.MC1_7_2 to 0x35
            }
            ServerPlaySignEditorOpenPacket {
                ProtocolVersion.MC1_7_2 to 0x36
            }
            ServerPlayStatisticsPacket {
                ProtocolVersion.MC1_7_2 to 0x37
            }
            ServerPlayPlayerListItemPacket {
                ProtocolVersion.MC1_7_2 to 0x38
            }
            ServerPlayPlayerAbilitiesPacket {
                ProtocolVersion.MC1_7_2 to 0x39
            }
            ServerPlayTabCompletePacket {
                ProtocolVersion.MC1_7_2 to 0x3A
            }
            ServerPlayScoreboardObjectivePacket {
                ProtocolVersion.MC1_7_2 to 0x3B
            }
            ServerPlayUpdateScorePacket {
                ProtocolVersion.MC1_7_2 to 0x3C
            }
            ServerPlayDisplayScoreboardPacket {
                ProtocolVersion.MC1_7_2 to 0x3D
            }
            ServerPlayTeamsPacket {
                ProtocolVersion.MC1_7_2 to 0x3E
            }
            ServerPlayPluginMessagePacket {
                ProtocolVersion.MC1_7_2 to 0x3F
            }
            ServerPlayDisconnectPacket {
                ProtocolVersion.MC1_7_2 to 0x40
            }
        }
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
            ClientLoginEncryptionResponsePacket {
                ProtocolVersion.MC1_7_2 to 0x01
            }
        }
        clientbound {
            ServerLoginDisconnectPacket {
                ProtocolVersion.MC1_7_2 to 0x00
            }
            ServerLoginEncryptionRequestPacket {
                ProtocolVersion.MC1_7_2 to 0x01
            }
            ServerLoginSuccessPacket {
                ProtocolVersion.MC1_7_2 to 0x02
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
        val config = ConnectConfig(address, port).apply(build)
        val connection = config.connectionFactory().apply { handler = config.handler }

        // Configure and connect
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
                    config.build(ch)
                    ch.config().isTcpNoDelay = config.noDelay
                    config.timeout.takeIf { it > 0 }
                        ?.let { ch.pipeline().addLast("readTimeout", ReadTimeoutHandler(it)) }

                    ch.pipeline()
                        .addLast("length", LengthCodec(connection))
                        .addLast("packet", PacketCodec(connection))
                        .apply { if (config.debug) addLast("logger", LoggingHandler(LogLevel.INFO)) }
                        .addLast("connection", connection)
                }
            })
            .connect(config.address, config.port)
            .let { if (config.connectSync) it.syncUninterruptibly() }

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
            this.build = build
        }
    }
}
