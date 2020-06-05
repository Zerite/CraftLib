package dev.zerite.craftlib.chat.type

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests that chat colors are being correctly formatted and output
 * the right values when calling toString.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ChatColorTest {

    @Test
    fun `Test ChatColor String Output`() {
        assertEquals("§0", ChatColor.BLACK.toString())
        assertEquals("§1", ChatColor.DARK_BLUE.toString())
        assertEquals("§2", ChatColor.DARK_GREEN.toString())
        assertEquals("§3", ChatColor.DARK_AQUA.toString())
        assertEquals("§4", ChatColor.DARK_RED.toString())
        assertEquals("§5", ChatColor.DARK_PURPLE.toString())
        assertEquals("§6", ChatColor.GOLD.toString())
        assertEquals("§7", ChatColor.GRAY.toString())
        assertEquals("§8", ChatColor.DARK_GRAY.toString())
        assertEquals("§9", ChatColor.BLUE.toString())
        assertEquals("§a", ChatColor.GREEN.toString())
        assertEquals("§b", ChatColor.AQUA.toString())
        assertEquals("§c", ChatColor.RED.toString())
        assertEquals("§d", ChatColor.LIGHT_PURPLE.toString())
        assertEquals("§e", ChatColor.YELLOW.toString())
        assertEquals("§f", ChatColor.WHITE.toString())
        assertEquals("§k", ChatColor.OBFUSCATED.toString())
        assertEquals("§l", ChatColor.BOLD.toString())
        assertEquals("§m", ChatColor.STRIKETHROUGH.toString())
        assertEquals("§n", ChatColor.UNDERLINE.toString())
        assertEquals("§o", ChatColor.ITALIC.toString())
        assertEquals("§r", ChatColor.RESET.toString())
    }

    @Test
    fun `Test Stripping Colors`() {
        assertEquals("teststring", "§etest§dstring".stripColor())
        assertEquals("ExampleStr", "§fExample§dStr".stripColor())
        assertEquals("NotStripped§z", "§fNot§dStripped§z".stripColor())
    }

}