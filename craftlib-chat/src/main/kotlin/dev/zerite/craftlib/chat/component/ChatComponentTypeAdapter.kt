package dev.zerite.craftlib.chat.component

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import dev.zerite.craftlib.chat.type.ChatColor
import dev.zerite.craftlib.chat.type.ClickEvent
import dev.zerite.craftlib.chat.type.HoverEvent

/**
 * Utilizes GSON to serialize and deserialize chat components from
 * their JSON representations.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
class ChatComponentTypeAdapter : TypeAdapter<BaseChatComponent>() {

    companion object : TypeAdapterFactory {

        /**
         * Registers our custom type adapter with the GSON instance.
         *
         * @param  gson     The GSON instance to assign this adapter to.
         * @param  type     The target class type we're reading from.
         *
         * @author Koding
         * @since  0.1.0-SNAPSHOT
         */
        @Suppress("UNCHECKED_CAST")
        override fun <T : Any?> create(gson: Gson, type: TypeToken<T>) =
            if (BaseChatComponent::class.java.isAssignableFrom(type.rawType))
                ChatComponentTypeAdapter() as TypeAdapter<T>
            else null
    }

    /**
     * Writes the chat component into a JSON object.
     *
     * @param  out       The JSON output which we write to.
     * @param  input     THe input component which we are converting.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    override fun write(out: JsonWriter, input: BaseChatComponent) {
        out.beginObject()

        out.write("color", input.colorImpl?.name?.toLowerCase())
        out.write("bold", input.boldImpl)
        out.write("italic", input.italicImpl)
        out.write("underlined", input.underlinedImpl)
        out.write("strikethrough", input.strikethroughImpl)
        out.write("obfuscated", input.obfuscatedImpl)
        out.write("insertion", input.insertion)

        out.write("clickEvent", input.clickEvent) {
            write("action", it.action.name.toLowerCase())
            write("value", it.value)
        }
        out.write("hoverEvent", input.hoverEvent) {
            write("action", it.action.name.toLowerCase())
            name("value")
            write(out, it.value)
        }

        out.writeArray("extra", input.siblings.toTypedArray()) { write(out, it) }

        when (input) {
            is StringChatComponent -> out.write("text", input.text)
            is TranslationChatComponent -> {
                out.write("translate", input.key)
                out.writeArray("with", input.with) { write(out, it) }
            }
            is KeybindChatComponent -> out.write("keybind", input.key)
            is ScoreChatComponent -> out.write("score", input) {
                write("name", it.name)
                write("objective", it.objective)
                write("value", it.value)
            }
            is SelectorChatComponent -> out.write("selector", input.selector)
        }

        out.endObject()
    }

    /**
     * Reads the content of the JSON fully and converts it
     * into a chat component.
     *
     * @param  reader       The JSON input.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    override fun read(reader: JsonReader) = JsonParser.parseReader(reader).asComponent()

    /**
     * Converts the inputted JSON element into a component.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun JsonElement.asComponent(): BaseChatComponent = when (this) {
        is JsonPrimitive -> StringChatComponent(toString)
        is JsonArray -> {
            assert(size() > 0)
            this[0].asComponent().also { c ->
                repeat(size() - 1) { c + this[it + 1].asComponent() }
            }
        }
        is JsonObject -> {
            val base = when {
                has("text") -> StringChatComponent(this["text"].asJsonPrimitive.toString)
                has("translate") -> TranslationChatComponent(
                    this["translate"].asString,
                    this["with"]?.asJsonArray?.map { it.asComponent() }?.toTypedArray() ?: emptyArray()
                )
                has("keybind") -> KeybindChatComponent(this["keybind"].asString)
                has("score") -> this["score"].asJsonObject.let {
                    ScoreChatComponent(
                        it["name"].asString,
                        it["objective"].asString,
                        it["value"]?.asString ?: ""
                    )
                }
                has("selector") -> SelectorChatComponent(this["selector"].asString)
                else -> StringChatComponent("")
            }

            this["bold"]?.asBoolean?.let { base.bold = it }
            this["italic"]?.asBoolean?.let { base.italic = it }
            this["underlined"]?.asBoolean?.let { base.underlined = it }
            this["strikethrough"]?.asBoolean?.let { base.strikethrough = it }
            this["obfuscated"]?.asBoolean?.let { base.obfuscated = it }
            this["bold"]?.asBoolean?.let { base.bold = it }

            this["color"]?.asString?.let { base.color = ChatColor.valueOf(it.toUpperCase()) }
            this["insertion"]?.asString?.let { base.insertion = it }

            this["clickEvent"]?.asJsonObject?.let {
                base.clickEvent = ClickEvent(
                    ClickEvent.Action.valueOf(it["action"].asString.toUpperCase()),
                    it["value"].asJsonPrimitive.toString
                )
            }

            this["hoverEvent"]?.asJsonObject?.let {
                base.hoverEvent = HoverEvent(
                    HoverEvent.Action.valueOf(it["action"].asString.toUpperCase()),
                    it["value"].asJsonObject.asComponent()
                )
            }

            this["extra"]?.asJsonArray?.takeIf { it.size() > 0 }
                ?.forEach { base + it.asComponent() }

            base
        }
        else -> error("Invalid component JSON")
    }

    /**
     * Converts a JSON primitive into a string.
     */
    val JsonPrimitive.toString: String
        get() = when {
            isBoolean -> asBoolean.toString()
            isNumber -> asNumber.toString()
            isString -> asString
            else -> error("Invalid JSON primitive")
        }

    /**
     * Writes a value to the JSON writer provided it isn't null.
     *
     * @param  name      The name of the key to write.
     * @param  value     The value to write.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun JsonWriter.write(name: String, value: Any?) =
        value?.let {
            name(name)
            when (it) {
                is Boolean -> value(it)
                is String -> value(it)
                else -> return@let
            }
        }

    /**
     * Writes data to the output into a JSON object.
     *
     * @param  name        The key to write in this new object.
     * @param  value       The object we're trying to write.
     * @param  build       Block for writing the data to the writer.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    private fun <T> JsonWriter.write(name: String, value: T?, build: JsonWriter.(T) -> Unit) =
        value?.let {
            name(name)
            beginObject()
            this.build(it)
            endObject()
        }

    /**
     * Writes array data to the output into a JSON array.
     *
     * @param  name        The key to write in this new object.
     * @param  value       The array we're trying to write.
     * @param  build       Block for writing the data to the writer.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    private fun <T> JsonWriter.writeArray(name: String, value: Array<T>?, build: JsonWriter.(T) -> Unit) =
        value?.takeIf { it.isNotEmpty() }?.let {
            name(name)
            beginArray()
            it.forEach { v -> this.build(v) }
            endArray()
        }
}

/**
 * Bare-bones reference to a simple GSON builder, which is enough
 * to read and write chat components.
 */
private val gson = GsonBuilder()
    .registerTypeAdapterFactory(ChatComponentTypeAdapter)
    .create()

/**
 * Converts the string from JSON format into a
 * chat component.
 */
val String.chatComponent: BaseChatComponent
    get() = gson.fromJson(this, BaseChatComponent::class.java)

/**
 * Converts the chat component into JSON format.
 */
val BaseChatComponent.json: String
    get() = gson.toJson(this)
