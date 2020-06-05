package dev.zerite.craftlib.chat.component

import dev.zerite.craftlib.chat.type.ChatColor
import kotlin.reflect.KMutableProperty0
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

/**
 * Tests the base chat component class for any other values
 * not already included in other's coverage.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class BaseChatComponentTest {

    @Test
    fun `Ensure Fields Match`() {
        val base = object : BaseChatComponent() {
            override val localUnformattedText = "test"
        }

        assertNull(base.parent)
        assertNull(base.insertion)

        base::insertion.checkSet("example")
        base::bold.checkSet(true)
        base::italic.checkSet(true)
        base::underlined.checkSet(true)
        base::strikethrough.checkSet(true)
        base::obfuscated.checkSet(true)
        base::color.checkSet(ChatColor.AQUA)

        val child = StringChatComponent("child")
        assert(base.siblings.isEmpty())
        base + child
        assert(base.siblings.isNotEmpty())
        base - child
        assert(base.siblings.isEmpty())

        assertEquals("test", base.toString())
    }

    /**
     * Checks that a value has been properly set.
     *
     * @param  value        The new value to set.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    private fun <T> KMutableProperty0<T>.checkSet(value: T) {
        set(value)
        assertEquals(value, get())
    }

}