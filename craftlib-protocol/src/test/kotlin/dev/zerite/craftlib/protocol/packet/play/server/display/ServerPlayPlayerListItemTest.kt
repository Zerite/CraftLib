package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.packet.PacketTest
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
class ServerPlayPlayerListItemTest : PacketTest<ServerPlayPlayerListItemPacket>(ServerPlayPlayerListItemPacket) {

    init {
        example(
            ServerPlayPlayerListItemPacket(
                0,
                arrayOf(ServerPlayPlayerListItemPacket.PlayerEntry(UUID(0L, 0L), "Example"))
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeString("Example")
                writeBoolean(true)
                writeShort(0)
            }
            ProtocolVersion.MC1_8 {
                writeVarInt(0)
                writeVarInt(1)

                // Entry
                writeUUID(UUID(0L, 0L), mode = ProtocolBuffer.UUIDMode.RAW)
                writeString("Example")
                writeVarInt(0)
                writeVarInt(0)
                writeVarInt(0)
                writeBoolean(false)
            }
        }
        example(
            ServerPlayPlayerListItemPacket(4, arrayOf(ServerPlayPlayerListItemPacket.PlayerEntry(UUID(0L, 0L)))),
            minimumVersion = ProtocolVersion.MC1_8
        ) {
            ProtocolVersion.MC1_8 {
                writeVarInt(4)
                writeVarInt(1)
                writeUUID(UUID(0L, 0L), mode = ProtocolBuffer.UUIDMode.RAW)
            }
        }
    }

}
