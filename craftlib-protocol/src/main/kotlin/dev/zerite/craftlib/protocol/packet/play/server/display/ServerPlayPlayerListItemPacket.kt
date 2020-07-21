package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.chat.component.BaseChatComponent
import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.PacketIO
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.connection.NettyConnection
import dev.zerite.craftlib.protocol.data.registry.RegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicGamemode
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import java.util.*

/**
 * Sent by the server to indicate that the tab list should be updated
 * with a new player, or one should be removed. If online is true, a new
 * player is added to the list or has their ping updated if they're already present.
 * If false, it will remove the player from the list.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ServerPlayPlayerListItemPacket(
    var action: Int,
    var items: Array<PlayerEntry>
) : Packet() {
    companion object : PacketIO<ServerPlayPlayerListItemPacket> {
        override fun read(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            connection: NettyConnection
        ) = if (version >= ProtocolVersion.MC1_8) {
            val action = buffer.readVarInt()
            ServerPlayPlayerListItemPacket(
                action,
                buffer.readArray {
                    val player = PlayerEntry(readUUID(mode = ProtocolBuffer.UUIDMode.RAW))
                    if (action == 0) {
                        player.name = readString()
                        player.properties = readArray {
                            PlayerProperty(
                                readString(),
                                readString(),
                                this.takeIf { readBoolean() }?.readString()
                            )
                        }
                    }
                    if (action == 0 || action == 1) player.gamemode = MagicGamemode[version, readVarInt()]
                    if (action == 0 || action == 2) player.ping = readVarInt()
                    if (action == 0 || action == 3) player.displayName = this.takeIf { readBoolean() }?.readChat()
                    player
                }
            )
        } else {
            val name = buffer.readString()
            val online = buffer.readBoolean()
            val ping = buffer.readShort().toInt()
            ServerPlayPlayerListItemPacket(
                when {
                    online && ping > 0 -> 2
                    online && ping == 0 -> 0
                    else -> 4
                },
                arrayOf(PlayerEntry(UUID(0L, 0L), name = name, ping = ping))
            )
        }

        override fun write(
            buffer: ProtocolBuffer,
            version: ProtocolVersion,
            packet: ServerPlayPlayerListItemPacket,
            connection: NettyConnection
        ) {
            if (version >= ProtocolVersion.MC1_8) {
                buffer.writeVarInt(packet.action)
                buffer.writeArray(packet.items) {
                    writeUUID(it.uuid, mode = ProtocolBuffer.UUIDMode.RAW)
                    if (packet.action == 0) {
                        writeString(it.name ?: "Player")
                        writeArray(it.properties) { p ->
                            writeString(p.name)
                            writeString(p.value)
                            writeBoolean(p.signature != null)
                            if (p.signature != null)
                                writeString(p.signature!!)
                        }
                    }
                    if (packet.action == 0 || packet.action == 1) writeVarInt(
                        MagicGamemode[version, it.gamemode, Int::class.java] ?: 0
                    )
                    if (packet.action == 0 || packet.action == 2) writeVarInt(it.ping)
                    if (packet.action == 0 || packet.action == 3) {
                        writeBoolean(it.displayName != null)
                        if (it.displayName != null)
                            writeChat(it.displayName!!)
                    }
                }
            } else {
                val entry = packet.items.first()
                buffer.writeString(entry.name ?: "Player")
                buffer.writeBoolean(packet.action != 4)
                buffer.writeShort(entry.ping)
            }
        }
    }

    data class PlayerEntry(
        var uuid: UUID,
        var name: String? = null,
        var properties: Array<PlayerProperty> = arrayOf(),
        var gamemode: RegistryEntry = MagicGamemode.SURVIVAL,
        var ping: Int = 0,
        var displayName: BaseChatComponent? = null
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as PlayerEntry

            if (uuid != other.uuid) return false
            if (name != other.name) return false
            if (!properties.contentEquals(other.properties)) return false
            if (gamemode != other.gamemode) return false
            if (ping != other.ping) return false
            if (displayName != other.displayName) return false

            return true
        }

        override fun hashCode(): Int {
            var result = uuid.hashCode()
            result = 31 * result + name.hashCode()
            result = 31 * result + properties.contentHashCode()
            result = 31 * result + gamemode.hashCode()
            result = 31 * result + ping
            result = 31 * result + displayName.hashCode()
            return result
        }
    }

    data class PlayerProperty(
        var name: String,
        var value: String,
        var signature: String? = null
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServerPlayPlayerListItemPacket

        if (action != other.action) return false
        if (!items.contentEquals(other.items)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = action
        result = 31 * result + items.contentHashCode()
        return result
    }
}
