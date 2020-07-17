package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.nbt.compound
import dev.zerite.craftlib.nbt.impl.CompoundTag
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Updates NBT data about an entity on the client.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
class ServerPlayUpdateEntityNBTTest : PacketTest<ServerPlayUpdateEntityNBTPacket>(ServerPlayUpdateEntityNBTPacket) {
    init {
        example(ServerPlayUpdateEntityNBTPacket(50, CompoundTag())) {
            ProtocolVersion.MC1_8 {
                writeVarInt(50)
                writeNBT(CompoundTag(), compressed = true)
            }
        }
        example(ServerPlayUpdateEntityNBTPacket(42, compound { "test" to "test" })) {
            ProtocolVersion.MC1_8 {
                writeVarInt(42)
                writeNBT(compound { "test" to "test" }, compressed = true)
            }
        }
    }
}
