package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.ProtocolBuffer
import dev.zerite.craftlib.protocol.data.registry.UnknownRegistryEntry
import dev.zerite.craftlib.protocol.data.registry.impl.MagicEntityProperty
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import java.util.*

/**
 * Tests that entity properties are being properly encoded.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayEntityPropertiesTest : PacketTest<ServerPlayEntityPropertiesPacket>(ServerPlayEntityPropertiesPacket) {

    init {
        example(ServerPlayEntityPropertiesPacket(0, emptyArray()))
        example(ServerPlayEntityPropertiesPacket(42, Array(5) {
            ServerPlayEntityPropertiesPacket.Property(UnknownRegistryEntry("example${it * 2}"), it.toDouble(), Array(3) {
                ServerPlayEntityPropertiesPacket.Modifier(UUID.randomUUID(), 2.0, 1)
            })
        }))
        example(
            ServerPlayEntityPropertiesPacket(
                127, arrayOf(
                    ServerPlayEntityPropertiesPacket.Property(
                        MagicEntityProperty.GENERIC_MAX_HEALTH, 20.0, arrayOf(
                            ServerPlayEntityPropertiesPacket.Modifier(UUID(0L, 0L), 1.0, 1)
                        )
                    )
                )
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                // Write entity ID
                writeInt(127)

                // Write single property array
                writeInt(1)
                writeString("generic.maxHealth")
                writeDouble(20.0)

                // Write single modifier array
                writeShort(1)
                writeUUID(UUID(0L, 0L), ProtocolBuffer.UUIDMode.RAW)
                writeDouble(1.0)
                writeByte(1)
            }
            ProtocolVersion.MC1_8 {
                // Write entity ID
                writeVarInt(127)

                // Write single property array
                writeInt(1)
                writeString("generic.maxHealth")
                writeDouble(20.0)

                // Write single modifier array
                writeVarInt(1)
                writeUUID(UUID(0L, 0L), ProtocolBuffer.UUIDMode.RAW)
                writeDouble(1.0)
                writeByte(1)
            }
        }
    }

}
