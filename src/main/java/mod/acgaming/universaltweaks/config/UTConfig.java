package mod.acgaming.universaltweaks.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.cleanroommc.configanytime.ConfigAnytime;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.bugfixes.blocks.blockoverlay.UTBlockOverlayLists;
import mod.acgaming.universaltweaks.core.UTLoadingPlugin;
import mod.acgaming.universaltweaks.mods.botania.UTBotaniaFancySkybox;
import mod.acgaming.universaltweaks.tweaks.blocks.breakablebedrock.UTBreakableBedrock;
import mod.acgaming.universaltweaks.tweaks.items.parry.UTParry;
import mod.acgaming.universaltweaks.tweaks.items.rarity.UTCustomRarity;
import mod.acgaming.universaltweaks.tweaks.items.useduration.UTCustomUseDuration;
import mod.acgaming.universaltweaks.tweaks.misc.incurablepotions.UTIncurablePotions;
import mod.acgaming.universaltweaks.tweaks.misc.loadsound.UTLoadSound;
import mod.acgaming.universaltweaks.tweaks.misc.swingthroughgrass.UTSwingThroughGrassLists;
import mod.acgaming.universaltweaks.tweaks.performance.autosave.UTAutoSaveOFCompat;
import mod.acgaming.universaltweaks.util.compat.UTObsoleteModsHandler;

@Config(modid = UniversalTweaks.MODID, name = "UniversalTweaks")
public class UTConfig
{
    @Config.LangKey("cfg.universaltweaks.bugfixes.blocks")
    @Config.Name("Bugfixes: Blocks")
    public static final BugfixesBlocksCategory BUGFIXES_BLOCKS = new BugfixesBlocksCategory();

    @Config.LangKey("cfg.universaltweaks.bugfixes.entities")
    @Config.Name("Bugfixes: Entities")
    public static final BugfixesEntitiesCategory BUGFIXES_ENTITIES = new BugfixesEntitiesCategory();

    @Config.LangKey("cfg.universaltweaks.bugfixes.misc")
    @Config.Name("Bugfixes: Misc")
    public static final BugfixesMiscCategory BUGFIXES_MISC = new BugfixesMiscCategory();

    @Config.LangKey("cfg.universaltweaks.bugfixes.world")
    @Config.Name("Bugfixes: World")
    public static final BugfixesWorldCategory BUGFIXES_WORLD = new BugfixesWorldCategory();

    @Config.LangKey("cfg.universaltweaks.debug")
    @Config.Name("Debug")
    public static final DebugCategory DEBUG = new DebugCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration")
    @Config.Name("Mod Integration")
    public static final ModIntegrationCategory MOD_INTEGRATION = new ModIntegrationCategory();

    @Config.LangKey("cfg.universaltweaks.tweaks.blocks")
    @Config.Name("Tweaks: Blocks")
    public static final TweaksBlocksCategory TWEAKS_BLOCKS = new TweaksBlocksCategory();

    @Config.LangKey("cfg.universaltweaks.tweaks.entities")
    @Config.Name("Tweaks: Entities")
    public static final TweaksEntitiesCategory TWEAKS_ENTITIES = new TweaksEntitiesCategory();

    @Config.LangKey("cfg.universaltweaks.tweaks.items")
    @Config.Name("Tweaks: Items")
    public static final TweaksItemsCategory TWEAKS_ITEMS = new TweaksItemsCategory();

    @Config.LangKey("cfg.universaltweaks.tweaks.misc")
    @Config.Name("Tweaks: Misc")
    public static final TweaksMiscCategory TWEAKS_MISC = new TweaksMiscCategory();

    @Config.LangKey("cfg.universaltweaks.tweaks.performance")
    @Config.Name("Tweaks: Performance")
    public static final TweaksPerformanceCategory TWEAKS_PERFORMANCE = new TweaksPerformanceCategory();

    @Config.LangKey("cfg.universaltweaks.tweaks.world")
    @Config.Name("Tweaks: World")
    public static final TweaksWorldCategory TWEAKS_WORLD = new TweaksWorldCategory();

    public enum EnumLists
    {
        WHITELIST,
        BLACKLIST
    }

    public enum EnumDifficulty
    {
        PEACEFUL,
        EASY,
        NORMAL,
        HARD
    }

    public static class BugfixesBlocksCategory
    {
        @Config.LangKey("cfg.universaltweaks.bugfixes.blocks.blockoverlay")
        @Config.Name("Block Overlay")
        public final BlockOverlayCategory BLOCK_OVERLAY = new BlockOverlayCategory();

        @Config.RequiresMcRestart
        @Config.Name("Comparator Timing")
        @Config.Comment("Fixes inconsistent delays of comparators to prevent redstone timing issues")
        public boolean utComparatorTimingToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Falling Block Entity Damage")
        @Config.Comment("Only damage living entities hit by falling blocks, prevents killing items and XP")
        public boolean utFallingBlockDamageToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Hopper Bounding Box")
        @Config.Comment("Slims down the hopper bounding box for easier access of nearby blocks")
        public boolean utDietHopperToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Hopper Insert Safety Check")
        @Config.Comment
            ({
                "Prevents crashes when the destination tile entity becomes unavailable during the item insert process",
                "Mainly utilized to suppress edge case symptoms with Thaumcraft's Thaumatorium"
            })
        public boolean utHopperInsertToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Item Frame Void")
        @Config.Comment("Prevents voiding held items when right + left clicking on an item frame simultaneously")
        public boolean utItemFrameVoidToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Ladder Flying Slowdown")
        @Config.Comment("Disables climbing movement when flying")
        public boolean utLadderFlyingToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Mining Glitch")
        @Config.Comment("Avoids the need for multiple mining attempts by sending additional movement packets")
        public boolean utMiningGlitchToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Piston Progress")
        @Config.Comment("Properly saves the last state of pistons to tags")
        public boolean utPistonTileToggle = true;

        public static class BlockOverlayCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Block Overlay Toggle")
            @Config.Comment("Fixes x-ray when standing in non-suffocating blocks")
            public boolean utBlockOverlayToggle = true;

            @Config.Name("[2] Blacklist")
            @Config.Comment
                ({
                    "Excludes blocks from the block overlay bugfix",
                    "Syntax: modid:block"
                })
            public String[] utBlockOverlayBlacklist = new String[] {};

