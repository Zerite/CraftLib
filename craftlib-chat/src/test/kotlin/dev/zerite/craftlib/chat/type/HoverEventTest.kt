package dev.zerite.craftlib.chat.type

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 * Tests the equals methods of the hover event class.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class HoverEventTest {

    @Test
    fun `Test Equals`() {
        assertEquals(
            HoverEvent(HoverEvent.Action.SHOW_TEXT, "example"),
            HoverEvent(HoverEvent.Action.SHOW_TEXT, "example")
        )
        assertNotEquals(
            HoverEvent(HoverEvent.Action.SHOW_TEXT, "example"),
            HoverEvent(HoverEvent.Action.SHOW_ENTITY, "example")
        )
        assertNotEquals(
            HoverEvent(HoverEvent.Action.SHOW_TEXT, "example"),
            HoverEvent(HoverEvent.Action.SHOW_TEXT, "different")
        )
    }

}