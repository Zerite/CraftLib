package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.nbt.impl.CompoundTag
import dev.zerite.craftlib.protocol.Slot
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Tests that entity equipment is working.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayEntityEquipmentTest : PacketTest<ServerPlayEntityEquipmentPacket>(ServerPlayEntityEquipmentPacket) {

    init {
        example(ServerPlayEntityEquipmentPacket(0, 0, Slot(1, count = 1)))
        example(ServerPlayEntityEquipmentPacket(127, 2, Slot(3, count = 64, data = CompoundTag())))
        example(ServerPlayEntityEquipmentPacket(50, 1, Slot(3, count = 64))) {
            ProtocolVersion.MC1_7_2 {
                // Entity ID and slot index
                writeInt(50)
                writeShort(1)

                // Slot
                writeShort(3)
                writeByte(64)
                writeShort(0)
                writeShort(-1)
            }
            ProtocolVersion.MC1_8 {
                // Entity ID and slot index
                writeVarInt(50)
                writeShort(1)

                // Slot
                writeShort(3)
                writeByte(64)
                writeShort(0)
                writeByte(0)
            }
        }
    }

}