            @Config.Name("[3] Whitelist")
            @Config.Comment
                ({
                    "Includes blocks in the block overlay bugfix",
                    "Syntax: modid:block"
                })
            public String[] utBlockOverlayWhitelist = new String[] {};
        }
    }

    public static class BugfixesEntitiesCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Attack Radius")
        @Config.Comment("Improves the attack radius of hostile mobs by checking the line of sight with raytracing")
        public boolean utAttackRadiusToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Block Fire")
        @Config.Comment("Prevents fire projectiles burning entities when blocking with shields")
        public boolean utBlockFireToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Boat Riding Offset")
        @Config.Comment("Fixes entities glitching through the bottom of boats")
        public boolean utBoatOffsetToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Concurrent Entity AI Tasks")
        @Config.Comment
            ({
                "Replaces linked entity AI task sets with concurrent sets to avoid mod exception concerning entity AI",
                "Only enable this if you're facing concurrent modification exceptions with entity AI tasks, for example Thaumcraft's Pechs"
            })
        public boolean utEntityAITasksToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Death Time")
        @Config.Comment("Fixes corrupted entities exceeding the allowed death time")
        public boolean utDeathTimeToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Destroy Entity Packets")
        @Config.Comment("Fixes lag caused by dead entities by sending additional packets when the player is not alive")
        public boolean utDestroyPacketToggle = true;

        @Config.Name("Disconnect Dupe")
        @Config.Comment("Fixes item dupes when players are dropping items and disconnecting")
        public boolean utDisconnectDupeToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Dimension Change Player States")
        @Config.Comment("Fixes missing player states when changing dimensions by sending additional packets")
        public boolean utDimensionChangeToggle = true;

        @Config.Name("Double Consumption")
        @Config.Comment("Fixes consuming an item having a chance of also consuming a second item without any animation")
        public boolean utDoubleConsumptionToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Elytra Deployment & Landing")
        @Config.Comment("Relocate elytra deployment and landing to client side to prevent issues with high latencies")
        public boolean utElytraDeploymentLandingToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Entity Bounding Box")
        @Config.Comment("Saves entity bounding boxes to tags to prevent breakouts and suffocation")
        public boolean utEntityAABBToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Entity Desync")
        @Config.Comment("Fixes entity motion desyncs most notable with arrows and thrown items")
        public boolean utEntityDesyncToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Entity ID")
        @Config.Comment("Fixes non-functional elytra firework boosting and guardian targeting if the entity ID is 0")
        public boolean utEntityIDToggle = true;

        @Config.Name("Entity NaN Values")
        @Config.Comment("Prevents corruption of entities caused by invalid health or damage values")
        public boolean utEntityNaNToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Entity Suffocation")
        @Config.Comment("Pushes entities out of blocks when growing up to prevent suffocation")
        public boolean utEntitySuffocationToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Entity Tracker")
        @Config.Comment
            ({
                "Fixes entity tracker to prevent client-sided desyncs when teleporting or changing dimensions",
                "Incompatible with SpongeForge"
            })
        public boolean utEntityTrackerToggle = true;

        @Config.Name("Entity UUID")
        @Config.Comment("Changes UUIDs of loaded entities in case their UUIDs are already assigned (and removes log spam)")
        public boolean utEntityUUIDToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Max Player Health")
        @Config.Comment("Corrects maximum player health on joining by setting the last saved health value")
        public boolean utMaxHealthToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Mount Desync")
        @Config.Comment("Fixes mounts and boats sometimes disappearing after dismounting")
        public boolean utMountDesyncToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Player Saturation")
        @Config.Comment("Fixes saturation depleting in peaceful mode")
        public boolean utExhaustionToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Skeleton Aim")
        @Config.Comment("Fixes skeletons not looking at their targets when strafing")
        public boolean utSkeletonAimToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Villager Mantle Hoods")
        @Config.Comment("Returns missing hoods to villager mantles")
        public boolean utVillagerMantleToggle = true;
    }

    public static class BugfixesMiscCategory
    {
        @Config.LangKey("cfg.universaltweaks.bugfixes.misc.modelgap")
        @Config.Name("Model Gap")
        public final ModelGapCategory MODEL_GAP = new ModelGapCategory();

        @Config.RequiresMcRestart
        @Config.Name("Accurate Smooth Lighting")
        @Config.Comment("Improves the accuracy of smooth lighting by checking for suffocation and light opacity")
        public boolean utAccurateSmoothLighting = true;

        @Config.RequiresMcRestart
        @Config.Name("Depth Mask")
        @Config.Comment("Fixes entity and particle rendering issues by enabling depth buffer writing")
        public boolean utDepthMaskToggle = true;

        @Config.Name("Help Command")
        @Config.Comment("Replaces the help command, sorts and reports broken commands")
        public boolean utHelpToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Locale Crash")
        @Config.Comment("Prevents various crashes with Turkish locale")
        public boolean utLocaleToggle = true;

        public static class ModelGapCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Model Gap Toggle")
            @Config.Comment("Fixes transparent gaps in all 3D models of blocks and items")
            public boolean utModelGapToggle = true;

            @Config.Name("[2] Recess Value")
            @Config.Comment
                ({
                    "Quad X/Y offset",
                    "Moves the quad toward the center of the item",
                    "Use to hide gaps, keep as close to 0 as possible"
                })
            public double utModelGapRecess = 0.007D;

            @Config.Name("[3] Expansion Value")
            @Config.Comment
                ({
                    "Quad expansion increment",
                    "Enlarges each quad",
                    "Use to hide gaps, keep as close to 0 as possible"
                })
            public double utModelGapExpansion = 0.008D;
        }
    }

    public static class BugfixesWorldCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Chunk Saving")
        @Config.Comment
            ({
                "Fixes loading of outdated chunks to prevent duplications, deletions and data corruption",
                "Incompatible with SpongeForge"
            })
        public boolean utChunkSavingToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Frustum Culling")
        @Config.Comment("Fixes invisible chunks in edge cases (small enclosed rooms at chunk borders)")
        public boolean utFrustumCullingToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Tile Entity Map")
        @Config.Comment
            ({
                "Changes the data table of tile entities to resolve issues",
                "HASHMAP:                   Vanilla default",
                "LINKED_HASHMAP:            Keeps the loading order of tile entities to prevent issues during the first ticks of chunk loading",
                "CONCURRENT_HASHMAP:        Allows simultaneous access to tile entities to prevent concurrent modification exceptions",
                "CONCURRENT_LINKED_HASHMAP: Combines LINKED_HASHMAP and CONCURRENT_HASHMAP",
            })
        public EnumMaps utTileEntityMap = EnumMaps.CONCURRENT_LINKED_HASHMAP;

        public enum EnumMaps
        {
            HASHMAP,
            LINKED_HASHMAP,
            CONCURRENT_HASHMAP,
            CONCURRENT_LINKED_HASHMAP
        }
    }

    public static class TweaksBlocksCategory
    {
        @Config.LangKey("cfg.universaltweaks.tweaks.blocks.betterplacement")
        @Config.Name("Better Placement")
        public final BetterPlacementCategory BETTER_PLACEMENT = new BetterPlacementCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.blocks.blockdispenser")
        @Config.Name("Block Dispenser")
        public final BlockDispenserCategory BLOCK_DISPENSER = new BlockDispenserCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.blocks.breakablebedrock")
        @Config.Name("Breakable Bedrock")
        public final BreakableBedrockCategory BREAKABLE_BEDROCK = new BreakableBedrockCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.blocks.finitewater")
        @Config.Name("Finite Water")
        public final FiniteWaterCategory FINITE_WATER = new FiniteWaterCategory();

        @Config.RequiresMcRestart
        @Config.Name("Bed Obstruction Replacement")
        @Config.Comment("Replaces bed obstruction checks with an improved version")
        public boolean utBedObstructionToggle = true;

        @Config.Name("Better Harvest")
        @Config.Comment("Prevents breaking lower parts of sugar cane and cacti as well as unripe crops, unless sneaking")
        public boolean utBetterHarvestToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Block Hit Delay")
        @Config.Comment("Sets the delay in ticks between breaking blocks")
        public int utBlockHitDelay = 5;

        @Config.RequiresMcRestart
        @Config.Name("Cactus Size")
        @Config.Comment("Determines how tall cacti can grow")
        public int utCactusSize = 3;

        @Config.RequiresMcRestart
        @Config.Name("Fast Leaf Decay")
        @Config.Comment("Makes leaves decay faster when trees are chopped")
        public boolean utLeafDecayToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Fence/Wall Jump")
        @Config.Comment("Allows the player to jump over fences and walls")
        public boolean utFenceWallJumpToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Lenient Paths")
        @Config.Comment("Allows the creation of grass paths everywhere (beneath fence gates, trapdoors, ...)")
        public boolean utLenientPathsToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Sugar Cane Size")
        @Config.Comment("Determines how tall sugar cane can grow")
        public int utSugarCaneSize = 3;

        @Config.RequiresMcRestart
        @Config.Name("Vine Size")
        @Config.Comment
            ({
                "Determines how long vines can grow",
                "0 = Infinite (vanilla default)"
            })
        public int utVineSize = 0;

        public static class BetterPlacementCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Better Placement Toggle")
            @Config.Comment("Removes the delay between placing blocks")
            public boolean utBetterPlacementToggle = false;

            @Config.Name("[2] Force New Location")
            @Config.Comment("If the cursor must be moved to a new location before placing another block")
            public boolean utBetterPlacementNewLoc = true;

            @Config.Name("[3] Creative Mode Only")
            @Config.Comment("Only affects block placement in creative mode")
            public boolean utBetterPlacementCreative = false;
        }

        public static class BlockDispenserCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Block Dispenser Toggle")
            @Config.Comment("Allows dispensers to place blocks")
            public boolean utBlockDispenserToggle = true;

            @Config.Name("[2] Block List")
            @Config.Comment
                ({
                    "List of blocks concerning dispensing",
                    "Behavior depends on the list mode",
                    "Syntax: modid:block"
                })
            public String[] utBlockDispenserBlockList = new String[]
                {
                    "minecraft:water",
                    "minecraft:flowing_water",
                    "minecraft:lava",
                    "minecraft:flowing_lava",
                    "minecraft:fire",
                    "minecraft:web",
                    "botania:specialflower",
                    "thermalexpansion:strongbox"
                };

            @Config.Name("[3] List Mode")
            @Config.Comment
                ({
                    "Blacklist Mode: Blocks which can't be placed, others can",
                    "Whitelist Mode: Blocks which can be placed, others can't"
                })
            public EnumLists utBlockDispenserBlockListMode = EnumLists.BLACKLIST;
        }

        public static class BreakableBedrockCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Breakable Bedrock Toggle")
            @Config.Comment("Allows customizable mining of bedrock")
            public boolean utBreakableBedrockToggle = false;

            @Config.Name("[2] Tool List")
            @Config.Comment
                ({
                    "List of tools concerning mining bedrock",
                    "Behavior depends on the list mode",
                    "Syntax: modid:tool"
                })
            public String[] utBreakableBedrockToolList = new String[] {};

            @Config.Name("[3] List Mode")
            @Config.Comment
                ({
                    "Blacklist Mode: Tools which can't mine bedrock, others can",
                    "Whitelist Mode: Tools which can mine bedrock, others can't"
                })
            public EnumLists utBreakableBedrockToolListMode = EnumLists.BLACKLIST;
        }

        public static class FiniteWaterCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Finite Water Toggle")
            @Config.Comment("Prevents creation of infinite water sources")
            public boolean utFiniteWaterToggle = false;

            @Config.Name("[2] Allow Water Biomes")
            @Config.Comment("Allows creation of infinite water sources in ocean and river biomes")
            public boolean utFiniteWaterWaterBiomes = true;

            @Config.Name("[3] Minimum Altitude")
            @Config.Comment("Inclusive minimum altitude at which water is infinite")
            public int utFiniteWaterInfMin = 0;

            @Config.Name("[4] Maximum Altitude")
            @Config.Comment("Inclusive maximum altitude at which water is infinite")
            public int utFiniteWaterInfMax = 63;
        }
    }

    public static class TweaksEntitiesCategory
    {
        @Config.LangKey("cfg.universaltweaks.tweaks.entities.attributes")
        @Config.Name("Attributes")
        public final AttributesCategory ATTRIBUTES = new AttributesCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.entities.betterburning")
        @Config.Name("Better Burning")
        public final BetterBurningCategory BETTER_BURNING = new BetterBurningCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.entities.collisiondamage")
        @Config.Name("Collision Damage")
        public final CollisionDamageCategory COLLISION_DAMAGE = new CollisionDamageCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.entities.damagevelocity")
        @Config.Name("Damage Velocity")
        public final DamageVelocityCategory DAMAGE_VELOCITY = new DamageVelocityCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.entities.easybreeding")
        @Config.Name("Easy Breeding")
        public final EasyBreedingCategory EASY_BREEDING = new EasyBreedingCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.entities.nogolems")
        @Config.Name("No Golems")
        public final NoGolemsCategory NO_GOLEMS = new NoGolemsCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.entities.playerspeed")
        @Config.Name("Player Speed")
        public final PlayerSpeedCategory PLAYER_SPEED = new PlayerSpeedCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.entities.rallyhealth")
        @Config.Name("Rally Health")
        public final RallyHealthCategory RALLY_HEALTH = new RallyHealthCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.entities.spawncaps")
        @Config.Name("Spawn Caps")
        public final SpawnCapsCategory SPAWN_CAPS = new SpawnCapsCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.entities.undeadhorses")
        @Config.Name("Undead Horses")
        public final UndeadHorsesCategory UNDEAD_HORSES = new UndeadHorsesCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.entities.waterfalldamage")
        @Config.Name("Water Fall Damage")
        public final WaterFallDamageCategory WATER_FALL_DAMAGE = new WaterFallDamageCategory();

        @Config.RequiresMcRestart
        @Config.Name("AI Replacement")
        @Config.Comment("Replaces entity AI for improved server performance")
        public boolean utAIReplacementToggle = true;

        @Config.Name("AI Removal")
        @Config.Comment("Removes entity AI for improved server performance")
        public boolean utAIRemovalToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Auto Jump Replacement")
        @Config.Comment("Replaces auto jump with an increased step height (singleplayer only)")
        public boolean utAutoJumpToggle = true;

        @Config.Name("Better Ignition")
        @Config.Comment("Enables ignition of entities by right-clicking instead of awkwardly lighting the block under them")
        public boolean utBetterIgnitionToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Boat Speed")
        @Config.Comment("Sets the acceleration value for controlling boats")
        public double utBoatSpeed = 0.04D;

        @Config.Name("Creeper Charged Spawning Chance")
        @Config.Comment("Sets the chance for creepers to spawn charged")
        @Config.RangeDouble(min = 0.0D, max = 1.0D)
        public double utCreeperChargedChance = 0.0D;

        @Config.RequiresMcRestart
        @Config.Name("Creeper Confetti Spawning Chance")
        @Config.Comment("Sets the chance to replace deadly creeper explosions with delightful confetti")
        @Config.RangeDouble(min = 0.0D, max = 1.0D)
        public double utCreeperConfettiChance = 0.0D;

        @Config.RequiresMcRestart
        @Config.Name("Critical Arrow Damage")
        @Config.Comment
            ({
                "Sets the additional damage that critical arrows deal",
                "-1 for vanilla random default"
            })
        public int utCriticalArrowDamage = -1;

        @Config.RequiresMcRestart
        @Config.Name("Disable Creeper Music Discs")
        @Config.Comment("Disables creepers dropping music discs when slain by skeletons")
        public boolean utCreeperMusicDiscsToggle = false;

        @Config.Name("Disable Sleeping")
        @Config.Comment("Disables skipping night by using a bed while making it still able to set spawn")
        public boolean utSleepingToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Disable Wither Targeting AI")
        @Config.Comment("Disables withers targeting animals")
        public boolean utWitherAIToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Husk & Stray Spawning")
        @Config.Comment("Lets husks and strays spawn underground like regular zombies and skeletons")
        public boolean utHuskStraySpawningToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Mob Despawn Improvement")
        @Config.Comment("Mobs carrying picked up items will drop their equipment and despawn properly")
        public boolean utMobDespawnToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("No Saddled Wandering")
        @Config.Comment("Stops horses wandering around when saddled")
        public boolean utSaddledWanderingToggle = true;

        @Config.Name("Rabbit Killer Spawning Chance")
        @Config.Comment("Sets the chance for rabbits to spawn as the killer bunny variant")
        @Config.RangeDouble(min = 0.0D, max = 1.0D)
        public double utRabbitKillerChance = 0.0D;

        @Config.Name("Rabbit Toast Spawning Chance")
        @Config.Comment("Sets the chance for rabbits to spawn as the Toast variant")
        @Config.RangeDouble(min = 0.0D, max = 1.0D)
        public double utRabbitToastChance = 0.0D;

        public static class AttributesCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[01] Attributes Toggle")
            @Config.Comment("Sets custom ranges for entity attributes")
            public boolean utAttributesToggle = true;

            @Config.Name("[02] Max Health Min")
            public double utAttributeMaxHealthMin = -65536;

            @Config.Name("[03] Max Health Max")
            public double utAttributeMaxHealthMax = 65536;

            @Config.Name("[04] Follow Range Min")
            public double utAttributeFollowRangeMin = -65536;

            @Config.Name("[05] Follow Range Max")
            public double utAttributeFollowRangeMax = 65536;

            @Config.Name("[06] Knockback Resistance Min")
            public double utAttributeKnockbackResistanceMin = -65536;

            @Config.Name("[07] Knockback Resistance Max")
            public double utAttributeKnockbackResistanceMax = 65536;

            @Config.Name("[08] Movement Speed Min")
            public double utAttributeMovementSpeedMin = -65536;

            @Config.Name("[09] Movement Speed Max")
            public double utAttributeMovementSpeedMax = 65536;

            @Config.Name("[10] Flying Speed Min")
            public double utAttributeFlyingSpeedMin = -65536;

            @Config.Name("[11] Flying Speed Max")
            public double utAttributeFlyingSpeedMax = 65536;

            @Config.Name("[12] Attack Damage Min")
            public double utAttributeAttackDamageMin = -65536;

            @Config.Name("[13] Attack Damage Max")
            public double utAttributeAttackDamageMax = 65536;

            @Config.Name("[14] Attack Speed Min")
            public double utAttributeAttackSpeedMin = -65536;

            @Config.Name("[15] Attack Speed Max")
            public double utAttributeAttackSpeedMax = 65536;

            @Config.Name("[16] Armor Min")
            public double utAttributeArmorMin = -65536;

            @Config.Name("[17] Armor Max")
            public double utAttributeArmorMax = 65536;

            @Config.Name("[18] Armor Toughness Min")
            public double utAttributeArmorToughnessMin = -65536;

            @Config.Name("[19] Armor Toughness Max")
            public double utAttributeArmorToughnessMax = 65536;

            @Config.Name("[20] Luck Min")
            public double utAttributeLuckMin = -65536;

            @Config.Name("[21] Luck Max")
            public double utAttributeLuckMax = 65536;
        }

        public static class BetterBurningCategory
        {
            @Config.Name("[1] Cooked Items")
            @Config.Comment("Fixes some edge cases where fire damage sources won't cause mobs to drop their cooked items")
            public boolean utBBCookedToggle = true;

            @Config.Name("[2] Extinguishing")
            @Config.Comment("If entities have fire resistance, they get extinguished right away when on fire")
            public boolean utBBExtinguishToggle = true;

            @Config.Name("[3] Fire Overlay")
            @Config.Comment("Prevents the fire animation overlay from being displayed when the player is immune to fire")
            public boolean utBBOverlayToggle = true;

            @Config.Name("[4] Flaming Arrows")
            @Config.Comment("Allows skeletons to shoot flaming arrows when on fire (30% chance * regional difficulty)")
            public boolean utBBArrowsToggle = true;

            @Config.Name("[5] Spreading Fire")
            @Config.Comment("Allows fire to spread from entity to entity (30% chance * regional difficulty)")
            public boolean utBBSpreadingToggle = true;
        }

        public static class EasyBreedingCategory
        {
            @Config.Name("[1] Easy Breeding Toggle")
            @Config.Comment("Enables easy breeding of animals by tossing food on the ground")
            public boolean utEasyBreedingToggle = false;

            @Config.Name("[2] Search Distance")
            @Config.Comment("Determines the distance for animals to search for food")
            public double utEasyBreedingDistance = 10;
        }

        public static class CollisionDamageCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Collision Damage Toggle")
            @Config.Comment("Applies horizontal collision damage to the player akin to elytra collision")
            public boolean utCollisionDamageToggle = false;

            @Config.Name("[2] Damage Factor")
            @Config.Comment
                ({
                    "The damage factor that gets multiplied with the player speed",
                    "Vanilla default for elytra damage is 10"
                })
            public int utCollisionDamageFactor = 10;
        }

        public static class DamageVelocityCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Damage Velocity Toggle")
            @Config.Comment("Enables the modification of damage sources that change the entity's velocity")
            public boolean utDamageVelocityToggle = false;

            @Config.Name("[2] Damage Velocity List")
            @Config.Comment("Syntax: damagetype")
            public String[] utDamageVelocityList = new String[]
                {
                    "inFire",
                    "onFire"
                };

            @Config.Name("[3] List Mode")
            @Config.Comment
                ({
                    "Blacklist Mode: Damage sources that don't change velocity, others do",
                    "Whitelist Mode: Damage sources that change velocity, others don't"
                })
            public EnumLists utDamageVelocityListMode = EnumLists.BLACKLIST;
        }

        public static class NoGolemsCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Iron Golem Toggle")
            @Config.Comment("Disables the manual creation of iron golems")
            public boolean utNGIronGolemToggle = false;

            @Config.RequiresMcRestart
            @Config.Name("[2] Snow Golem Toggle")
            @Config.Comment("Disables the manual creation of snow golems")
            public boolean utNGSnowGolemToggle = false;

            @Config.RequiresMcRestart
            @Config.Name("[3] Wither Toggle")
            @Config.Comment("Disables the manual creation of withers")
            public boolean utNGWitherToggle = false;
        }

        public static class PlayerSpeedCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Player Speed Toggle")
            @Config.Comment("Enables the modification of base and maximum player speeds")
            public boolean utPlayerSpeedToggle = false;

            @Config.Name("[2] Walk Speed")
            @Config.Comment("Determines the player's base walk speed")
            public double utPlayerWalkSpeed = 0.1;

            @Config.Name("[3] Fly Speed")
            @Config.Comment("Determines the player's base fly speed")
            public double utPlayerFlySpeed = 0.05;

            @Config.Name("[4] Max Speed")
            @Config.Comment
                ({
                    "Determines the player's maximum speed",
                    "Increase if you get the infamous 'Player moved too quickly' messages"
                })
            public double utPlayerMaxSpeed = 100;

            @Config.Name("[5] Max Elytra Speed")
            @Config.Comment
                ({
                    "Determines the player's maximum speed when flying with elytra",
                    "Increase if you get the infamous 'Player moved too quickly' messages"
                })
            public double utPlayerMaxElytraSpeed = 300;

            @Config.Name("[6] Max Vehicle Speed")
            @Config.Comment
                ({
                    "Determines the player's maximum speed when riding a vehicle or mount",
                    "Increase if you get the infamous 'Player moved too quickly' messages"
                })
            public double utPlayerVehicleSpeed = 100;
        }

        public static class RallyHealthCategory
        {
            @Config.Name("[1] Rally Health Toggle")
            @Config.Comment
                ({
                    "Adds Bloodborne's Rally system to Minecraft",
                    "Regain lost health when attacking back within the risk time"
                })
            public boolean utRallyHealthToggle = false;

            @Config.Name("[2] Risk Time")
            @Config.Comment("Determines the risk time in ticks")
            public int utRallyHealthRiskTime = 60;

            @Config.Name("[3] Heal Chance")
            @Config.Comment("Determines the chance to regain health in percent")
            public int utRallyHealthHealChance = 80;

            @Config.Name("[4] Indication Sound")
            @Config.Comment("Plays an indication sound effect when health is regained")
            public boolean utRallyHealthSound = false;
        }

        public static class SpawnCapsCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Spawn Caps Toggle")
            @Config.Comment("Sets maximum spawning limits for different entity types")
            public boolean utSpawnCapsToggle = false;

            @Config.RequiresMcRestart
            @Config.Name("[2] Monster Cap")
            @Config.Comment("Maximum amount of monsters (IMob)")
            public int utSpawnCapsMonster = 70;

            @Config.RequiresMcRestart
            @Config.Name("[3] Creature Cap")
            @Config.Comment("Maximum amount of creatures (EntityAnimal)")
            public int utSpawnCapsCreature = 10;

            @Config.RequiresMcRestart
            @Config.Name("[4] Ambient Cap")
            @Config.Comment("Maximum amount of ambients (EntityAmbientCreature)")
            public int utSpawnCapsAmbient = 15;

            @Config.RequiresMcRestart
            @Config.Name("[5] Water Creature Cap")
            @Config.Comment("Maximum amount of water creatures (EntityWaterMob)")
            public int utSpawnCapsWaterCreature = 5;
        }

        public static class UndeadHorsesCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("Burning Undead Horses")
            @Config.Comment("Lets untamed undead horses burn in daylight")
            public boolean utBurningUndeadHorsesToggle = true;

            @Config.RequiresMcRestart
            @Config.Name("Taming Undead Horses")
            @Config.Comment("Allows taming of undead horses")
            public boolean utTamingUndeadHorsesToggle = true;
        }

        public static class WaterFallDamageCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Water Fall Damage Toggle")
            @Config.Comment("Re-implements an improved version of pre-1.4 fall damage in water")
            public boolean utFallDamageToggle = false;

            @Config.Name("[2] Damage Reduction")
            @Config.Comment("How much fall damage gets reduced by water per tick")
            public double utFallDamageValue = 2.0D;
        }
    }

    public static class TweaksItemsCategory
    {
        @Config.LangKey("cfg.universaltweaks.tweaks.items.attackcooldown")
        @Config.Name("Attack Cooldown")
        public final AttackCooldownCategory ATTACK_COOLDOWN = new AttackCooldownCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.items.itementities")
        @Config.Name("Item Entities")
        public final ItemEntitiesCategory ITEM_ENTITIES = new ItemEntitiesCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.items.mending")
        @Config.Name("Mending")
        public final MendingCategory MENDING = new MendingCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.items.parry")
        @Config.Name("Shield Parry")
        public final ParryCategory PARRY = new ParryCategory();

        @Config.Name("Auto Switch Tools")
        @Config.Comment("Switches the selected hotbar slot to a proper tool if required")
        public boolean utAutoSwitchToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("No Crafting Repair")
        @Config.Comment("Disables crafting recipes for repairing tools")
        public boolean utCraftingRepairToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Hardcore Buckets")
        @Config.Comment("Prevents placing of liquid source blocks in the world")
        public boolean utHardcoreBucketsToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("No Leftover Breath Bottles")
        @Config.Comment("Disables dragon's breath from being a container item and leaving off empty bottles when a stack is brewed with")
        public boolean utLeftoverBreathBottleToggle = true;

        @Config.Name("Bow Infinity")
        @Config.Comment("Bows enchanted with Infinity no longer require arrows")
        public boolean utBowInfinityToggle = true;

        @Config.Name("Custom Rarity")
        @Config.Comment
            ({
                "Sets custom rarities for items, affecting tooltip colors",
                "Syntax: modid:item:meta;rarity",
                "'meta' is optional and defaults to 0",
                "Available rarities: common, uncommon, rare, epic",
                "Example -> minecraft:diamond;rare"
            })
        public String[] utCustomRarities = new String[]
            {
                "minecraft:dragon_breath;uncommon",
                "minecraft:elytra;uncommon",
                "minecraft:experience_bottle;uncommon",
                "minecraft:nether_star;uncommon",
                "minecraft:skull:0;uncommon",
                "minecraft:skull:1;uncommon",
                "minecraft:skull:2;uncommon",
                "minecraft:skull:3;uncommon",
                "minecraft:skull:4;uncommon",
                "minecraft:skull:5;uncommon",
                "minecraft:totem_of_undying;uncommon",
                "minecraft:beacon;rare",
                "minecraft:end_crystal;rare",
                "minecraft:barrier;epic",
                "minecraft:chain_command_block;epic",
                "minecraft:command_block;epic",
                "minecraft:command_block_minecart;epic",
                "minecraft:dragon_egg;epic",
                "minecraft:knowledge_book;epic",
                "minecraft:repeating_command_block;epic",
                "minecraft:structure_block;epic",
                "minecraft:structure_void;epic",
                "thaumcraft:thaumium_axe;uncommon",
                "thaumcraft:thaumium_hoe;uncommon",
                "thaumcraft:thaumium_pick;uncommon",
                "thaumcraft:thaumium_shovel;uncommon",
                "thaumcraft:thaumium_sword;uncommon",
                "thaumcraft:void_axe;uncommon",
                "thaumcraft:void_hoe;uncommon",
                "thaumcraft:void_pick;uncommon",
                "thaumcraft:void_shovel;uncommon",
                "thaumcraft:void_sword;uncommon",
                "thaumcraft:primal_crusher;epic"
            };

        @Config.RequiresMcRestart
        @Config.Name("Custom Use Duration")
        @Config.Comment
            ({
                "Sets custom use durations for items like shields, affecting the maximum block time",
                "Syntax: modid:item:meta;duration;cooldown",
                "'meta' and 'cooldown' are optional and default to 0, 'duration' and 'cooldown' in ticks",
                "Examples -> minecraft:shield;69",
                "         -> custommod:customshield:1;42;69"
            })
        public String[] utCustomUseDurations = new String[] {};

        @Config.Name("Super Hot Torch")
        @Config.Comment("Enables one-time ignition of entities by hitting them with a torch")
        public boolean utSuperHotTorchToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("XP Bottle Amount")
        @Config.Comment
            ({
                "Sets the amount of experience spawned by bottles o' enchanting",
                "-1 for vanilla default"
            })
        public int utXPBottleAmount = -1;

        public static class AttackCooldownCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] No Attack Cooldown Toggle")
            @Config.Comment("Disables the 1.9 combat update attack cooldown")
            public boolean utAttackCooldownToggle = false;

            @Config.Name("[2] Only Affect Swords")
            @Config.Comment("Only removes the attack cooldown of swords to balance other weapons like axes")
            public boolean utAttackCooldownSwords = false;

            @Config.Name("[3] Hide Attack Speed Tooltip")
            @Config.Comment("Hides attack speed tooltips of weapons")
            public boolean utAttackCooldownTooltips = true;
        }

        public static class ItemEntitiesCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[01] Item Entities Toggle")
            @Config.Comment("Enables the modification of item entity properties")
            public boolean utItemEntitiesToggle = true;

            @Config.Name("[02] Physics")
            @Config.Comment("Adds physical aspects such as collision boxes to item entities")
            public boolean utIEPhysicsToggle = false;

            @Config.Name("[03] Automatic Pickup")
            @Config.Comment
                ({
                    "Item entities can be picked up automatically",
                    "When disabled, item entities can be picked up by right-clicking (requires 'Physics' option)"
                })
            public boolean utIEAutomaticPickupToggle = true;

            @Config.Name("[04] Sneaking Pickup")
            @Config.Comment("Item entities can only be picked up when sneaking")
            public boolean utIESneakingPickupToggle = false;

            @Config.Name("[05] Collection Tool")
            @Config.Comment
                ({
                    "Tools which enable picking up items automatically",
                    "Example -> minecraft:bucket"
                })
            public String[] utIECollectionTools = new String[] {};

            @Config.Name("[06] Pickup Delay")
            @Config.Comment
                ({
                    "Determines the delay in ticks until item entities can be picked up",
                    "-1 for vanilla default"
                })
            public int utIEPickupDelay = -1;

            @Config.Name("[07] Lifespan")
            @Config.Comment
                ({
                    "Determines the time in ticks until item entities get despawned",
                    "-1 for vanilla default"
                })
            public int utIELifespan = -1;

            @Config.Name("[08] No Combination")
            @Config.Comment("Stops combination of item entities")
            public boolean utIENoCombinationToggle = false;

            @Config.Name("[09] Smart Combination")
            @Config.Comment("Stops combination of item entities if their maximum stack size is reached")
            public boolean utIESmartCombinationToggle = true;

            @Config.Name("[10] Smart Combination Radius")
            @Config.Comment
                ({
                    "The radius (in blocks) that dropped items should check around them for other dropped items to combine with",
                    "Depends on the Smart Combination toggle"
                })
            public double utIESmartCombinationRadius = 2;

            @Config.Name("[11] Smart Combination Y-Axis Check")
            @Config.Comment
                ({
                    "Allows dropped items to also check above and below them for combination",
                    "Depends on the Smart Combination toggle"
                })
            public boolean utIESmartCombinationYAxis = true;

            @Config.Name("[12] Rotation")
            @Config.Comment("Enables the rotation effect")
            public boolean utIERotationToggle = true;

            @Config.Name("[13] Bobbing")
            @Config.Comment("Enables the bobbing effect")
            public boolean utIEBobbingToggle = true;

            @Config.Name("[14] Clear Despawn")
            @Config.Comment("Makes item entities flash when they're about to despawn")
            public boolean utIEClearDespawnToggle = false;

            @Config.Name("[15] Clear Despawn: Flashing Time")
            @Config.Comment("Determines the time in seconds item entities have left before despawn to start flashing")
            public int utIEClearDespawnTime = 20;

            @Config.Name("[16] Clear Despawn: Urgent Flashing")
            @Config.Comment("Makes item entities flash faster as they get closer to despawning")
            public boolean utIEClearDespawnUrgentToggle = true;
        }

        public static class MendingCategory
        {
            @Config.Name("[1] Mending Toggle")
            @Config.Comment("Implements modern mending behavior to only repair damaged equipment with XP")
            public boolean utMendingToggle = true;

            @Config.Name("[2] Ratio")
            @Config.Comment("Determines the amount of durability mending will repair, on average, per point of experience")
            public double utMendingRatio = 2.0D;

            @Config.Name("[3] Overpowered")
            @Config.Comment("Repairs damaged items from the entire inventory with XP")
            public boolean utMendingOPToggle = false;
        }

        public static class ParryCategory
        {
            @Config.Name("[01] Shield Parry Toggle")
            @Config.Comment("Allows parrying of projectiles with shields")
            public boolean utParryToggle = false;

            @Config.Name("[02] Arrow Time Window")
            @Config.Comment
                ({
                    "Determines the amount of time an arrow can be parried after raising the shield",
                    "Measured in ticks"
                })
            public int utParryArrowTimeWindow = 40;

            @Config.Name("[03] Fireball Time Window")
            @Config.Comment
                ({
                    "Determines the amount of time a fireball can be parried after raising the shield",
                    "Measured in ticks"
                })
            public int utParryFireballTimeWindow = 40;

            @Config.Name("[04] Throwable Time Window")
            @Config.Comment
                ({
                    "Determines the amount of time a throwable can be parried after raising the shield",
                    "Measured in ticks"
                })
            public int utParryThrowableTimeWindow = 40;

            @Config.Name("[05] Projectile List")
            @Config.Comment
                ({
                    "Syntax:  modid:entity",
                    "Example: minecraft:arrow"
                })
            public String[] utParryProjectileList = new String[] {};

            @Config.Name("[06] List Mode")
            @Config.Comment
                ({
                    "Blacklist Mode: Projectiles which can't be parried, others can be parried",
                    "Whitelist Mode: Projectiles which can be parried, others can't be parried"
                })
            public EnumLists utParryProjectileListMode = EnumLists.BLACKLIST;

            @Config.Name("[07] Indication Sound")
            @Config.Comment("Plays an indication sound effect when projectiles are parried")
            public boolean utParrySound = false;

            @Config.Name("[08] Rebound Enchantment")
            @Config.Comment("Adds the Rebound enchantment for extended parry time windows")
            public boolean utParryReboundToggle = true;

            @Config.Name("[09] Rebound Treasure Enchantment")
            @Config.Comment("Makes the Rebound enchantment exclusive to enchanted books as loot")
            public boolean utParryReboundTreasure = false;

            @Config.Name("[10] Rebound Max Level")
            @Config.Comment("Maximum enchantment level for the Rebound enchantment")
            public int utParryReboundMaxLevel = 5;

            @Config.Name("[11] Rebound Multiplier")
            @Config.Comment("Multiplier for the parry time windows")
            public double utParryReboundMultiplier = 0.25D;

            @Config.Name("[12] Require Rebound Enchantment")
            @Config.Comment("Requires the rebound enchantment for parrying")
            public boolean utParryReboundRequire = false;
        }
    }

    public static class TweaksMiscCategory
    {
        @Config.LangKey("cfg.universaltweaks.tweaks.misc.incurablepotions")
        @Config.Name("Incurable Potions")
        public final IncurablePotionsCategory INCURABLE_POTIONS = new IncurablePotionsCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.misc.lightning")
        @Config.Name("Lightning")
        public final LightningCategory LIGHTNING = new LightningCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.misc.loadsounds")
        @Config.Name("Load Sounds")
        public final LoadSoundsCategory LOAD_SOUNDS = new LoadSoundsCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.misc.pickupnotification")
        @Config.Name("Pickup Notification")
        public final PickupNotificationCategory PICKUP_NOTIFICATION = new PickupNotificationCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.misc.smoothscrolling")
        @Config.Name("Smooth Scrolling")
        public final SmoothScrollingCategory SMOOTH_SCROLLING = new SmoothScrollingCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.misc.stg")
        @Config.Name("Swing Through Grass")
        public final SwingThroughGrassCategory SWING_THROUGH_GRASS = new SwingThroughGrassCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.misc.toastcontrol")
        @Config.Name("Toast Control")
        public final ToastControlCategory TOAST_CONTROL = new ToastControlCategory();

        @Config.RequiresMcRestart
        @Config.Name("Copy World Seed")
        @Config.Comment("Enables clicking of `/seed` world seed in chat to copy to clipboard")
        public boolean utCopyWorldSeedToggle = false;

        @Config.Name("Damage Tilt")
        @Config.Comment("Restores feature to tilt the camera when damaged")
        public boolean utDamageTiltToggle = true;

        @Config.Name("Default Difficulty")
        @Config.Comment("Sets the default difficulty for newly generated worlds")
        public EnumDifficulty utDefaultDifficulty = EnumDifficulty.NORMAL;

        @Config.RequiresMcRestart
        @Config.Name("Disable Narrator")
        @Config.Comment("Disables the narrator functionality entirely")
        public boolean utDisableNarratorToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("End Portal Parallax")
        @Config.Comment("Re-implements parallax rendering of the end portal from 1.10 and older")
        public boolean utEndPortalParallaxToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Infinite Music")
        @Config.Comment("Lets background music play continuously without delays")
        public boolean utInfiniteMusicToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Linear XP Amount")
        @Config.Comment
            ({
                "Sets the amount of XP needed for each level, effectively removing the increasing level scaling",
                "0 for vanilla default"
            })
        public int utLinearXP = 0;

        @Config.RequiresMcRestart
        @Config.Name("No Night Vision Flash")
        @Config.Comment("Disables the flashing effect when the night vision potion effect is about to run out")
        public boolean utNightVisionFlashToggle = false;

        @Config.Name("No Potion Shift")
        @Config.Comment("Disables the inventory shift when potion effects are active")
        public boolean utPotionShiftToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("No Smelting XP")
        @Config.Comment("Disables the experience reward when smelting items in furnaces")
        public boolean utSmeltingXPToggle = false;

        @Config.Name("Offhand Improvement")
        @Config.Comment("Prevents placing offhand blocks when blocks or food are held in the mainhand")
        public boolean utOffhandToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Remove Realms Button")
        @Config.Comment("Removes the redundant Minecraft Realms button from the main menu")
        public boolean utRealmsButtonToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Remove Recipe Book")
        @Config.Comment("Removes the recipe book button from GUIs")
        public boolean utRecipeBookToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Remove Snooper")
        @Config.Comment("Forcefully turns off the snooper and hides the snooper settings button from the options menu")
        public boolean utSnooperToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Skip Credits")
        @Config.Comment("Skips the credits screen after the player goes through the end podium portal")
        public boolean utSkipCreditsToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Skip Missing Registry Entries Screen")
        @Config.Comment
            ({
                "Automatically confirms the 'Missing Registry Entries' screen on world load",
                "Identical to the launch parameter `-Dfml.queryResult=confirm`"
            })
        public boolean utSkipRegistryScreenToggle = false;

        @Config.Name("Toggle Cheats Button")
        @Config.Comment("Adds a button to the pause menu to toggle cheats")
        public boolean utToggleCheatsToggle = true;

        public static class IncurablePotionsCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Incurable Potions Toggle")
            @Config.Comment("Determines if potion effects are curable with curative items like buckets of milk")
            public boolean utIncurablePotionsToggle = true;

            @Config.Name("[2] Potion Effect List")
            @Config.Comment("Syntax: modid:potioneffect")
            public String[] utIncurablePotionsList = new String[] {};

            @Config.Name("[3] List Mode")
            @Config.Comment
                ({
                    "Blacklist Mode: Potion effects incurable by curative items, others are curable",
                    "Whitelist Mode: Potion effects curable by curative items, others are incurable"
                })
            public EnumLists utIncurablePotionsListMode = EnumLists.BLACKLIST;
        }

        public static class LightningCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("Lightning Damage")
            @Config.Comment("Sets the damage lightning bolts deal to entities")
            public double utLightningDamage = 5.0D;

            @Config.RequiresMcRestart
            @Config.Name("Lightning Fire Ticks")
            @Config.Comment("Sets the duration in ticks lightning bolts set entities on fire")
            public int utLightningFireTicks = 8;

            @Config.RequiresMcRestart
            @Config.Name("No Lightning Fire")
            @Config.Comment("Disables the creation of fire around lightning strikes")
            public boolean utLightningFireToggle = false;

            @Config.RequiresMcRestart
            @Config.Name("No Lightning Flash")
            @Config.Comment("Disables the flashing of skybox and ground brightness on lightning bolt strikes")
            public boolean utLightningFlashToggle = false;
        }

        public static class LoadSoundsCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Mode")
            @Config.Comment("Play load sound on...")
            public EnumSoundModes utLoadSoundMode = EnumSoundModes.NOTHING;

            @Config.Name("[2] Minecraft Loaded Sounds")
            @Config.Comment({"Sounds to play when Minecraft is loaded", "Syntax: eventname;pitch"})
            public String[] utLoadSoundMC = new String[]
                {
                    "entity.experience_orb.pickup;1.0",
                    "entity.player.levelup;1.0"
                };

            @Config.Name("[3] World Loaded Sounds")
            @Config.Comment({"Sounds to play when the world is loaded", "Syntax: eventname;pitch"})
            public String[] utLoadSoundWorld = new String[]
                {
                    "entity.experience_orb.pickup;1.0",
                    "entity.player.levelup;1.0"
                };

            public enum EnumSoundModes
            {
                NOTHING,
                MINECRAFT,
                WORLD,
                MINECRAFT_AND_WORLD
            }
        }

        public static class PickupNotificationCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[01] Pickup Notification Toggle")
            @Config.Comment("Displays notifications when the player obtains or loses items")
            public boolean utPickupNotificationToggle = false;

            @Config.Name("[02] Display Item Additions")
            @Config.Comment("Displays item additions when a player obtains an item")
            public boolean utPUNItemAdditions = true;

            @Config.Name("[03] Display Item Removals")
            @Config.Comment("Displays item removals when a player loses an item")
            public boolean utPUNItemRemovals = true;

            @Config.Name("[04] Display Experience")
            @Config.Comment("Displays changes in player experience")
            public boolean utPUNExperience = true;

            @Config.Name("[05] Display Icon")
            @Config.Comment("Displays the icon of the respective item")
            public boolean utPUNDisplayIcon = true;

            @Config.Name("[06] Display Name")
            @Config.Comment("Displays the name of the respective item")
            public boolean utPUNDisplayName = true;

            @Config.Name("[07] Display Background")
            @Config.Comment("Displays a dark rectangle behind changed items")
            public boolean utPUNDisplayBackground = false;

            @Config.Name("[08] Display Offset Horizontal")
            @Config.Comment("Sets the horizontal offset of the notification")
            public int utPUNOffsetHorizontal = 0;

            @Config.Name("[09] Display Offset Vertical")
            @Config.Comment("Sets the vertical offset of the notification")
            public int utPUNOffsetVertical = 18;

            @Config.Name("[10] Snap Position")
            @Config.Comment("Sets the edge/corner of the screen to use as the base location")
            public EnumDrawPosition utPUNSnapPosition = EnumDrawPosition.BOTTOM_RIGHT;

            @Config.Name("[11] Name Scale")
            @Config.Comment("Sets the scaling of item names")
            public double utPUNScaleName = 0.8;

            @Config.Name("[12] Icon Scale")
            @Config.Comment("Sets the scaling of item icons")
            public double utPUNScaleIcon = 0.8;

            @Config.Name("[13] Soft Limit")
            @Config.Comment("Sets the maximum number of items in the queue before they start fading out artificially")
            public int utPUNSoftLimit = 6;

            @Config.Name("[14] Fade Limit")
            @Config.Comment("Sets the number of items that will be faded out after the soft limit is reached")
            public int utPUNFadeLimit = 3;

            @Config.Name("[15] Display Duration")
            @Config.Comment("Sets the duration in ticks how long the notification will be displayed")
            public int utPUNDisplayDuration = 120;

            @Config.Name("[16] Fade Duration")
            @Config.Comment("Sets the duration in ticks how long the notification fades out")
            public int utPUNFadeDuration = 20;

            @Config.Name("[17] Blacklist: Ignore Item Changes")
            @Config.Comment
                ({
                    "List of item registry names to ignore when displaying changes",
                    "Syntax: modid:item"
                })
            public String[] utPUNBlacklistItem = new String[] {};

            @Config.Name("[18] Blacklist: Ignore Subitem Changes")
            @Config.Comment
                ({
                    "List of item registry names for which to ignore subitem changes",
                    "Syntax: modid:item"
                })
            public String[] utPUNBlacklistSubitem = new String[] {};

            public enum EnumDrawPosition
            {
                BOTTOM_RIGHT,
                BOTTOM,
                BOTTOM_LEFT,
                LEFT,
                TOP_LEFT,
                TOP,
                TOP_RIGHT,
                RIGHT,
                CENTER
            }
        }

        public static class SmoothScrollingCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Smooth Scrolling Toggle")
            @Config.Comment("Adds smooth scrolling to in-game lists")
            public boolean utSmoothScrollingToggle = true;

            @Config.Name("[2] Bounce Back Multiplier")
            public double utSmoothScrollingBounce = 0.24;

            @Config.Name("[3] Scroll Duration")
            public int utSmoothScrollingDuration = 600;

            @Config.Name("[4] Scroll Step")
            public double utSmoothScrollingStep = 19.0;
        }

        public static class SwingThroughGrassCategory
        {
            @Config.Name("[1] Swing Through Grass Toggle")
            @Config.Comment("Allows hitting entities through grass instead of breaking it")
            public boolean utSwingThroughGrassToggle = true;

            @Config.Name("[2] Blacklist")
            @Config.Comment
                ({
                    "Excludes blocks from the swing through grass tweak",
                    "Syntax: modid:block"
                })
            public String[] utSwingThroughGrassBlacklist = new String[] {};

            @Config.Name("[3] Whitelist")
            @Config.Comment
                ({
                    "Includes blocks in the swing through grass tweak",
                    "Syntax: modid:block"
                })
            public String[] utSwingThroughGrassWhitelist = new String[] {};
        }

        public static class ToastControlCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Toast Control Toggle")
            @Config.Comment("Enables the control of toasts (pop-up text boxes)")
            public boolean utToastControlToggle = true;

            @Config.Name("[2] Disable Advancement Toasts")
            @Config.Comment("Determines if advancement toasts are blocked. Enabling will block ALL advancements.")
            public boolean utToastControlAdvancementsToggle = false;

            @Config.Name("[3] Disable Recipe Toasts")
            @Config.Comment("Determines if recipe unlock toasts are blocked. Blocks \"you have unlocked a new recipe\" toasts.")
            public boolean utToastControlRecipesToggle = true;

            @Config.Name("[4] Disable System Toasts")
            @Config.Comment("Determines if system toasts are blocked. This is used only for the narrator toggle notification right now.")
            public boolean utToastControlSystemToggle = true;

            @Config.RequiresMcRestart
            @Config.Name("[5] Disable Tutorial Toasts")
            @Config.Comment("Determines if tutorial toasts are blocked. Blocks useless things like use WASD to move.")
            public boolean utToastControlTutorialToggle = true;
        }
    }

    public static class TweaksPerformanceCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Auto Save Interval")
        @Config.Comment("Determines the interval in ticks between world auto saves")
        public int utAutoSaveInterval = 900;

        @Config.RequiresMcRestart
        @Config.Name("Check Animated Models")
        @Config.Comment("Improves model load times by checking if an animated model exists before trying to load it")
        public boolean utCheckAnimatedModelsToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Crafting Cache")
        @Config.Comment("Adds an IRecipe cache to improve recipe performance in larger modpacks")
        public boolean utCraftingCacheToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Disable Audio Debug")
        @Config.Comment("Improves loading times by removing debug code for missing sounds and subtitles")
        public boolean utDisableAudioDebugToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Disable Fancy Missing Model")
        @Config.Comment("Improves rendering performance by removing the resource location text on missing models")
        public boolean utDisableFancyMissingModelToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Faster Background Startup")
        @Config.Comment("Fixes slow background startup edge case caused by checking tooltips during the loading process")
        public boolean utFasterBackgroundStartupToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Fast Dye Blending")
        @Config.Comment("Replaces color lookup for sheep to check a predefined table rather than querying the recipe registry")
        public boolean utDyeBlendingToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Fast Prefix Checking")
        @Config.Comment("Optimizes Forge's ID prefix checking and removes prefix warnings impacting load time")
        public boolean utPrefixCheckToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Fast World Loading")
        @Config.Comment
            ({
                "Skips initial world chunk loading & garbage collection to speed up world loading",
                "May have side effects such as slower chunk generation"
            })
        public boolean utWorldLoadingToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("No Redstone Lighting")
        @Config.Comment("Disables lighting of active redstone, repeaters, and comparators to improve performance")
        public boolean utRedstoneLightingToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Uncap FPS")
        @Config.Comment("Removes the hardcoded 30 FPS limit in screens like the main menu")
        public boolean utUncapFPSToggle = true;
    }

    public static class TweaksWorldCategory
    {
        @Config.LangKey("cfg.universaltweaks.tweaks.world.chunkgenlimit")
        @Config.Name("Chunk Gen Limit")
        public final ChunkGenLimitCategory CHUNK_GEN_LIMIT = new ChunkGenLimitCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.world.dimensionunload")
        @Config.Name("Dimension Unload")
        public final DimensionUnloadCategory DIMENSION_UNLOAD = new DimensionUnloadCategory();

        @Config.RequiresMcRestart
        @Config.Name("Stronghold Replacement")
        @Config.Comment("Replaces stronghold generation with a safer variant")
        public boolean utStrongholdToggle = true;

        @Config.Name("Tidy Chunk")
        @Config.Comment("Tidies newly generated chunks by removing scattered item entities")
        public boolean utTidyChunkToggle = false;

        public static class ChunkGenLimitCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Chunk Gen Limit Toggle")
            @Config.Comment("Limits maximum chunk generation per tick for improved server performance")
            public boolean utChunkGenLimitToggle = false;

            @Config.Name("[2] Ticks")
            @Config.Comment("Maximum chunks to generate per tick per dimension")
            public int utChunkGenLimitTicks = 2;

            @Config.Name("[3] Time")
            @Config.Comment("Maximum time in ms to spend generating chunks per tick per dimension")
            public int utChunkGenLimitTime = 5;
        }

        public static class DimensionUnloadCategory
        {
            @Config.Name("[1] Dimension Unload Toggle")
            @Config.Comment("Unloads dimensions not in use to free up resources")
            public boolean utUnloaderToggle = true;

            @Config.Name("[2] Interval")
            @Config.Comment("Time (in ticks) to wait before checking dimensions")
            public int utUnloaderInterval = 600;

            @Config.Name("[3] Blacklist")
            @Config.Comment
                ({
                    "List of dimensions which should not be unloaded",
                    "Can be dimension name or ID",
                    "Uses regular expressions"
                })
            public String[] utUnloaderBlacklist = new String[]
                {
                    "0",
                    "overworld"
                };
        }
    }

    public static class DebugCategory
    {
        @Config.Name("Bypass Incompatibility Warnings")
        @Config.Comment("For those who live life on the edge, may or may not include Jons")
        public boolean utBypassIncompatibilityToggle = false;

        @Config.Name("Debug Logging")
        @Config.Comment("Enables debug logging")
        public boolean utDebugToggle = false;

        @Config.Name("Show Loading Time")
        @Config.Comment("Prints the time the game needed to launch to the log")
        public boolean utLoadingTimeToggle = true;
    }

    public static class ModIntegrationCategory
    {
        @Config.LangKey("cfg.universaltweaks.modintegration.abyssalcraft")
        @Config.Name("AbyssalCraft")
        public final AbyssalCraftCategory ABYSSALCRAFT = new AbyssalCraftCategory();

        @Config.LangKey("cfg.universaltweaks.modintegration.aoa")
        @Config.Name("Advent of Ascension")
        public final AOACategory AOA = new AOACategory();

        @Config.LangKey("cfg.universaltweaks.modintegration.bop")
        @Config.Name("Biomes O' Plenty")
        public final BiomesOPlentyCategory BIOMES_O_PLENTY = new BiomesOPlentyCategory();

        @Config.LangKey("cfg.universaltweaks.modintegration.botania")
        @Config.Name("Botania")
        public final BotaniaCategory BOTANIA = new BotaniaCategory();

        @Config.LangKey("cfg.universaltweaks.modintegration.cqrepoured")
        @Config.Name("Chocolate Quest Repoured")
        public final ChocolateQuestCategory CHOCOLATE_QUEST = new ChocolateQuestCategory();

        @Config.LangKey("cfg.universaltweaks.modintegration.cofhcore")
        @Config.Name("CoFH Core")
        public final CoFHCoreCategory COFH_CORE = new CoFHCoreCategory();

        @Config.LangKey("cfg.universaltweaks.modintegration.elementarystaffs")
        @Config.Name("Elementary Staffs")
        public final ElementaryStaffsCategory ELEMENTARY_STAFFS = new ElementaryStaffsCategory();

        @Config.LangKey("cfg.universaltweaks.modintegration.elenaidodge2")
        @Config.Name("Elenai Dodge 2")
        public final ElenaiDodge2Category ELENAI_DODGE_2 = new ElenaiDodge2Category();

        @Config.LangKey("cfg.universaltweaks.modintegration.esm")
        @Config.Name("Epic Siege Mod")
        public final EpicSiegeModCategory EPIC_SIEGE_MOD = new EpicSiegeModCategory();

        @Config.LangKey("cfg.universaltweaks.modintegration.forestry")
        @Config.Name("Forestry")
        public final ForestryCategory FORESTRY = new ForestryCategory();

        @Config.LangKey("cfg.universaltweaks.modintegration.infernalmobs")
        @Config.Name("Infernal Mobs")
        public final InfernalMobsCategory INFERNAL_MOBS = new InfernalMobsCategory();

        @Config.LangKey("cfg.universaltweaks.modintegration.roost")
        @Config.Name("Roost")
        public final RoostCategory ROOST = new RoostCategory();

        @Config.LangKey("cfg.universaltweaks.modintegration.simpledifficulty")
        @Config.Name("Simple Difficulty")
        public final SimpleDifficultyCategory SIMPLE_DIFFICULTY = new SimpleDifficultyCategory();

        @Config.LangKey("cfg.universaltweaks.modintegration.sd")
        @Config.Name("Storage Drawers")
        public final StorageDrawersCategory STORAGE_DRAWERS = new StorageDrawersCategory();

        @Config.LangKey("cfg.universaltweaks.modintegration.tr")
        @Config.Name("Tech Reborn")
        public final TechRebornCategory TECH_REBORN = new TechRebornCategory();

        @Config.LangKey("cfg.universaltweaks.modintegration.tc")
        @Config.Name("Thaumcraft")
        public final ThaumcraftCategory THAUMCRAFT = new ThaumcraftCategory();

        @Config.LangKey("cfg.universaltweaks.modintegration.tc.entities")
        @Config.Name("Thaumcraft: Entities")
        public final ThaumcraftEntitiesCategory THAUMCRAFT_ENTITIES = new ThaumcraftEntitiesCategory();

        @Config.LangKey("cfg.universaltweaks.modintegration.tc.foci")
        @Config.Name("Thaumcraft: Foci")
        public final ThaumcraftFociCategory THAUMCRAFT_FOCI = new ThaumcraftFociCategory();

        @Config.LangKey("cfg.universaltweaks.modintegration.te")
        @Config.Name("Thermal Expansion")
        public final ThermalExpansionCategory THERMAL_EXPANSION = new ThermalExpansionCategory();

        @Config.LangKey("cfg.universaltweaks.modintegration.tcon")
        @Config.Name("Tinkers' Construct")
        public final TinkersConstructCategory TINKERS_CONSTRUCT = new TinkersConstructCategory();

        public static class AbyssalCraftCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("Optimized Item Transport")
            @Config.Comment("Makes an optimization to reduce tick overhead of AbyssalCraft's item transport system")
            public boolean utOptimizedItemTransferToggle = true;
        }

        public static class AOACategory
        {
            @Config.RequiresMcRestart
            @Config.Name("Inventory-less GUI Compatibility")
            @Config.Comment("Fixes AoA player ticking in certain GUIs without player inventories (i.e. Flux Networks GUI)")
            public boolean utFixPlayerTickInInventorylessGui = false;
        }

        public static class BiomesOPlentyCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("Hot Spring Water")
            @Config.Comment("Fixes rapid inflection of regeneration effects in hot spring water")
            public boolean utBoPHotSpringWaterToggle = true;
        }

        public static class BotaniaCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("Fancy Skybox")
            @Config.Comment
                ({
                    "Enables the Botania Garden of Glass skybox for custom dimensions",
                    "Abides by Botania's 'enableFancySkybox' config option",
                    "Example: 43"
                })
            public Integer[] utBotaniaSkyboxDims = new Integer[] {};
        }

        public static class ChocolateQuestCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("Legacy Golden Feather")
            @Config.Comment("Restores the golden feather behavior from the original Better Dungeons mod")
            public boolean utCQRGoldenFeatherToggle = true;
        }

        public static class CoFHCoreCategory
        {
            @Config.Name("Vorpal Enchantment Damage")
            @Config.Comment("Sets the damage multiplier of the Vorpal enchantment")
            public double utCoFHVorpalDamage = 10.0D;
        }

        public static class ElementaryStaffsCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("Electric Staff Port")
            @Config.Comment("Reintroduces the 1.5 electric staff behavior along with some subtle particles")
            public boolean utESElectricStaffToggle = true;
        }

        public static class ElenaiDodge2Category
        {
            @Config.RequiresMcRestart
            @Config.Name("Feathers Helper API Fix")
            @Config.Comment("Fixes server-sided crashes when the Feathers Helper API is utilized")
            public boolean utED2FeathersHelperToggle = true;
        }

        public static class EpicSiegeModCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("Disable Digger AI Debug")
            @Config.Comment("Disables leftover debug logging inside the digger AI of the beta builds")
            public boolean utESMDiggerDebugToggle = true;
        }

        public static class ForestryCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("Arborist Villager Trades")
            @Config.Comment
                ({
                    "Adds custom emerald to germling trades to the arborist villager",
                    "Syntax:        level;emeralds_min;emeralds_max;germlings_min;germlings_max;type;complexity_min;complexity_max",
                    "level          Level when this trade becomes available (how much trading needs to be done)",
                    "emeralds_min   Lower random limit for emeralds",
                    "emeralds_max   Upper random limit for emeralds",
                    "germlings_min  Lower random limit for germlings",
                    "germlings_max  Upper random limit for germlings",
                    "type           Type of germling, can be either pollen or sapling",
                    "complexity_min Lower limit of allele complexity",
                    "complexity_max Upper limit of allele complexity",
                    "",
                    "Example for a level 5 trade for a single sapling with a complexity between 6 and 10, costing between 10 to 40 emeralds:",
                    "5;10;40;1;1;sapling;6;10"
                })
            public String[] utFOArboristDeals = new String[] {};

            @Config.RequiresMcRestart
            @Config.Name("Disable Bee Damage Armor Bypass")
            @Config.Comment("Disables damage caused by bees bypassing player armor")
            public boolean utFOBeeDamageArmorBypassToggle = true;

            @Config.RequiresMcRestart
            @Config.Name("Extra Trees: Gather Windfall")
            @Config.Comment("Allows Forestry farms to pick up ExtraTrees fruit")
            public boolean utFOGatherWindfallToggle = true;

            @Config.RequiresMcRestart
            @Config.Name("Replanting Cocoa Beans")
            @Config.Comment("Allows Forestry farms to automatically replant cocoa beans")
            public boolean utFOCocoaBeansToggle = true;
        }

        public static class InfernalMobsCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("Sticky Recall Compatibility")
            @Config.Comment("Enables compatibility between Infernal Mobs' Sticky effect and Capsule's Recall enchantment")
            public boolean utIMStickyRecallToggle = true;
        }

        public static class RoostCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("ContentTweaker: Early Register CT Chickens")
            @Config.Comment
                ({
                    "Improves load time by registering CT chickens early for Roost to detect them",
                    "Note: All CT chickens must be specified in \"Custom Chickens\" for this tweak to work!",
                    "Note: In your .zs files, to use ContentTweaker's MaterialSystem Parts, you must:",
                    "1) Use '#loader finalize_contenttweaker', not '#loader contenttweaker'",
                    "2) Use the Material Part Bracket Handler to reference the item"
                })
            public boolean utRoostEarlyRegisterCTChickens = false;

            @Config.RequiresMcRestart
            @Config.Name("Custom Chickens")
            @Config.Comment
                ({
                    "Adds custom chickens from mods (e.g. ContentTweaker) to Roost's stock texture check",
                    "Syntax: name",
                    "name     Chicken name",
                })
            public String[] utRoostChickenMods = new String[] {};
        }

        public static class SimpleDifficultyCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("Iron Canteen Interaction Fix")
            @Config.Comment("Fixes the interaction of iron canteens with rain collectors")
            public boolean utRainCollectorCanteenToggle = true;
        }

        public static class StorageDrawersCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("Item Handlers")
            @Config.Comment
                ({
                    "Fixes voiding of items when nearing full capacity",
                    "Fixes slotless item handler implementation not allowing the extraction from compacting item drawers with the vending upgrade",
                    "Caches the drawer controller tile to avoid getting the TE from the world every time a drawer slave is interacted with"
                })
            public boolean utSDItemHandlers = false;

            @Config.RequiresMcRestart
            @Config.Name("Render Range")
            @Config.Comment
                ({
                    "Approximate range in blocks at which drawers render contained items",
                    "0 for default unlimited range"
                })
            public int utSDRenderRange = 0;
        }

        public static class TechRebornCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("Optimized Rolling Machine")
            @Config.Comment("Optimizes the Rolling Machine to reduce tick time")
            public boolean utOptimizeRollingMachineToggle = true;
        }

        public static class ThaumcraftCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("Flower Bounding Box")
            @Config.Comment("Fixes the bounding box always being at the center in both cinderpearls and shimmerleafs")
            public boolean utTCFlowerBoundingBoxToggle = true;

            @Config.RequiresMcRestart
            @Config.Name("Stable Thaumometer")
            @Config.Comment("Stops the thaumometer from bobbing rapidly when using it to scan objects")
            public boolean utTCStableThaumometerToggle = true;
        }

        public static class ThaumcraftEntitiesCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("Firebat Particles")
            @Config.Comment("Adds particles to firebats similar to legacy versions")
            public boolean utTCFirebatParticlesToggle = true;

            @Config.RequiresMcRestart
            @Config.Name("Spiderlike Eldritch Crabs")
            @Config.Comment("Rotates dead eldritch crabs all the way like endermites, silverfish, and spiders")
            public boolean utTCSpiderlikeEldritchCrabToggle = true;

            @Config.RequiresMcRestart
            @Config.Name("Wisp Particles")
            @Config.Comment("Increases particle size of wisps similar to legacy versions")
            public boolean utTCWispParticlesToggle = true;
        }

        public static class ThaumcraftFociCategory
        {
            @Config.LangKey("cfg.universaltweaks.modintegration.tc.foci.focuseffects")
            @Config.Name("Focus Effects")
            public final FocusEffectsCategory FOCUS_EFFECTS = new FocusEffectsCategory();

            @Config.LangKey("cfg.universaltweaks.modintegration.tc.foci.focusmediums")
            @Config.Name("Focus Mediums")
            public final FocusMediumsCategory FOCUS_MEDIUMS = new FocusMediumsCategory();

            public static class FocusEffectsCategory
            {
                @Config.RequiresMcRestart
                @Config.Name("[01] Break: Cast Sound Revamp")
                @Config.Comment("Overhauls the break focus effect cast sound")
                public boolean utTCBreakFocusSoundRevampToggle = true;

                @Config.RequiresMcRestart
                @Config.Name("[02] Break: Impact Sound")
                @Config.Comment("Adds an impact sound to the break focus effect")
                public boolean utTCBreakFocusImpactSoundToggle = true;

                @Config.RequiresMcRestart
                @Config.Name("[03] Earth: Cast Sound Revamp")
                @Config.Comment("Overhauls the earth focus effect cast sound")
                public boolean utTCEarthFocusSoundRevampToggle = true;

                @Config.RequiresMcRestart
                @Config.Name("[04] Earth: Impact Sound")
                @Config.Comment("Adds an impact sound to the earth focus effect")
                public boolean utTCEarthFocusImpactSoundToggle = true;

                @Config.RequiresMcRestart
                @Config.Name("[05] Fire: Impact Sound")
                @Config.Comment("Adds an impact sound to the fire focus effect")
                public boolean utTCFireFocusImpactSoundToggle = true;

                @Config.RequiresMcRestart
                @Config.Name("[06] Flux: Impact Sound")
                @Config.Comment("Adds an impact sound to the flux focus effect")
                public boolean utTCFluxFocusImpactSoundToggle = true;

                @Config.RequiresMcRestart
                @Config.Name("[07] Frost: Cast Sound Revamp")
                @Config.Comment("Overhauls the frost focus effect cast sound to make it a lot less plangent")
                public boolean utTCFrostFocusSoundRevampToggle = true;

                @Config.RequiresMcRestart
                @Config.Name("[08] Frost: Impact Sound")
                @Config.Comment("Adds an impact sound to the frost focus effect")
                public boolean utTCFrostFocusImpactSoundToggle = true;

                @Config.RequiresMcRestart
                @Config.Name("[09] Heal: Cast Sound Revamp")
                @Config.Comment("Overhauls the heal focus effect cast sound")
                public boolean utTCHealFocusSoundRevampToggle = true;

                @Config.RequiresMcRestart
                @Config.Name("[10] Heal: Impact Sound")
                @Config.Comment("Adds an impact sound to the heal focus effect")
                public boolean utTCHealFocusImpactSoundToggle = true;

                @Config.RequiresMcRestart
                @Config.Name("[11] Rift: Cast Sound Revamp")
                @Config.Comment("Overhauls the rift focus effect cast sound")
                public boolean utTCRiftFocusSoundRevampToggle = true;

                @Config.RequiresMcRestart
                @Config.Name("[12] Rift: Impact Sound")
                @Config.Comment("Adds an impact sound to the rift focus effect")
                public boolean utTCRiftFocusImpactSoundToggle = true;
            }

            public static class FocusMediumsCategory
            {
                @Config.RequiresMcRestart
                @Config.Name("[1] Bolt: Cast Sound")
                @Config.Comment("Plays an additional cast sound when using any bolt focus medium to add an extra layer of pow")
                public boolean utTCBoltMediumSoundToggle = true;

                @Config.RequiresMcRestart
                @Config.Name("[2] Cloud: Cast Sound")
                @Config.Comment("Plays an additional cast sound when using any cloud focus medium")
                public boolean utTCCloudMediumSoundToggle = true;

                @Config.RequiresMcRestart
                @Config.Name("[3] Mine: Sound Overhaul")
                @Config.Comment("Adds additional cast, despawn, and setup sounds when using any mine focus medium")
                public boolean utTCMineMediumSoundToggle = true;

                @Config.RequiresMcRestart
                @Config.Name("[4] Spellbat: Cast Sound")
                @Config.Comment("Plays an additional cast sound when summoning any type of spellbat")
                public boolean utTCSpellBatMediumSoundToggle = true;
            }
        }

        public static class ThermalExpansionCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("Insolator Custom Monoculture")
            @Config.Comment
                ({
                    "Adds Monoculture Cycle integration to desired phytogenic insolator recipes added by ModTweaker",
                    "Register the recipe with Insolator.addRecipeMonoculture() instead of Insolator.addRecipe() in .zs files",
                    "(and Insolator.addRecipeMonocultureSaplingInfuser() instead of Insolator.addRecipeSaplingInfuser())",
                    "Note: Make sure the 'fertilizer' is specified as the secondaryInput arg in the function"
                })
            public boolean utTEInsolatorCustomMonoculture = true;
        }

        public static class TinkersConstructCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("Gaseous Fluids")
            @Config.Comment("Excludes gaseous fluids from being transferable via faucets")
            public boolean utTConGaseousFluidsToggle = false;

            @Config.RequiresMcRestart
            @Config.Name("Projectile Despawning")
            @Config.Comment("Despawns unbreakable projectiles faster to improve framerates")
            public boolean utTConProjectileToggle = true;

            @Config.RequiresMcRestart
            @Config.Name("Offhand Shuriken")
            @Config.Comment("Suppresses special abilities of long swords and rapiers when shurikens are wielded in the offhand")
            public boolean utTConShurikenToggle = true;

            @Config.RequiresMcRestart
            @Config.Name("Ore Dictionary Cache")
            @Config.Comment("Caches all ore dictionary smelting recipes to speed up game loading")
            public boolean utTConOreDictCacheToggle = true;
        }
    }

    static
    {
        ConfigAnytime.register(UTConfig.class);
    }

    @Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
    public static class EventHandler
    {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
        {
            if (event.getModID().equals(UniversalTweaks.MODID))
            {
                ConfigManager.sync(UniversalTweaks.MODID, Config.Type.INSTANCE);
                if (TWEAKS_BLOCKS.BREAKABLE_BEDROCK.utBreakableBedrockToggle) UTBreakableBedrock.initToolList();
                if (TWEAKS_MISC.SWING_THROUGH_GRASS.utSwingThroughGrassToggle) UTSwingThroughGrassLists.initLists();
                if (TWEAKS_MISC.INCURABLE_POTIONS.utIncurablePotionsToggle) UTIncurablePotions.initPotionList();
                if (TWEAKS_ITEMS.utCustomRarities.length > 0) UTCustomRarity.initItemRarityMap();
                if (TWEAKS_ITEMS.utCustomUseDurations.length > 0) UTCustomUseDuration.initItemUseMaps();
                if (TWEAKS_ITEMS.PARRY.utParryToggle) UTParry.initProjectileList();
                if (UTLoadingPlugin.isClient)
                {
                    if (BUGFIXES_BLOCKS.BLOCK_OVERLAY.utBlockOverlayToggle) UTBlockOverlayLists.initLists();
                    if (Loader.isModLoaded("botania")) UTBotaniaFancySkybox.initDimList();
                    if (TWEAKS_MISC.LOAD_SOUNDS.utLoadSoundMode != TweaksMiscCategory.LoadSoundsCategory.EnumSoundModes.NOTHING) UTLoadSound.initLists();
                    UTAutoSaveOFCompat.updateOFConfig();
                }
                UTObsoleteModsHandler.showObsoleteMods = true;
                UniversalTweaks.LOGGER.info("Universal Tweaks config reloaded");
            }
        }
    }
}