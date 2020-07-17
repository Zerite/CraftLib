package dev.zerite.craftlib.protocol.packet.play.server.entity.movement

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion
import kotlin.math.roundToInt

/**
 * Velocity is believed to be in units of 1/8000 of a block per server tick (50ms);
 * for example, -1343 would move (-1343 / 8000) = −0.167875 blocks per tick
 * (or −3,3575 blocks per second).
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayEntityVelocityTest : PacketTest<ServerPlayEntityVelocityPacket>(
    ServerPlayEntityVelocityPacket
) {
    init {
        example(
            ServerPlayEntityVelocityPacket(
                300,
                0.0,
                1.0,
                1.5
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeInt(300)
                writeShort(0)
                writeShort(8000)
                writeShort((1.5 * 8000).roundToInt())
            }
            ProtocolVersion.MC1_8 {
                writeVarInt(300)
                writeShort(0)
                writeShort(8000)
                writeShort((1.5 * 8000).roundToInt())
            }
        }
        example(
            ServerPlayEntityVelocityPacket(
                150,
                0.0,
                0.0,
                0.0
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeInt(150)
                writeShort(0)
                writeShort(0)
                writeShort(0)
            }
            ProtocolVersion.MC1_8 {
                writeVarInt(150)
                writeShort(0)
                writeShort(0)
                writeShort(0)
            }
        }
    }
}
