package dev.zerite.mclib.chat.component

import dev.zerite.mclib.chat.type.ChatColor
import dev.zerite.mclib.chat.type.ClickEvent
import dev.zerite.mclib.chat.type.HoverEvent

/**
 * Base class which provides all the core attributes a chat component
 * may possess.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("UNUSED")
abstract class BaseChatComponent {

    /**
     * The parent to this chat component which we'll inherit some values
     * from in the getters.
     */
    @Suppress("UNUSED")
    var parent: BaseChatComponent? = null

    /**
     * Lists all the siblings / children of this component.
     */
    var siblings = arrayListOf<BaseChatComponent>()

    /**
     * Backing implementations for the component's states, used for
     * writing them to a JSON object.
     */
    internal var boldImpl: Boolean? = null
    internal var italicImpl: Boolean? = null
    internal var underlinedImpl: Boolean? = null
    internal var strikethroughImpl: Boolean? = null
    internal var obfuscatedImpl: Boolean? = null
    internal var colorImpl: ChatColor? = null

    /**
     * Proper user-facing variables to customize this chat component.
     * These all set the proper backend representations.
     */
    var bold: Boolean
        set(value) {
            boldImpl = value
        }
        get() = boldImpl ?: parent?.bold ?: false
    var italic: Boolean
        set(value) {
            italicImpl = value
        }
        get() = italicImpl ?: parent?.italic ?: false
    var underlined: Boolean
        set(value) {
            underlinedImpl = value
        }
        get() = underlinedImpl ?: parent?.underlined ?: false
    var strikethrough: Boolean
        set(value) {
            strikethroughImpl = value
        }
        get() = strikethroughImpl ?: parent?.strikethrough ?: false
    var obfuscated: Boolean
        set(value) {
            obfuscatedImpl = value
        }
        get() = obfuscatedImpl ?: parent?.obfuscated ?: false
    var color: ChatColor
        set(value) {
            colorImpl = value
        }
        get() = colorImpl ?: parent?.color ?: ChatColor.RESET

    /**
     * Other user-facing fields which are less important or
     * commonly used.
     */
    var insertion: String? = null
        get() = field ?: parent?.insertion
    var clickEvent: ClickEvent? = null
        get() = field ?: parent?.clickEvent
    var hoverEvent: HoverEvent? = null
        get() = field ?: parent?.hoverEvent

    /**
     * Both fields provide the display text to as a base
     * when calculating the formatted and unformatted text values.
     */
    protected abstract val localUnformattedText: String
    protected open val localFormattedText: String
        get() = localUnformattedText

    /**
     * Considers both the local text and siblings and creates
     * a set of text which is unformatted.
     */
    @Suppress("UNUSED")
    val unformattedText: String
        get() {
            var text = localUnformattedText
            siblings.forEach { text += it.unformattedText }
            return text
        }

    /**
     * Considers both the local text and siblings and
     * creates a string which includes formatting codes.
     */
    @Suppress("UNUSED")
    val formattedText: String
        get() {
            var prefix = "$color"
            if (obfuscated && color != ChatColor.OBFUSCATED) prefix += ChatColor.OBFUSCATED
            if (bold && color != ChatColor.BOLD) prefix += ChatColor.BOLD
            if (strikethrough && color != ChatColor.STRIKETHROUGH) prefix += ChatColor.STRIKETHROUGH
            if (underlined && color != ChatColor.UNDERLINE) prefix += ChatColor.UNDERLINE
            if (italic && color != ChatColor.ITALIC) prefix += ChatColor.ITALIC
            if (obfuscated && color != ChatColor.OBFUSCATED) prefix += ChatColor.OBFUSCATED

            var text = "$prefix$localFormattedText"
            siblings.forEach { text += it.formattedText }
            return text
        }

    /**
     * Appends a chat component to this current instance and registers
     * it fully as a sibling.
     *
     * @param  child     The child to register.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun plus(child: BaseChatComponent) {
        child.parent = this
        siblings.add(child)
    }

    /**
     * Removes a chat component from this instance and takes away
     * the sibling.
     *
     * @param  child     The child to unregister.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    operator fun minus(child: BaseChatComponent) =
        if (child.parent === this) {
            child.parent = null
            siblings.remove(child)
        } else null

    /**
     * Returns the unformatted text representation when the
     * component is being turned into a string.
     *
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    override fun toString() = unformattedText

}