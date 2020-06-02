package dev.zerite.mclib.chat.type

/**
 * Similarly to the click event, this tells the client what
 * to display or do upon the text being hovered over in chat.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("UNUSED")
data class HoverEvent(val action: Action, val value: String) {

    /**
     * Tells the client what to do with the provided value in relation
     * to displaying data.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    enum class Action {
        SHOW_TEXT,
        SHOW_ITEM,
        SHOW_ENTITY,
        SHOW_ACHIEVEMENT
    }

}