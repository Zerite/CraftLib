package dev.zerite.craftlib.protocol.packet.play.server.interaction

import dev.zerite.craftlib.protocol.packet.PacketTest
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Sent by the server when someone picks up an item lying on the ground - its sole purpose appears
 * to be the animation of the item flying towards you. It doesn't destroy the entity in the client
 * memory, and it doesn't add it to your inventory.
 *
 * The server only checks for items to be picked up after each Player Position and
 * Player Position & Look packet sent by the client.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ServerPlayCollectItemTest : PacketTest<ServerPlayCollectItemPacket>(ServerPlayCollectItemPacket) {

    init {
        example(ServerPlayCollectItemPacket(0, 0))
        example(ServerPlayCollectItemPacket(100, 200))
        example(ServerPlayCollectItemPacket(420, 69)) {
            ProtocolVersion.MC1_7_2 {
                writeInt(420)
                writeInt(69)
            }
            ProtocolVersion.MC1_8 {
                writeVarInt(420)
                writeVarInt(69)
            }
        }
    }

}
