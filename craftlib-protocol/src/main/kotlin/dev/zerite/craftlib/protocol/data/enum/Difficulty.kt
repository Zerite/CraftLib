package dev.zerite.craftlib.protocol.data.enum

import dev.zerite.craftlib.protocol.data.MappedEnum

/**
 * Contains the vanilla difficulty values which are mapped by their
 * protocol ID.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
enum class Difficulty(override val id: Int) : MappedEnum {

    PEACEFUL(0),
    EASY(1),
    NORMAL(2),
    HARD(3);

    companion object : MappedEnum.Companion<Difficulty>

}