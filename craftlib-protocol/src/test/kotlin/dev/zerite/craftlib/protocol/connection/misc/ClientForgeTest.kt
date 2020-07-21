package dev.zerite.craftlib.protocol.connection.misc

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import dev.zerite.craftlib.chat.component.StringChatComponent
import dev.zerite.craftlib.chat.dsl.chat
import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.compat.forge.forge
import dev.zerite.craftlib.protocol.compat.forge.packet.client.ClientForgeHandshakeHelloPacket
import dev.zerite.craftlib.protocol.compat.forge.packet.global.GlobalForgeHandshakeAcknowledgePacket
import dev.zerite.craftlib.protocol.compat.forge.packet.global.GlobalForgeHandshakeModListPacket
import dev.zerite.craftlib.protocol.compat.forge.packet.server.ServerForgeHandshakeHelloPacket
import dev.zerite.craftlib.protocol.compat.forge.packet.server.ServerForgeHandshakeModIdDataPacket
import dev.zerite.craftlib.protocol.compat.forge.packet.server.ServerForgeHandshakeRegistryDataPacket
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.connection.PacketHandler
import dev.zerite.craftlib.protocol.packet.handshake.client.ClientHandshakePacket
import dev.zerite.craftlib.protocol.packet.login.client.ClientLoginEncryptionResponsePacket
import dev.zerite.craftlib.protocol.packet.login.client.ClientLoginStartPacket
import dev.zerite.craftlib.protocol.packet.login.server.ServerLoginEncryptionRequestPacket
import dev.zerite.craftlib.protocol.packet.login.server.ServerLoginSetCompressionPacket
import dev.zerite.craftlib.protocol.packet.login.server.ServerLoginSuccessPacket
import dev.zerite.craftlib.protocol.packet.play.client.other.ClientPlayPluginMessagePacket
import dev.zerite.craftlib.protocol.packet.play.server.other.ServerPlaySetCompressionPacket
import dev.zerite.craftlib.protocol.packet.play.server.world.ServerPlayMapChunkBulkPacket
import dev.zerite.craftlib.protocol.packet.status.client.ClientStatusRequestPacket
import dev.zerite.craftlib.protocol.packet.status.server.ServerStatusResponsePacket
import dev.zerite.craftlib.protocol.util.Crypto
import dev.zerite.craftlib.protocol.util.ext.toUuid
import dev.zerite.craftlib.protocol.version.MinecraftProtocol
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import java.math.BigInteger
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.URL
import java.security.MessageDigest
import java.util.*

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
    var username = System.getProperty("client.username") ?: "ExampleUser"
    val password: String? = System.getProperty("client.password")
    val debugNetty = System.getProperty("client.nettyDebug")?.toBoolean() ?: true
    val debugLogging = System.getProperty("client.loggingDebug")?.toBoolean() ?: true
    val disconnectOnError = System.getProperty("client.disconnectOnError")?.toBoolean() ?: false
    val errorInterval = System.getProperty("client.errorInterval")?.toLong() ?: 1000L
    val clientToken = UUID.randomUUID()
    val version = ProtocolVersion.MC1_8

    var accessToken: String? = null
    var uuid = System.getProperty("client.uuid")?.toUuid() ?: UUID(0, 0)

    if (password != null) {
        // TODO: Use craftlib-auth once it's created
        println("Logging in")
        val authUrl = URL("https://authserver.mojang.com/authenticate")
        val data = JsonObject().also { data ->
            data.add("agent", JsonObject().also {
                it.addProperty("name", "Minecraft")
                it.addProperty("version", 1)
            })
            data.addProperty("username", username)
            data.addProperty("password", password)
            data.addProperty("clientToken", clientToken.toString())
            data.addProperty("requestUser", false)
        }
        val connection = authUrl.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.addRequestProperty("Content-Type", "application/json")
        connection.addRequestProperty(
            "User-Agent",
            "Craftlib/${MinecraftProtocol::class.java.`package`.implementationVersion}"
        )
        connection.addRequestProperty("Accept", "application/json")
        connection.doOutput = true
        connection.outputStream.use {
            it.write(data.toString().toByteArray())
        }
        val errored = connection.responseCode != 200
        val stream = if (errored) connection.errorStream else connection.inputStream
        val responseData = JsonParser.parseString(stream.bufferedReader().use { it.readText() }).asJsonObject
        if (errored) {
            error("Failed to log in: ${responseData["errorMessage"].asString}")
        }
        responseData["selectedProfile"].asJsonObject.let {
            username = it["name"].asString
            uuid = it["id"].asString.toUuid(dashes = true)
        }
        accessToken = responseData["accessToken"].asString
        println("Successfully logged in as $username")
    }

    var mods = arrayOf<GlobalForgeHandshakeModListPacket.Mod>()

    MinecraftProtocol.connect(InetAddress.getByName(host), port) {
        debug = debugNetty
        connectSync = true

        handler = object : PacketHandler {
            override fun connected(connection: NettyConnection) {
                // Set the connection values
                connection.version = version
                connection.state = MinecraftProtocol.HANDSHAKE
                connection.forge = true

                // Send the handshake packet
                connection.send(
                    ClientHandshakePacket(
                        version,
                        "localhost",
                        25566,
                        MinecraftProtocol.STATUS
                    )
                ) {
                    // Change the state to login
                    connection.state = MinecraftProtocol.STATUS

                    // Send the login start packet
                    connection.send(ClientStatusRequestPacket())
                }
            }

            override fun received(connection: NettyConnection, packet: Packet) {
                if (packet is ServerStatusResponsePacket) {
                    packet.response.modInfo?.modList?.forEach {
                        mods += GlobalForgeHandshakeModListPacket.Mod(it.modId, it.version)
                    } ?: error("Server is not using Forge")
                    connection.close(StringChatComponent("Disconnecting"))
                }
            }
        }
    }

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
                connection.version = version
                connection.state = MinecraftProtocol.HANDSHAKE
                connection.forge = true

                // Send the handshake packet
                connection.send(
                    ClientHandshakePacket(
                        version,
                        "localhost",
                        25566,
                        MinecraftProtocol.LOGIN
                    )
                ) {
                    // Change the state to login
                    connection.state = MinecraftProtocol.LOGIN

                    // Send the login start packet
                    connection.send(ClientLoginStartPacket(username))
                }
            }

            override fun sent(connection: NettyConnection, packet: Packet) {
                // Check if we're in debug logging & print
                if (debugLogging) println("[C->S]: $packet")
            }

            override fun received(connection: NettyConnection, packet: Packet) {
                // Check if we're in debug logging & print
                if (debugLogging && packet !is ServerPlayMapChunkBulkPacket) println("[S->C]: $packet")

                when (packet) {
                    is ServerForgeHandshakeHelloPacket -> {
                        connection.send(
                            ClientPlayPluginMessagePacket.register(
                                version,
                                "FML|HS",
                                "FML",
                                "FML|MP",
                                "FML",
                                "FORGE"
                            )
                        )
                        connection.send(ClientForgeHandshakeHelloPacket(packet.version))
                        connection.send(GlobalForgeHandshakeModListPacket(mods))
                    }
                    is GlobalForgeHandshakeModListPacket -> connection.send(
                        GlobalForgeHandshakeAcknowledgePacket(
                            GlobalForgeHandshakeAcknowledgePacket.WAITING_SERVER_DATA
                        )
                    )
                    is ServerForgeHandshakeModIdDataPacket -> connection.send(
                        GlobalForgeHandshakeAcknowledgePacket(
                            GlobalForgeHandshakeAcknowledgePacket.WAITING_SERVER_COMPLETE
                        )
                    )
                    is ServerForgeHandshakeRegistryDataPacket -> if (!packet.hasMore) connection.send(
                        GlobalForgeHandshakeAcknowledgePacket(GlobalForgeHandshakeAcknowledgePacket.WAITING_SERVER_COMPLETE)
                    )
                    is GlobalForgeHandshakeAcknowledgePacket -> when (packet.phase) {
                        GlobalForgeHandshakeAcknowledgePacket.WAITING_ACK -> connection.send(
                            GlobalForgeHandshakeAcknowledgePacket(GlobalForgeHandshakeAcknowledgePacket.PENDING_COMPLETE)
                        )
                        GlobalForgeHandshakeAcknowledgePacket.COMPLETE_SERVER -> connection.send(
                            GlobalForgeHandshakeAcknowledgePacket(GlobalForgeHandshakeAcknowledgePacket.COMPLETE_CLIENT)
                        )
                    }
                    is ServerLoginEncryptionRequestPacket -> {
                        if (accessToken == null) error("Can't connect to online mode servers without authentication")
                        val secret = Crypto.newSecretKey()
                        // Generate server id hash
                        val digest = MessageDigest.getInstance("SHA-1")
                        for (array in arrayOf(
                            packet.serverId.toByteArray(charset("ISO_8859_1")),
                            secret.encoded,
                            packet.publicKey.encoded
                        )) {
                            digest.update(array)
                        }
                        val idHash = BigInteger(digest.digest()).toString(16)
                        // Authenticate
                        val url = URL("https://sessionserver.mojang.com/session/minecraft/join")
                        val data = JsonObject().also {
                            it.addProperty("accessToken", accessToken)
                            it.addProperty("selectedProfile", uuid.toString().replace("-", ""))
                            it.addProperty("serverId", idHash)
                        }
                        val authConnection = url.openConnection() as HttpURLConnection
                        authConnection.requestMethod = "POST"
                        authConnection.addRequestProperty("Content-Type", "application/json")
                        authConnection.addRequestProperty(
                            "User-Agent",
                            "Craftlib/${MinecraftProtocol::class.java.`package`.implementationVersion}"
                        )
                        authConnection.addRequestProperty("Accept", "application/json")
                        authConnection.doOutput = true
                        authConnection.outputStream.use {
                            it.write(data.toString().toByteArray())
                        }

                        if (authConnection.responseCode != 204) {
                            error("Failed to log in to Mojang servers")
                        }

                        // Send response packet
                        connection.send(
                            ClientLoginEncryptionResponsePacket(
                                packet.publicKey,
                                secret,
                                packet.verifyToken
                            )
                        ) {
                            connection.enableEncryption(secret)
                        }
                    }
                    is ServerLoginSetCompressionPacket -> connection.compressionThreshold = packet.threshold
                    is ServerPlaySetCompressionPacket -> connection.compressionThreshold = packet.threshold
                    is ServerLoginSuccessPacket -> connection.state = MinecraftProtocol.PLAY
                }
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
