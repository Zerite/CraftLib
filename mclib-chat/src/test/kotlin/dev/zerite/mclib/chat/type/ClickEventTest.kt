package dev.zerite.mclib.chat.type

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 * Tests the equals methods of the click event class.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ClickEventTest {

    @Test
    fun `Test Equals`() {
        assertEquals(
            ClickEvent(ClickEvent.Action.CHANGE_PAGE, "example"),
            ClickEvent(ClickEvent.Action.CHANGE_PAGE, "example")
        )
        assertNotEquals(
            ClickEvent(ClickEvent.Action.CHANGE_PAGE, "example"),
            ClickEvent(ClickEvent.Action.OPEN_URL, "example")
        )
        assertNotEquals(
            ClickEvent(ClickEvent.Action.CHANGE_PAGE, "example"),
            ClickEvent(ClickEvent.Action.CHANGE_PAGE, "different")
        )
    }

}