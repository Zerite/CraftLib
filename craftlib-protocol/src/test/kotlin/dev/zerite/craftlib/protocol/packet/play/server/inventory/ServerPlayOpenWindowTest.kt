package dev.zerite.craftlib.protocol.packet.play.server.inventory

import dev.zerite.craftlib.chat.component.StringChatComponent
import dev.zerite.craftlib.protocol.data.registry.impl.MagicInventoryType
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * This is sent to the client when it should open an inventory,
 * such as a chest, workbench, or furnace. This message is not
 * sent anywhere for clients opening their own inventory.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayOpenWindowTest : PacketTest<ServerPlayOpenWindowPacket>(ServerPlayOpenWindowPacket) {
    init {
        example(ServerPlayOpenWindowPacket(20, MagicInventoryType.HORSE, StringChatComponent("Horse"), 3, true, entityId = 42)) {
            ProtocolVersion.MC1_7_2 {
                writeByte(20)
                writeByte(11)
                writeString("Horse")
                writeByte(3)
                writeBoolean(true)
                writeInt(42)
            }
            ProtocolVersion.MC1_8 {
                writeByte(20)
                writeString("EntityHorse")
                writeString("{\"text\":\"Horse\"}")
                writeByte(3)
                writeInt(42)
            }
        }
        example(ServerPlayOpenWindowPacket(37, MagicInventoryType.CHEST, StringChatComponent("Menu"), 3 * 9, true)) {
            ProtocolVersion.MC1_7_2 {
                writeByte(37)
                writeByte(0)
                writeString("Menu")
                writeByte(3 * 9)
                writeBoolean(true)
            }
            ProtocolVersion.MC1_8 {
                writeByte(37)
                writeString("minecraft:chest")
                writeString("{\"text\":\"Menu\"}")
                writeByte(3 * 9)
            }
        }
    }
}
