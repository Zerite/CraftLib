package dev.zerite.craftlib.protocol.packet.play.server.entity

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Most entity-related packets are subclasses of this packet. When sent from the server to the client,
 * it may initialize the entry.
 *
 * For player entities, either this packet or any move/look packet is sent every game tick.
 * So the meaning of this packet is basically that the entity did not move/look since the last such packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayEntityTest : PacketTest<ServerPlayEntityPacket>(
    ServerPlayEntityPacket
) {

    init {
        example(
            ServerPlayEntityPacket(
                10
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeInt(10)
            }
        }
        example(
            ServerPlayEntityPacket(
                42
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeInt(42)
            }
        }
    }

}
