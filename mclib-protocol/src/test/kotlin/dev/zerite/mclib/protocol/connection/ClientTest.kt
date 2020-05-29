package dev.zerite.mclib.protocol.connection

import dev.zerite.mclib.protocol.packet.handshake.client.ClientHandshakePacket
import dev.zerite.mclib.protocol.packet.login.client.ClientLoginStartPacket
import dev.zerite.mclib.protocol.version.MinecraftProtocol
import dev.zerite.mclib.protocol.version.ProtocolVersion
import java.net.InetAddress

/**
 * Tests the client authentication with connecting to an
 * offline server.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
fun main() {
    // Get the parameters
    val host = System.getProperty("client.host") ?: "127.0.0.1"
    val port = System.getProperty("client.port")?.toIntOrNull() ?: 25566
    val debugNetty = System.getProperty("client.nettyDebug")?.toBoolean() ?: true
    val debugLogging = System.getProperty("client.loggingDebug")?.toBoolean() ?: true

    // Connect to localhost
    MinecraftProtocol.connect(InetAddress.getByName(host), port) {
        // Set debug
        debug = debugNetty

        // Build a handler
        handler = object : PacketHandler {

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
                        MinecraftProtocol.LOGIN
                    )
                ) {
                    // Change the state to login
                    connection.state = MinecraftProtocol.LOGIN

                    // Send the login start packet
                    connection.send(ClientLoginStartPacket("ExampleUser"))
                }
            }

            override fun sent(connection: NettyConnection, packet: Any) {
                // Check if we're in debug logging & print
                if (debugLogging) println("[C->S]: $packet")
            }

            override fun received(connection: NettyConnection, packet: Any) {
                // Check if we're in debug logging & print
                if (debugLogging) println("[S->C] $packet")
            }

            override fun exception(connection: NettyConnection, cause: Throwable) {
                // Print the error
                if (!debugNetty && debugLogging) cause.printStackTrace()
            }
        }
    }
}