package dev.zerite.craftlib.protocol.data.registry

import dev.zerite.craftlib.protocol.data.registry.impl.*
import dev.zerite.craftlib.protocol.version.ProtocolVersion

/**
 * Registry for storing the data about all the protocol-related
 * enums and their mappings.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
object MagicRegistry {

    /**
     * Mappings for the difficulty values.
     */
    val difficulty = create<MagicDifficulty> {
        ProtocolVersion.MC1_7_2 {
            MagicDifficulty.PEACEFUL to 0
            MagicDifficulty.EASY to 1
            MagicDifficulty.NORMAL to 2
            MagicDifficulty.HARD to 3
        }
    }

    /**
     * Mappings for the dimension values.
     */
    val dimension = create<MagicDimension> {
        ProtocolVersion.MC1_7_2 {
            MagicDimension.NETHER to -1
            MagicDimension.OVERWORLD to 0
            MagicDimension.END to 1
        }
    }

    /**
     * Mappings for the gamemode values.
     */
    val gamemode = create<MagicGamemode> {
        ProtocolVersion.MC1_7_2 {
            MagicGamemode.SURVIVAL to 0
            MagicGamemode.CREATIVE to 1
            MagicGamemode.ADVENTURE to 2
        }
        ProtocolVersion.MC1_8 {
            MagicGamemode.SPECTATOR to 3
        }
    }

    /**
     * Stores the reason for the game state update packet.
     */
    val gameStateReason = create<MagicGameStateReason> {
        ProtocolVersion.MC1_7_2 {
            MagicGameStateReason.INVALID_BED to 0
            MagicGameStateReason.BEGIN_RAINING to 1
            MagicGameStateReason.END_RAINING to 2
            MagicGameStateReason.CHANGE_GAME_MODE to 3
            MagicGameStateReason.ENTER_CREDITS to 4
            MagicGameStateReason.DEMO_MESSAGES to 5
            MagicGameStateReason.BOW_HIT_SOUND to 6
            MagicGameStateReason.FADE_VALUE to 7
            MagicGameStateReason.FADE_TIME to 8
        }
    }

    /**
     * Stores the possible actions for the update block entity packet.
     */
    val blockEntityUpdateAction = create<MagicBlockEntityUpdateAction> {
        ProtocolVersion.MC1_7_2 {
            MagicBlockEntityUpdateAction.SET_SPAWNER_MOB to 1
        }
        ProtocolVersion.MC1_7_6 {
            MagicBlockEntityUpdateAction.SET_SPAWNER_MOB to 1
            MagicBlockEntityUpdateAction.SET_COMMAND_BLOCK_TEXT to 2
            MagicBlockEntityUpdateAction.SET_MOB_HEAD to 3
            MagicBlockEntityUpdateAction.SET_FLOWER_POT to 4
        }
        ProtocolVersion.MC1_8 {
            MagicBlockEntityUpdateAction.SET_SPAWNER_MOB to 1
            MagicBlockEntityUpdateAction.SET_COMMAND_BLOCK_TEXT to 2
            MagicBlockEntityUpdateAction.SET_BEACON_DATA to 3
            MagicBlockEntityUpdateAction.SET_MOB_HEAD to 4
            MagicBlockEntityUpdateAction.SET_FLOWER_POT to 5
            MagicBlockEntityUpdateAction.SET_BANNER to 6
        }
    }

    /**
     * Stores the possible animations for the animation packet.
     */
    val animation = create<MagicAnimation> {
        ProtocolVersion.MC1_7_2 {
            MagicAnimation.SWING_ARM to 0
            MagicAnimation.DAMAGE_ANIMATION to 1
            MagicAnimation.LEAVE_BED to 2
            MagicAnimation.EAT_FOOD to 3
            MagicAnimation.CRITICAL_EFFECT to 4
            MagicAnimation.MAGIC_CRITICAL_EFFECT to 5
            MagicAnimation.CROUCH to 104
            MagicAnimation.UNCROUCH to 105
        }
    }

    /**
     * Stores the possible objects for the spawn object packet.
     */
    val objects = create<MagicObject> {
        ProtocolVersion.MC1_7_2 {
            MagicObject.BOAT to 1
            MagicObject.ITEM_STACK to 2
            MagicObject.MINECART to 10
            MagicObject.MINECART_STORAGE to 11
            MagicObject.MINECART_POWERED to 12
            MagicObject.ACTIVATED_TNT to 50
            MagicObject.ENDER_CRYSTAL to 51
            MagicObject.ARROW to 60
            MagicObject.SNOWBALL to 61
            MagicObject.EGG to 62
            MagicObject.FIREBALL to 63
            MagicObject.FIRE_CHARGE to 64
            MagicObject.THROWN_ENDER_PEARL to 65
            MagicObject.WITHER_SKULL to 66
            MagicObject.FALLING_OBJECT to 70
            MagicObject.ITEM_FRAME to 71
            MagicObject.EYE_OF_ENDER to 72
            MagicObject.THROWN_POTION to 73
            MagicObject.FALLING_DRAGON_EGG to 74
            MagicObject.THROWN_EXP_BOTTLE to 75
            MagicObject.FISHING_FLOAT to 90
        }
        ProtocolVersion.MC1_8 {
            MagicObject.FIREWORK_ROCKET to 76
            MagicObject.LEASH_KNOT to 77
            MagicObject.ARMOR_STAND to 78
        }
    }

    /**
     * Stores the valid entity status values for the entity status packet.
     */
    val entityStatus = create<MagicEntityStatus> {
        ProtocolVersion.MC1_7_2 {
            MagicEntityStatus.ENTITY_HURT to 2
            MagicEntityStatus.ENTITY_DEAD to 3
            MagicEntityStatus.WOLF_TAMING to 6
            MagicEntityStatus.WOLF_TAMED to 7
            MagicEntityStatus.WOLF_SHAKING_WATER to 8
            MagicEntityStatus.EATING_ACCEPTED to 9
            MagicEntityStatus.SHEEP_EATING_GRASS to 10
            MagicEntityStatus.IRON_GOLEM_ROSE to 11
            MagicEntityStatus.VILLAGER_HEARTS to 12
            MagicEntityStatus.VILLAGER_ANGRY to 13
            MagicEntityStatus.VILLAGER_HAPPY to 14
            MagicEntityStatus.WITCH_MAGIC to 15
            MagicEntityStatus.CONVERT_ZOMBIE_VILLAGER to 16
            MagicEntityStatus.FIREWORK_EXPLODE to 17
        }
    }

    /**
     * Stores the values for a potion effect.
     */
    val potionEffect = create<MagicPotionEffect> {
        ProtocolVersion.MC1_7_2 {
            MagicPotionEffect.SPEED to 1
            MagicPotionEffect.SLOWNESS to 2
            MagicPotionEffect.HASTE to 3
            MagicPotionEffect.MINING_FATIGUE to 4
            MagicPotionEffect.STRENGTH to 5
            MagicPotionEffect.INSTANT_HEALTH to 6
            MagicPotionEffect.INSTANT_DAMAGE to 7
            MagicPotionEffect.JUMP_BOOST to 8
            MagicPotionEffect.NAUSEA to 9
            MagicPotionEffect.REGENERATION to 10
            MagicPotionEffect.RESISTANCE to 11
            MagicPotionEffect.FIRE_RESISTANCE to 12
            MagicPotionEffect.WATER_BREATHING to 13
            MagicPotionEffect.INVISIBILITY to 14
            MagicPotionEffect.BLINDNESS to 15
            MagicPotionEffect.NIGHT_VISION to 16
            MagicPotionEffect.HUNGER to 17
            MagicPotionEffect.WEAKNESS to 18
            MagicPotionEffect.POISON to 19
            MagicPotionEffect.WITHER to 20
            MagicPotionEffect.HEALTH_BOOST to 21
            MagicPotionEffect.ABSORPTION to 22
            MagicPotionEffect.SATURATION to 23
        }
    }

    /**
     * Stores the mappings for the inventory type magic value.
     */
    val inventoryType = create<MagicInventoryType> {
        ProtocolVersion.MC1_7_2 {
            MagicInventoryType.CHEST to 0
            MagicInventoryType.WORKBENCH to 1
            MagicInventoryType.FURNACE to 2
            MagicInventoryType.DISPENSER to 3
            MagicInventoryType.ENCHANTMENT_TABLE to 4
            MagicInventoryType.BREWING_STAND to 5
            MagicInventoryType.VILLAGER to 6
            MagicInventoryType.BEACON to 7
            MagicInventoryType.ANVIL to 8
            MagicInventoryType.HOPPER to 9
            MagicInventoryType.DROPPER to 10
            MagicInventoryType.HORSE to 11
        }
        ProtocolVersion.MC1_8 {
            MagicInventoryType.CHEST to "minecraft:chest"
            MagicInventoryType.WORKBENCH to "minecraft:crafting_table"
            MagicInventoryType.FURNACE to "minecraft:furnace"
            MagicInventoryType.DISPENSER to "minecraft:dispenser"
            MagicInventoryType.ENCHANTMENT_TABLE to "minecraft:enchanting_table"
            MagicInventoryType.BREWING_STAND to "minecraft:brewing_stand"
            MagicInventoryType.VILLAGER to "minecraft:villager"
            MagicInventoryType.BEACON to "minecraft:beacon"
            MagicInventoryType.ANVIL to "minecraft:anvil"
            MagicInventoryType.HOPPER to "minecraft:hopper"
            MagicInventoryType.DROPPER to "minecraft:dropper"
            MagicInventoryType.HORSE to "EntityHorse"
        }
    }

    /**
     * Stores the mappings for the scoreboard action values in the objective packet.
     */
    val scoreboardAction = create<MagicScoreboardAction> {
        ProtocolVersion.MC1_7_2 {
            MagicScoreboardAction.CREATE_SCOREBOARD to 0
            MagicScoreboardAction.REMOVE_SCOREBOARD to 1
            MagicScoreboardAction.UPDATE_TEXT to 2
        }
    }

    /**
     * Contains version specific mappings for the scoreboard display positions.
     */
    val scoreboardPosition = create<MagicScoreboardPosition> {
        ProtocolVersion.MC1_7_2 {
            MagicScoreboardPosition.LIST to 0
            MagicScoreboardPosition.SIDEBAR to 1
            MagicScoreboardPosition.BELOW_NAME to 2
        }
    }

    /**
     * Version mappings for the team mode value in the teams packet.
     */
    val teamMode = create<MagicTeamMode> {
        ProtocolVersion.MC1_7_2 {
            MagicTeamMode.CREATE_TEAM to 0
            MagicTeamMode.REMOVE_TEAM to 1
            MagicTeamMode.UPDATE_INFO to 2
            MagicTeamMode.ADD_PLAYERS to 3
            MagicTeamMode.REMOVE_PLAYERS to 4
        }
    }

    /**
     * Version mappings for the team friendly fire values.
     */
    val teamFriendlyFire = create<MagicTeamFriendlyFire> {
        ProtocolVersion.MC1_7_2 {
            MagicTeamFriendlyFire.OFF to 0
            MagicTeamFriendlyFire.ON to 1
            MagicTeamFriendlyFire.SEE_FRIENDLY_INVISIBLES to 3
        }
    }

    /**
     * Vanilla values which the client status packet can send.
     */
    val clientStatus = create<MagicClientStatus> {
        ProtocolVersion.MC1_7_2 {
            MagicClientStatus.PERFORM_RESPAWN to 0
            MagicClientStatus.REQUEST_STATS to 1
            MagicClientStatus.OPEN_INVENTORY_ACHIEVEMENT to 2
        }
    }

    /**
     * Vanilla values which the player digging status packet reads.
     */
    val playerDiggingStatus = create<MagicPlayerDiggingStatus> {
        ProtocolVersion.MC1_7_2 {
            MagicPlayerDiggingStatus.STARTED_DIGGING to 0
            MagicPlayerDiggingStatus.CANCELLED_DIGGING to 1
            MagicPlayerDiggingStatus.FINISHED_DIGGING to 2
            MagicPlayerDiggingStatus.DROP_ITEM_STACK to 3
            MagicPlayerDiggingStatus.DROP_ITEM to 4
            MagicPlayerDiggingStatus.SHOOT_ARROW to 5
        }
    }

    /**
     * Stores the possible animations for the animation packet.
     */
    val clientAnimation = create<MagicClientAnimation> {
        ProtocolVersion.MC1_7_2 {
            MagicClientAnimation.NO_ANIMATION to 0
            MagicClientAnimation.SWING_ARM to 1
            MagicClientAnimation.DAMAGE_ANIMATION to 2
            MagicClientAnimation.LEAVE_BED to 3
            MagicClientAnimation.EAT_FOOD to 5
            MagicClientAnimation.CRITICAL_EFFECT to 6
            MagicClientAnimation.MAGIC_CRITICAL_EFFECT to 7
            MagicClientAnimation.CROUCH to 104
            MagicClientAnimation.UNCROUCH to 105
        }
    }

    /**
     * Stores the values for the entity action packet.
     */
    val entityAction = create<MagicEntityAction> {
        ProtocolVersion.MC1_7_2 {
            MagicEntityAction.CROUCH to 1
            MagicEntityAction.UNCROUCH to 2
            MagicEntityAction.LEAVE_BED to 3
            MagicEntityAction.START_SPRINTING to 4
            MagicEntityAction.STOP_SPRINTING to 5
        }
        ProtocolVersion.MC1_8 {
            MagicEntityAction.CROUCH to 0
            MagicEntityAction.UNCROUCH to 1
            MagicEntityAction.LEAVE_BED to 2
            MagicEntityAction.START_SPRINTING to 3
            MagicEntityAction.STOP_SPRINTING to 4
            MagicEntityAction.HORSE_JUMP to 5
            MagicEntityAction.OPEN_INVENTORY to 6
        }
    }

    /**
     * Stores the values for view distance.
     */
    val viewDistance = create<MagicViewDistance> {
        ProtocolVersion.MC1_7_2 {
            MagicViewDistance.FAR to 0
            MagicViewDistance.NORMAL to 1
            MagicViewDistance.SHORT to 2
            MagicViewDistance.TINY to 3
        }
    }

    /**
     * Maps the mob IDs to their type names.
     */
    val mobType = create<MagicMobType> {
        ProtocolVersion.MC1_7_2 {
            MagicMobType.CREEPER to 50
            MagicMobType.SKELETON to 51
            MagicMobType.SPIDER to 52
            MagicMobType.GIANT_ZOMBIE to 53
            MagicMobType.ZOMBIE to 54
            MagicMobType.SLIME to 55
            MagicMobType.GHAST to 56
            MagicMobType.ZOMBIE_PIGMAN to 57
            MagicMobType.ENDERMAN to 58
            MagicMobType.CAVE_SPIDER to 59
            MagicMobType.SILVERFISH to 60
            MagicMobType.BLAZE to 61
            MagicMobType.MAGMA_CUBE to 62
            MagicMobType.ENDER_DRAGON to 63
            MagicMobType.WITHER to 64
            MagicMobType.BAT to 65
            MagicMobType.WITCH to 66
            MagicMobType.PIG to 90
            MagicMobType.SHEEP to 91
            MagicMobType.COW to 92
            MagicMobType.CHICKEN to 93
            MagicMobType.SQUID to 94
            MagicMobType.WOLF to 95
            MagicMobType.MOOSHROOM to 96
            MagicMobType.SNOWMAN to 97
            MagicMobType.OCELOT to 98
            MagicMobType.IRON_GOLEM to 99
            MagicMobType.HORSE to 100
            MagicMobType.VILLAGER to 120
        }
        ProtocolVersion.MC1_8 {
            MagicMobType.MOB to 48
            MagicMobType.MONSTER to 49
            MagicMobType.ENDERMITE to 67
            MagicMobType.GUARDIAN to 68
            MagicMobType.RABBIT to 101
        }
    }

    /**
     * Valid properties which can be attached to an entity.
     */
    val entityProperty = create<MagicEntityProperty> {
        ProtocolVersion.MC1_7_2 {
            MagicEntityProperty.GENERIC_MAX_HEALTH to "generic.maxHealth"
            MagicEntityProperty.GENERIC_FOLLOW_RANGE to "generic.followRange"
            MagicEntityProperty.GENERIC_KNOCKBACK_RESISTANCE to "generic.knockbackResistance"
            MagicEntityProperty.GENERIC_MOVEMENT_SPEED to "generic.movementSpeed"
            MagicEntityProperty.GENERIC_ATTACK_DAMAGE to "generic.attackDamage"
            MagicEntityProperty.HORSE_JUMP_STRENGTH to "horse.jumpStrength"
            MagicEntityProperty.ZOMBIE_SPAWN_REINFORCEMENTS to "zombie.spawnReinforcements"
        }
    }

    /**
     * Maps where a chat message will be displayed.
     */
    val chatPosition = create<MagicChatPosition> {
        ProtocolVersion.MC1_8 {
            MagicChatPosition.CHAT to 0
            MagicChatPosition.SYSTEM_MESSAGE to 1
            MagicChatPosition.ACTION_BAR to 2
        }
    }

    /**
     * Stores the mappings for different particles.
     */
    val particleType = create<MagicParticleType> {
        ProtocolVersion.MC1_7_2 {
            MagicParticleType.EXPLOSION_NORMAL to "explode"
            MagicParticleType.EXPLOSION_LARGE to "largeexplode"
            MagicParticleType.EXPLOSION_HUGE to "hugeexplosion"
            MagicParticleType.FIREWORKS_SPARK to "fireworksSpark"
            MagicParticleType.WATER_BUBBLE to "bubble"
            MagicParticleType.WATER_SPLASH to "splash"
            MagicParticleType.WATER_WAKE to "wake"
            MagicParticleType.SUSPENDED to "suspended"
            MagicParticleType.SUSPENDED_DEPTH to "depthsuspend"
            MagicParticleType.CRITICAL to "crit"
            MagicParticleType.CRITICAL_MAGIC to "magicCrit"
            MagicParticleType.SMOKE_NORMAL to "smoke"
            MagicParticleType.SMOKE_LARGE to "largesmoke"
            MagicParticleType.SPELL to "spell"
            MagicParticleType.SPELL_INSTANT to "instantSpell"
            MagicParticleType.SPELL_MOB to "mobSpell"
            MagicParticleType.SPELL_MOB_AMBIENT to "mobSpellAmbient"
            MagicParticleType.SPELL_WITCH to "witchMagic"
            MagicParticleType.DRIP_WATER to "dripWater"
            MagicParticleType.DRIP_LAVA to "dripLava"
            MagicParticleType.VILLAGER_ANGRY to "angryVillager"
            MagicParticleType.VILLAGER_HAPPY to "happyVillager"
            MagicParticleType.TOWN_AURA to "townaura"
            MagicParticleType.NOTE to "note"
            MagicParticleType.PORTAL to "portal"
            MagicParticleType.ENCHANTMENT_TABLE to "enchantmenttable"
            MagicParticleType.FLAME to "flame"
            MagicParticleType.LAVA to "lava"
            MagicParticleType.FOOTSTEP to "footstep"
            MagicParticleType.CLOUD to "cloud"
            MagicParticleType.REDSTONE to "reddust"
            MagicParticleType.SNOWBALL to "snowballpoof"
            MagicParticleType.SNOW_SHOVEL to "snowshovel"
            MagicParticleType.SLIME to "slime"
            MagicParticleType.HEART to "heart"
            MagicParticleType.BARRIER to "barrier"
            MagicParticleType.ITEM_CRACK to "iconcrack_"
            MagicParticleType.BLOCK_CRACK to "blockcrack_"
            MagicParticleType.BLOCK_DUST to "blockdust_"
            MagicParticleType.WATER_DROP to "droplet"
            MagicParticleType.ITEM_TAKE to "take"
            MagicParticleType.MOB_APPEARANCE to "mobappearance"
        }
        ProtocolVersion.MC1_8 {
            MagicParticleType.EXPLOSION_NORMAL to 0
            MagicParticleType.EXPLOSION_LARGE to 1
            MagicParticleType.EXPLOSION_HUGE to 2
            MagicParticleType.FIREWORKS_SPARK to 3
            MagicParticleType.WATER_BUBBLE to 4
            MagicParticleType.WATER_WAKE to 5
            MagicParticleType.WATER_SPLASH to 6
            MagicParticleType.SUSPENDED to 7
            MagicParticleType.TOWN_AURA to 8
            MagicParticleType.CRITICAL to 9
            MagicParticleType.CRITICAL_MAGIC to 10
            MagicParticleType.SMOKE_NORMAL to 11
            MagicParticleType.SMOKE_LARGE to 12
            MagicParticleType.SPELL_MOB to 13
            MagicParticleType.SPELL_INSTANT to 14
            MagicParticleType.SPELL to 15
            MagicParticleType.SPELL_WITCH to 17
            MagicParticleType.DRIP_WATER to 18
            MagicParticleType.DRIP_LAVA to 19
            MagicParticleType.VILLAGER_ANGRY to 20
            MagicParticleType.VILLAGER_HAPPY to 21
            MagicParticleType.SUSPENDED_DEPTH to 22
            MagicParticleType.NOTE to 23
            MagicParticleType.PORTAL to 24
            MagicParticleType.ENCHANTMENT_TABLE to 25
            MagicParticleType.FLAME to 26
            MagicParticleType.LAVA to 27
            MagicParticleType.FOOTSTEP to 28
            MagicParticleType.CLOUD to 29
            MagicParticleType.REDSTONE to 30
            MagicParticleType.SNOWBALL to 31
            MagicParticleType.SNOW_SHOVEL to 32
            MagicParticleType.SLIME to 33
            MagicParticleType.HEART to 34
            MagicParticleType.BARRIER to 35
            MagicParticleType.ITEM_CRACK to 36
            MagicParticleType.BLOCK_CRACK to 37
            MagicParticleType.BLOCK_DUST to 38
            MagicParticleType.WATER_DROP to 49
            MagicParticleType.ITEM_TAKE to 40
            MagicParticleType.MOB_APPEARANCE to 41
        }
    }

    /**
     * Determines when a teams name tag should be visible.
     */
    val teamNameTagVisibility = create<MagicTeamNameTagVisibility> {
        ProtocolVersion.MC1_8 {
            MagicTeamNameTagVisibility.ALWAYS to "always"
            MagicTeamNameTagVisibility.HIDE_FOR_OTHER_TEAMS to "hideForOtherTeams"
            MagicTeamNameTagVisibility.HIDE_FOR_OWN_TEAM to "hideForOwnTeam"
            MagicTeamNameTagVisibility.NEVER to "never"
        }
    }

    /**
     * Sent to the client to update their combat status.
     */
    val combatEvent = create<MagicCombatEvent> {
        ProtocolVersion.MC1_8 {
            MagicCombatEvent.ENTER_COMBAT to 0
            MagicCombatEvent.END_COMBAT to 1
            MagicCombatEvent.ENTITY_DEAD to 2
        }
    }

    /**
     * Controls the functionality of the world border packet.
     */
    val worldBorderAction = create<MagicWorldBorderAction> {
        ProtocolVersion.MC1_8 {
            MagicWorldBorderAction.SET_SIZE to 0
            MagicWorldBorderAction.LERP_SIZE to 1
            MagicWorldBorderAction.SET_CENTER to 2
            MagicWorldBorderAction.INITIALIZE to 3
            MagicWorldBorderAction.SET_WARNING_TIME to 4
            MagicWorldBorderAction.SET_WARNING_BLOCKS to 5
        }
    }

    /**
     * Defines the action which the title packet will execute.
     */
    val titleAction = create<MagicTitleAction> {
        ProtocolVersion.MC1_8 {
            MagicTitleAction.TITLE to 0
            MagicTitleAction.SUBTITLE to 1
            MagicTitleAction.TIMES to 2
            MagicTitleAction.CLEAR to 3
            MagicTitleAction.RESET to 4
        }
    }

    /**
     * Controls how the use entity packet works.
     */
    val useEntityType = create<MagicUseEntityType> {
        ProtocolVersion.MC1_7_2 {
            MagicUseEntityType.INTERACT to 0
            MagicUseEntityType.ATTACK to 1
            MagicUseEntityType.INTERACT_AT to 2
        }
    }

    /**
     * Indicates the result of the resource pack being applied.
     */
    val resourcePackResult = create<MagicResourcePackResult> {
        ProtocolVersion.MC1_8 {
            MagicResourcePackResult.LOADED to 0
            MagicResourcePackResult.DECLINED to 1
            MagicResourcePackResult.FAILED_DOWNLOAD to 2
            MagicResourcePackResult.ACCEPTED to 3
        }
    }

    /**
     * Creates a new Minecraft enum and provides a builder function
     * to initialize it.
     *
     * @param  build         The builder function to initialize these mappings.
     * @author Koding
     * @since  0.1.0-SNAPSHOT
     */
    private fun <T : RegistryEntry> create(build: MinecraftRegistry<T>.() -> Unit) =
        MinecraftRegistry<T>().apply(build).apply { rebuild() }

}

/**
 * Represents a single registry entry.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
open class RegistryEntry(@Suppress("UNUSED") open val name: String) {
    override fun toString() = name
}

/**
 * Base for all unknown registry entries to inherit.
 *
 * @author Koding
 * @since  0.1.0-SNAPSHOT
 */
data class UnknownRegistryEntry<T : Any>(@Suppress("UNUSED") val raw: T) :
    RegistryEntry("UNKNOWN")
