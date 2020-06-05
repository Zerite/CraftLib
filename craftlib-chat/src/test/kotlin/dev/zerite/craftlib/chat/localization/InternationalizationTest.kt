package dev.zerite.craftlib.chat.localization

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 * Tests that the default I18N handler produces the correct output.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class InternationalizationTest {

    companion object {
        private val default = Internationalization.instance
    }

    @Test
    fun `Test Formatting`() {
        assertEquals("test ()", default.format("test"))
        assertNotEquals("test", default.format("test"))
        assertEquals("example (one, two)", default.format("example", "one", "two"))
    }

    @Test
    fun `Test Setter`() {
        assertNotEquals("example", Internationalization.instance.format("example"))
        val new = Internationalization(set = false) { key, _ -> key }
        assertEquals("example", new.format("example"))
        Internationalization { key, args -> "$key (${args.joinToString()})" }
    }

}