package dev.zerite.craftlib.protocol.packet.play.server.display

import dev.zerite.craftlib.protocol.data.registry.impl.MagicTeamFriendlyFire
import dev.zerite.craftlib.protocol.data.registry.impl.MagicTeamMode
import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Creates and updates teams.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayTeamsTest : PacketTest<ServerPlayTeamsPacket>(ServerPlayTeamsPacket) {
    init {
        example(
            ServerPlayTeamsPacket(
                "team",
                MagicTeamMode.CREATE_TEAM,
                displayName = "team",
                prefix = "prefix",
                suffix = "suffix",
                friendlyFire = MagicTeamFriendlyFire.ON,
                players = arrayOf("Username")
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeString("team")
                writeByte(0)
                writeString("team")
                writeString("prefix")
                writeString("suffix")
                writeByte(1)
                writeShort(1)
                writeString("Username")
            }
        }
        example(ServerPlayTeamsPacket("delete", MagicTeamMode.REMOVE_TEAM)) {
            ProtocolVersion.MC1_7_2 {
                writeString("delete")
                writeByte(1)
            }
        }
        example(ServerPlayTeamsPacket("players", MagicTeamMode.ADD_PLAYERS, players = arrayOf("NewPlayer"))) {
            ProtocolVersion.MC1_7_2 {
                writeString("players")
                writeByte(3)
                writeShort(1)
                writeString("NewPlayer")
            }
        }
        example(
            ServerPlayTeamsPacket(
                "update",
                MagicTeamMode.UPDATE_INFO,
                displayName = "name",
                prefix = "dummy",
                suffix = "placeholder",
                friendlyFire = MagicTeamFriendlyFire.SEE_FRIENDLY_INVISIBLES
            )
        ) {
            ProtocolVersion.MC1_7_2 {
                writeString("update")
                writeByte(2)
                writeString("name")
                writeString("dummy")
                writeString("placeholder")
                writeByte(3)
            }
        }
    }
}
