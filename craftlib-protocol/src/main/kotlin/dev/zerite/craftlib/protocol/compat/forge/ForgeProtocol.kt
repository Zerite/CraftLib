package dev.zerite.craftlib.protocol.compat.forge

import dev.zerite.craftlib.protocol.Packet
import dev.zerite.craftlib.protocol.compat.forge.packet.client.ClientForgeHandshakeHelloPacket
import dev.zerite.craftlib.protocol.compat.forge.packet.global.GlobalForgeHandshakeAcknowledgePacket
import dev.zerite.craftlib.protocol.compat.forge.packet.global.GlobalForgeHandshakeModListPacket
import dev.zerite.craftlib.protocol.compat.forge.packet.server.ServerForgeHandshakeHelloPacket
import dev.zerite.craftlib.protocol.compat.forge.packet.server.ServerForgeHandshakeModIdDataPacket
import dev.zerite.craftlib.protocol.compat.forge.packet.server.ServerForgeHandshakeRegistryDataPacket
import dev.zerite.craftlib.protocol.compat.forge.packet.server.ServerForgeHandshakeResetPacket
import dev.zerite.craftlib.protocol.version.AbstractProtocol
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Contains all the mappings for the Forge protocol and methods to write
 * and read the packets.
 *
 * @author Koding
 * @since  0.1.2
 */
@Suppress("UNUSED")
object ForgeProtocol : AbstractProtocol() {

    /**
     * Stores the mappings for the Forge handshake plugin message channel.
     */
    @JvmField
    val HANDSHAKE = protocol("Forge Handshake", "FML|HS") {
        clientbound {
            ServerForgeHandshakeHelloPacket {
                ProtocolVersion.MC1_7_2 to 0x00
            }
            GlobalForgeHandshakeModListPacket {
                ProtocolVersion.MC1_7_2 to 0x02
            }
            ServerForgeHandshakeModIdDataPacket {
                ProtocolVersion.MC1_7_2 to 0x03
            }
            ServerForgeHandshakeRegistryDataPacket {
                ProtocolVersion.MC1_8 to 0x03
            }
            GlobalForgeHandshakeAcknowledgePacket {
                ProtocolVersion.MC1_7_2 to -0x1
            }
            ServerForgeHandshakeResetPacket {
                ProtocolVersion.MC1_7_2 to -0x2
            }
        }
        serverbound {
            ClientForgeHandshakeHelloPacket {
                ProtocolVersion.MC1_7_2 to 0x01
            }
            GlobalForgeHandshakeModListPacket {
                ProtocolVersion.MC1_7_2 to 0x02
            }
            GlobalForgeHandshakeAcknowledgePacket {
                ProtocolVersion.MC1_7_2 to -0x1
            }
        }
    }

}

/**
 * Base class for all Forge packets to implement.
 *
 * @author Koding
 * @since  0.1.2
 */
open class ForgePacket : Packet()
