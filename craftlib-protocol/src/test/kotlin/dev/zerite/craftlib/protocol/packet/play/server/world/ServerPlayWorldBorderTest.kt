package dev.zerite.craftlib.protocol.packet.play.server.world

import dev.zerite.craftlib.protocol.data.registry.impl.MagicWorldBorderAction
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server to update the world border visually on the client.
 *
 * @author Koding
 * @since  0.1.1-SNAPSHOT
 */
class ServerPlayWorldBorderTest : PacketTest<ServerPlayWorldBorderPacket>(ServerPlayWorldBorderPacket) {
    init {
        example(
            ServerPlayWorldBorderPacket(
                MagicWorldBorderAction.INITIALIZE,
                oldRadius = 10.0,
                newRadius = 15.0,
                speed = 20,
                portalTeleportBoundary = 4,
                warningBlocks = 63,
                warningTime = 23
            )
        ) {
            ProtocolVersion.MC1_8 {
                writeVarInt(3)
                writeDouble(0.0)
                writeDouble(0.0)
                writeDouble(10.0)
                writeDouble(15.0)
                writeVarLong(20)
                writeVarInt(4)
                writeVarInt(23)
                writeVarInt(63)
            }
        }
        example(ServerPlayWorldBorderPacket(MagicWorldBorderAction.SET_SIZE, newRadius = 50.0)) {
            ProtocolVersion.MC1_8 {
                writeVarInt(0)
                writeDouble(50.0)
            }
        }
        example(
            ServerPlayWorldBorderPacket(
                MagicWorldBorderAction.LERP_SIZE,
                oldRadius = 1.0,
                newRadius = 2.0,
                speed = 4
            )
        ) {
            ProtocolVersion.MC1_8 {
                writeVarInt(1)
                writeDouble(1.0)
                writeDouble(2.0)
                writeVarLong(4)
            }
        }
        example(ServerPlayWorldBorderPacket(MagicWorldBorderAction.SET_CENTER)) {
            ProtocolVersion.MC1_8 {
                writeVarInt(2)
                writeDouble(0.0)
                writeDouble(0.0)
            }
        }
        example(ServerPlayWorldBorderPacket(MagicWorldBorderAction.SET_WARNING_TIME, warningTime = 3)) {
            ProtocolVersion.MC1_8 {
                writeVarInt(4)
                writeVarInt(3)
            }
        }
        example(ServerPlayWorldBorderPacket(MagicWorldBorderAction.SET_WARNING_BLOCKS, warningBlocks = 3)) {
            ProtocolVersion.MC1_8 {
                writeVarInt(5)
                writeVarInt(3)
            }
        }
    }
}
