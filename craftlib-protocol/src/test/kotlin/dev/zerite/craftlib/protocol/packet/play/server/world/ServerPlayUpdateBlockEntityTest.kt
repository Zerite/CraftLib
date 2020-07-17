package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.nbt.compound
import dev.zerite.craftlib.protocol.data.registry.impl.MagicBlockEntityUpdateAction
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Tests that the update block entity packet is working, including testing NBT and
 * fixed point.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayUpdateBlockEntityTest :
    PacketTest<ServerPlayUpdateBlockEntityPacket>(ServerPlayUpdateBlockEntityPacket) {

    init {
        example(ServerPlayUpdateBlockEntityPacket(0, 0, 0, MagicBlockEntityUpdateAction.SET_SPAWNER_MOB, null))
        example(
            ServerPlayUpdateBlockEntityPacket(
                25,
                100,
                25,
                MagicBlockEntityUpdateAction.SET_SPAWNER_MOB,
                compound { "placeholder" to 1 }
            )
        )
        example(
            ServerPlayUpdateBlockEntityPacket(
                20,
                20,
                20,
                MagicBlockEntityUpdateAction.SET_SPAWNER_MOB,
                compound { "type" to "Zombie" }
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeInt(20)
                writeShort(20)
                writeInt(20)
                writeByte(1)
                writeNBT(compound { "type" to "Zombie" }, compressed = true)
            }
            ProtocolVersion.MC1_8 {
                writeLong(((20L and 0x3FFFFFFL) shl 38) or ((20L and 0x3FFFFFFL) shl 12) or (20L and 0xFFFL))
                writeByte(1)
                writeNBT(compound { "type" to "Zombie" }, compressed = true)
            }
        }
    }

}
