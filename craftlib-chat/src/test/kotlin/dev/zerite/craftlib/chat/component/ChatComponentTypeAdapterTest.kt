package dev.zerite.craftlib.chat.component

import com.google.gson.JsonNull
import com.google.gson.JsonPrimitive
import com.google.gson.stream.JsonWriter
import dev.zerite.craftlib.chat.type.ClickEvent
import dev.zerite.craftlib.chat.type.HoverEvent
import java.io.StringWriter
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

/**
 * Tests that the chat components are being serialized and de-serialized
 * correctly, and that writing and reading is identical.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ChatComponentTypeAdapterTest {

    @Test
    fun `Test Chat Component IO`() {
        test("{\"text\":\"Sample Text\"}", StringChatComponent("Sample Text"))
        test("{\"keybind\":\"bind\"}", KeybindChatComponent("bind"))
        test(
            """
                {"score":{
                    "name":"score",
                    "objective":"obj",
                    "value":"value"
                }}
            """.trimIndent().replace("\n", "").replace(" ", "")
            , ScoreChatComponent("score", "obj", "value")
        )
        test("{\"selector\":\"selector\"}", SelectorChatComponent("selector"))
        test(
            "{\"translate\":\"key\",\"with\":[{\"text\":\"inner\"}]}",
            TranslationChatComponent("key", arrayOf(StringChatComponent("inner")))
        )
        test(
            "{\"clickEvent\":{\"action\":\"run_command\",\"value\":\"test\"},\"text\":\"example\"}",
            StringChatComponent("example").apply {
                clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, "test")
            }
        )
        test(
            "{\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"test\"},\"text\":\"example\"}",
            StringChatComponent("example").apply {
                hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, "test")
            }
        )
        test(
            "{\"extra\":[{\"text\":\"extra\"}]}",
            StringChatComponent("").apply {
                this + StringChatComponent("extra")
            }
        )
        test("[{\"text\":\"text\"}]", StringChatComponent("text"), serialize = false)
    }

    @Test
    fun `Test Exceptions`() {
        ChatComponentTypeAdapter().apply {
            assertFails { JsonNull.INSTANCE.asComponent() }
            assertFails { JsonPrimitive('a').apply {
                javaClass.getDeclaredField("value").let {
                    it.isAccessible = true
                    it.set(this, null)
                }
            }.toString }
            StringWriter().also { string ->
                val json = JsonWriter(string)
                json.write("test", 'e')
                assertEquals("", string.toString())
                string.close()
            }
        }
    }

    /**
     * Tests that chat components serialize and deserialize correctly.
     *
     * @param  json       The raw JSON output we expect.
     * @param  component  The component output we expect.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    private fun test(json: String, component: BaseChatComponent, serialize: Boolean = true) {
        if (serialize) assertEquals(json, component.json, "Component $component didn't serialize correctly")
        assertEquals(component, json.chatComponent, "Component $component wasn't de-serialized correctly")
    }

}