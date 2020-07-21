package dev.zerite.craftlib.protocol.connection.misc

import dev.zerite.craftlib.chat.dsl.chat
import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.connection.PacketHandler
import dev.zerite.craftlib.protocol.packet.handshake.client.ClientHandshakePacket
import dev.zerite.craftlib.protocol.packet.status.client.ClientStatusPingPacket
import dev.zerite.craftlib.protocol.packet.status.client.ClientStatusRequestPacket
import dev.zerite.craftlib.protocol.version.MinecraftProtocol
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.InetAddress

/**
 * Tests the client authentication with connecting to an
 * offline server.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("BlockingMethodInNonBlockingContext")
suspend fun main() {
    // Get the parameters
    val host = System.getProperty("client.host") ?: "127.0.0.1"
    val port = System.getProperty("client.port")?.toIntOrNull() ?: 25566
    val debugNetty = System.getProperty("client.nettyDebug")?.toBoolean() ?: true
    val debugLogging = System.getProperty("client.loggingDebug")?.toBoolean() ?: true
    val disconnectOnError = System.getProperty("client.disconnectOnError")?.toBoolean() ?: false
    val errorInterval = System.getProperty("client.errorInterval")?.toLong() ?: 1000L

    // Connect to localhost
    MinecraftProtocol.connect(InetAddress.getByName(host), port) {
        // Set debug
        debug = debugNetty

        // Build a handler
        handler = object : PacketHandler {

            /**
             * Stores the last logged exception time.
             */
            private var nextLog = -1L

            /**
             * Initializes the connection by sending the handshake and
             * login start packets to test reading and writing.
             *
             * @author Koding
             * @since  0.1.0-SNAPSHOT
             */
            override fun connected(connection: NettyConnection) {
                // Set the connection values
                connection.version = ProtocolVersion.MC1_7_2
                connection.state = MinecraftProtocol.HANDSHAKE

                // Send the handshake packet
                connection.send(
                    ClientHandshakePacket(
                        ProtocolVersion.MC1_7_2,
                        "localhost",
                        25566,
                        MinecraftProtocol.STATUS
                    )
                ) {
                    // Change the state to login
                    connection.state = MinecraftProtocol.STATUS

                    // Send the login start packet
                    connection.send(ClientStatusRequestPacket())
                    connection.send(ClientStatusPingPacket(System.currentTimeMillis()))
                }
            }

            override fun sent(connection: NettyConnection, packet: Packet) {
                // Check if we're in debug logging & print
                if (debugLogging) println("[C->S]: $packet")
            }

            override fun received(connection: NettyConnection, packet: Packet) {
                // Check if we're in debug logging & print
                if (debugLogging) println("[S->C]: $packet")
            }

            override fun exception(connection: NettyConnection, cause: Throwable) {
                // Print the error
                if (!debugNetty && debugLogging && nextLog < System.currentTimeMillis()) {
                    // Set the next log
                    cause.printStackTrace()

                    nextLog = System.currentTimeMillis() + errorInterval
                }
                if (disconnectOnError) {
                    connection.close(chat { string(cause.toString()) })
                }
            }
        }
    }
}
