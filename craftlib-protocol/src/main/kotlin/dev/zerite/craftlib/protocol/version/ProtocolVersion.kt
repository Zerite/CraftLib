package dev.zerite.craftlib.protocol.version

/**
 * An enum of all Minecraft protocol versions supported by CraftLib.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
@Suppress("UNUSED")
enum class ProtocolVersion(val id: Int) {

    UNKNOWN(-1),
    MC1_7_2(4),
    MC1_7_6(5),
    MC1_8(47),
    MC1_9(107),
    MC1_9_1(108),
    MC1_9_2(109),
    MC1_9_3(110),
    MC1_10(210),
    MC1_11(315),
    MC1_11_1(316),
    MC1_12(335),
    MC1_12_1(338),
    MC1_12_2(340),
    MC1_13(393),
    MC1_13_1(401),
    MC1_13_2(404),
    MC1_14(477),
    MC1_14_1(480),
    MC1_14_2(485),
    MC1_14_3(490),
    MC1_14_4(498),
    MC1_15(573),
    MC1_15_1(575),
    MC1_15_2(578);

    companion object {
        /**
         * Contains the mapped protocol versions based on their ID.
         */
        private val mapped = hashMapOf<Int, ProtocolVersion>()

        /**
         * Retrieve a version by its ID.
         *
         * @param  version     The version for this protocol.
         * @author Koding
         * @since  0.1.0-SNAPSHOT
         */
        @JvmStatic
        operator fun get(version: Int) = mapped[version] ?: UNKNOWN

        /**
         * Initializes the mappings.
         *
         * @author Koding
         * @since  0.1.0-SNAPSHOT
         */
        init {
            for (version in values()) {
                mapped[version.id] = version
            }
        }
    }

}
