package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.data.entity.EntityMetadata
import dev.zerite.craftlib.protocol.data.entity.MetadataValue
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Tests that entity metadata is being properly written and read
 * from the packet buffer.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayEntityMetadataTest : PacketTest<ServerPlayEntityMetadataPacket>(ServerPlayEntityMetadataPacket) {

    init {
        example(ServerPlayEntityMetadataPacket(0, EntityMetadata(hashMapOf())))
        example(ServerPlayEntityMetadataPacket(90, EntityMetadata(hashMapOf(1 to MetadataValue(1, "example")))))
        example(ServerPlayEntityMetadataPacket(4, EntityMetadata(hashMapOf(2 to MetadataValue(2, "test"))))) {
            ProtocolVersion.MC1_7_2 {
                writeInt(4)
                writeByte((4 shl 5) or (2 and 0x1F) and 0xFF)
                writeString("test")
                writeByte(127)
            }
        }
    }

}