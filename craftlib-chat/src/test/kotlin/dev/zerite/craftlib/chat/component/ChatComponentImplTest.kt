package dev.zerite.craftlib.chat.component

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 * Tests all the chat component implementations without IO.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ChatComponentImplTest {

    @Test
    fun `Test Translation`() {
        val base = TranslationChatComponent("example", arrayOf(StringChatComponent("test")))
        TranslationChatComponent("example", emptyArray()).let {
            assertNotEquals(base, it)
            assertNotEquals(base.hashCode(), it.hashCode())

            assertEquals("example ()", it.unformattedText)
            assertEquals("§rexample ()", it.formattedText)
        }
        TranslationChatComponent("example", arrayOf(StringChatComponent("test"))).let {
            assertEquals(base, it)
            assertEquals(base.hashCode(), it.hashCode())

            assertEquals("example (test)", it.unformattedText)
            assertEquals("§rexample (§rtest)", it.formattedText)
        }
    }

}