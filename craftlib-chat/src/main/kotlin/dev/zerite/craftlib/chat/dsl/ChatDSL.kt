package dev.zerite.craftlib.chat.dsl

import dev.zerite.craftlib.chat.component.*
import dev.zerite.craftlib.chat.type.ChatColor
import dev.zerite.craftlib.chat.type.ClickEvent
import dev.zerite.craftlib.chat.type.HoverEvent

/**
 * Marker to indicate that an object is a part of the chat DSL.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@DslMarker
annotation class ChatDSL

/**
 * Utility for applying additional formatting to a chat component.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
enum class ChatFormat(val build: BaseChatComponent.() -> Unit) {
    BOLD({ bold = true }),
    ITALIC({ italic = true }),
    UNDERLINED({ underlined = true }),
    STRIKETHROUGH({ strikethrough = true }),
    OBFUSCATED({ obfuscated = true }),
}

@Suppress("UNUSED")
class ChatBuilder {

    /**
     * Extracts the enum values for chat formatting into easily
     * accessible variables.
     */
    val bold = ChatFormat.BOLD
    val italic = ChatFormat.ITALIC
    val underlined = ChatFormat.UNDERLINED
    val strikethrough = ChatFormat.STRIKETHROUGH
    val obfuscated = ChatFormat.OBFUSCATED

    /**
     * Creates a string component using the given text.
     *
     * @param  text        The text to include in the component.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun string(text: String) = StringChatComponent(text)

    /**
     * Creates a key binding chat component with the given translation
     * key code.
     *
     * @param  key         The key's internally registered name to the client.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun key(key: String) = KeybindChatComponent(key)

    /**
     * Creates a score chat component.
     *
     * @param  name        The score's name.
     * @param  objective   The scoreboard objective's name which we are referencing.
     * @param  value       The value to display in chat.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun score(name: String, objective: String, value: String) = ScoreChatComponent(name, objective, value)

    /**
     * Creates a selector chat component using the provided type.
     *
     * @param  selector    The selector to use for this component.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun selector(selector: String) = SelectorChatComponent(selector)

    /**
     * Creates a translation chat component, optionally using other
     * components to be formatted into this translation.
     *
     * @param  key         The translation key which the client should use.
     * @param  with        Any additional components which should be included in the translation.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun translation(key: String, vararg with: BaseChatComponent) = TranslationChatComponent(key, with)

    /**
     * Creates a click event using the provided action and value.
     *
     * @param  action      The action to execute when the component is clicked in chat.
     * @param  value       The associated value which is used for the action.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun click(action: ClickEvent.Action, value: String) = ClickEvent(action, value)

    /**
     * Creates a hover event using the provided action and value.
     *
     * @param  action      The action to use which tells the client how to display the value.
     * @param  value       The value which we need to display.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun hover(action: HoverEvent.Action, value: BaseChatComponent) = HoverEvent(action, value)

    /**
     * Applies a chat formatting code to the chat component.
     *
     * @param  other       The chat formatting to apply.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @ChatDSL
    operator fun BaseChatComponent.plus(other: ChatFormat) = apply { other.build(this) }

    /**
     * Sets the chat color which should be used for this component.
     *
     * @param  other      The color to apply.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @ChatDSL
    operator fun BaseChatComponent.plus(other: ChatColor) = apply { color = other }

    /**
     * Sets the chat component's click event value.
     *
     * @param  value      The new click event value to set.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @ChatDSL
    infix fun BaseChatComponent.onClick(value: ClickEvent?) = apply { clickEvent = value }

    /**
     * Sets the chat component's hover event value.
     *
     * @param  value      The new hover event value to set.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @ChatDSL
    infix fun BaseChatComponent.onHover(value: HoverEvent?) = apply { hoverEvent = value }

    /**
     * Adds another chat component as a child to the
     * current component.
     *
     * @param  other      The other chat component to add as a child.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @ChatDSL
    infix fun BaseChatComponent.with(other: BaseChatComponent) = apply { this + other }

    /**
     * Sets whether this chat component is bold.
     *
     * @param  value          Whether this component is bold.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @ChatDSL
    infix fun BaseChatComponent.bold(value: Boolean) = apply { bold = value }

    /**
     * Sets whether this chat component is italic.
     *
     * @param  value          Whether this component is italic.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @ChatDSL
    infix fun BaseChatComponent.italic(value: Boolean) = apply { italic = value }

    /**
     * Sets whether this chat component is underlined.
     *
     * @param  value          Whether this component is underlined.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @ChatDSL
    infix fun BaseChatComponent.underlined(value: Boolean) = apply { underlined = value }

    /**
     * Sets whether this chat component has strikethrough.
     *
     * @param  value          Whether this component should have strikethrough.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @ChatDSL
    infix fun BaseChatComponent.strikethrough(value: Boolean) = apply { strikethrough = value }

    /**
     * Sets whether this chat component should be obfuscated.
     *
     * @param  value          Whether this component should be obfuscated.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    @ChatDSL
    infix fun BaseChatComponent.obfuscated(value: Boolean) = apply { obfuscated = value }

}

/**
 * Creates a new chat builder which will result in
 * a formatted chat component.
 *
 * @param  build         Builder function which creates a new chat component.
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@ChatDSL
fun chat(build: ChatBuilder.() -> BaseChatComponent) = ChatBuilder().build()
