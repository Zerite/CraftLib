package dev.zerite.craftlib.chat.localization

/**
 * Provides a simple function to allow translation keys and their
 * arguments to be converted into a proper display string.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
interface Internationalization {

    /**
     * Request translation of a localization key and arguments
     * into a proper string which can be displayed to the user.
     *
     * @param  key       The key to use when looking up a translation.
     * @param  args      Any arguments which the translation requires.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    fun format(key: String, vararg args: String): String

    companion object {
        /**
         * Global instance reference which should be accessible to any
         * class which requires translation.
         */
        @Suppress("UNUSED")
        var instance = object : Internationalization {
            override fun format(key: String, vararg args: String) = "$key (${args.joinToString()})"
        }

        /**
         * Initializes the instance with a provider for internationalization.
         *
         * @param  format       The formatter for converting translation keys.
         * @author Koding
         * @since  0.1.0-SNAPSHOT
         */
        @JvmStatic
        operator fun invoke(
            set: Boolean = true,
            format: I18nFormatter
        ) = object : Internationalization {
            override fun format(key: String, vararg args: String) = format(key, args)
        }.apply { if (set) instance = this }
    }

}

/**
 * Alias for the formatter which is used to set the
 * internationalization instance.
 *
 * @author Koding
 * @since  0.1.2
 */
typealias I18nFormatter = (key: String, args: Array<out String>) -> String