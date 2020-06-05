package dev.zerite.craftlib.chat.type

/**
 * Simple utility class which stores the old Minecraft color
 * code system and their associated character.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
enum class ChatColor(private val code: Char) {

    BLACK('0'),
    DARK_BLUE('1'),
    DARK_GREEN('2'),
    DARK_AQUA('3'),
    DARK_RED('4'),
    DARK_PURPLE('5'),
    GOLD('6'),
    GRAY('7'),
    DARK_GRAY('8'),
    BLUE('9'),
    GREEN('a'),
    AQUA('b'),
    RED('c'),
    LIGHT_PURPLE('d'),
    YELLOW('e'),
    WHITE('f'),
    OBFUSCATED('k'),
    BOLD('l'),
    STRIKETHROUGH('m'),
    UNDERLINE('n'),
    ITALIC('o'),
    RESET('r');

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