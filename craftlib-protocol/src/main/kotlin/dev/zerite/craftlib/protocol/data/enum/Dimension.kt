package dev.zerite.craftlib.protocol.data.enum

import dev.zerite.craftlib.protocol.data.MappedEnum

/**
 * Contains the mappings for a vanilla dimension based on the IDs sent.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
enum class Dimension(override val id: Int) : MappedEnum {

    NETHER(-1),
    OVERWORLD(0),
    END(1);

    companion object : MappedEnum.Companion<Dimension>

}