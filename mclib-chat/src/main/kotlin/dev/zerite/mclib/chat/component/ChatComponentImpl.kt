package dev.zerite.mclib.chat.component

import dev.zerite.mclib.chat.localization.Internationalization

/**
 * Component which stores a specific key binding.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class KeybindChatComponent(val key: String) : BaseChatComponent() {
    override val localUnformattedText = key
    override fun toString() = unformattedText
}

/**
 * Displays a score value to the client, also containing information about
 * the target's name/UUID and objective.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class ScoreChatComponent(val name: String, val objective: String, val value: String) : BaseChatComponent() {
    override val localUnformattedText = value
    override fun toString() = unformattedText
}

/**
 * Displays the results of a selector in chat, however should not
 * be sent to clients.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class SelectorChatComponent(val selector: String) : BaseChatComponent() {
    override val localUnformattedText = selector
    override fun toString() = unformattedText
}

/**
 * Simple component which displays raw text to the client whilst
 * allowing for formatting.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class StringChatComponent(val text: String) : BaseChatComponent() {
    override val localUnformattedText = text
    override fun toString() = unformattedText
}

/**
 * Chat component which indicates that a message should be translated
 * using the given key and parameters.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class TranslationChatComponent(val key: String, val with: Array<out BaseChatComponent> = emptyArray()) : BaseChatComponent() {
    override val localUnformattedText =
        Internationalization.instance.format(key, *with.map { it.unformattedText }.toTypedArray())
    override val localFormattedText =
        Internationalization.instance.format(key, *with.map { it.formattedText }.toTypedArray())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TranslationChatComponent

        if (key != other.key) return false
        if (!with.contentEquals(other.with)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + with.contentHashCode()
        return result
    }

    override fun toString() = unformattedText

}