package dev.zerite.mclib.protocol.data.enum

import dev.zerite.mclib.protocol.data.MappedEnum

/**
 * Contains the vanilla game modes which are sent during the join
 * game packet.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
enum class Gamemode(override val id: Int) : MappedEnum {

    SURVIVAL(0),
    CREATIVE(1),
    ADVENTURE(2);

    companion object : MappedEnum.Companion<Gamemode>

}