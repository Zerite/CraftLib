package dev.zerite.craftlib.chat.type

/**
 * Contains data about an action which should be fired upon
 * this component being clicked in chat.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("UNUSED")
data class ClickEvent(val action: Action, val value: String) {

    /**
     * Underlying action which shows what the value field represents, as well
     * as what the client should do with it.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    enum class Action {
        OPEN_URL,
        OPEN_FILE,
        RUN_COMMAND,
        TWITCH_USER_INFO,
        SUGGEST_COMMAND,
        CHANGE_PAGE
    }

}