@file:JvmName("ChatColorUtil")
package dev.zerite.craftlib.chat.type

/**
 * Simple utility class which stores the old Minecraft color
 * code system and their associated character.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
enum class ChatColor(val code: Char, val color: Int?) {

    BLACK('0', 0x000000),
    DARK_BLUE('1', 0x0000AA),
    DARK_GREEN('2', 0x00AA00),
    DARK_AQUA('3', 0x00AAAA),
    DARK_RED('4', 0xAA0000),
    DARK_PURPLE('5', 0xAA00AA),
    GOLD('6', 0xFFAA00),
    GRAY('7', 0xAAAAAA),
    DARK_GRAY('8', 0x555555),
    BLUE('9', 0x5555FF),
    GREEN('a', 0x55FF55),
    AQUA('b', 0x55FFFF),
    RED('c', 0xFF5555),
    LIGHT_PURPLE('d', 0xFF55FF),
    YELLOW('e', 0xFFFF55),
    WHITE('f', 0xFFFFFF),
    OBFUSCATED('k', null),
    BOLD('l', null),
    STRIKETHROUGH('m', null),
    UNDERLINE('n', null),
    ITALIC('o', null),
    RESET('r', null);

    /**
     * Prints this formatting code prefixed with the formatting indicator.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    override fun toString() = "§$code"

}

/**
 * The regular expression which can be used to strip all color codes
 * from a string.
 */
private val regex = "§([0-9a-fklmnor])".toRegex()

/**
 * Strips all the color codes from the string and returns the
 * stripped version.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
fun String.stripColor() = replace(regex, "")