package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.chat.component.StringChatComponent
import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.data.registry.impl.MagicBossBarAction
import dev.zerite.craftlib.protocol.data.registry.impl.MagicBossBarColor
import dev.zerite.craftlib.protocol.data.registry.impl.MagicBossBarDivision
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import java.util.*

/**
 * Allows the server to send multiple custom boss bars without the need to
 * use an invisible ender dragon alike versions before 1.9.
 *
 * @author Koding
 * @since  0.2.0
 */
class ServerPlayBossBarTest : PacketTest<ServerPlayBossBarPacket>(ServerPlayBossBarPacket) {

    init {
        example(
            ServerPlayBossBarPacket(
                UUID(0, 0),
                MagicBossBarAction.ADD,
                title = StringChatComponent("example"),
                health = 1f,
                color = MagicBossBarColor.PURPLE,
                division = MagicBossBarDivision.NO_DIVISION,
                flags = 0
            )
        ) {
            ProtocolVersion.MC1_9 {
                writeUUID(UUID(0, 0), mode = ProtocolBuffer.UUIDMode.RAW)
                writeVarInt(0)

                writeChat(StringChatComponent("example"))
                writeFloat(1f)
                writeVarInt(5)
                writeVarInt(0)
                writeByte(0)
            }
        }
        example(
            ServerPlayBossBarPacket(
                UUID(100, 0),
                MagicBossBarAction.UPDATE_TITLE,
                title = StringChatComponent("example")
            )
        ) {
            ProtocolVersion.MC1_9 {
                writeUUID(UUID(100, 0), mode = ProtocolBuffer.UUIDMode.RAW)
                writeVarInt(3)
                writeChat(StringChatComponent("example"))
            }
        }
        example(
            ServerPlayBossBarPacket(
                UUID(200, 10),
                MagicBossBarAction.UPDATE_HEALTH,
                health = 1f
            )
        ) {
            ProtocolVersion.MC1_9 {
                writeUUID(UUID(200, 10), mode = ProtocolBuffer.UUIDMode.RAW)
                writeVarInt(2)
                writeFloat(1f)
            }
        }
        example(
            ServerPlayBossBarPacket(
                UUID(200, 10),
                MagicBossBarAction.UPDATE_STYLE,
                color = MagicBossBarColor.PINK,
                division = MagicBossBarDivision.SIX_NOTCHES,
            )
        ) {
            ProtocolVersion.MC1_9 {
                writeUUID(UUID(200, 10), mode = ProtocolBuffer.UUIDMode.RAW)
                writeVarInt(4)

                writeVarInt(0)
                writeVarInt(1)
            }
        }
        example(
            ServerPlayBossBarPacket(
                UUID(23, 64),
                MagicBossBarAction.UPDATE_FLAGS,
                flags = 0
            )
        ) {
            ProtocolVersion.MC1_9 {
                writeUUID(UUID(23, 64), mode = ProtocolBuffer.UUIDMode.RAW)
                writeVarInt(5)
                writeByte(0)
            }
        }
        example(
            ServerPlayBossBarPacket(
                UUID(2, 5),
                MagicBossBarAction.REMOVE,
                flags = 0
            )
        ) {
            ProtocolVersion.MC1_9 {
                writeUUID(UUID(2, 5), mode = ProtocolBuffer.UUIDMode.RAW)
                writeVarInt(1)
            }
        }
    }

}