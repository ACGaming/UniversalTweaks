package mod.acgaming.universaltweaks.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.cleanroommc.configanytime.ConfigAnytime;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.bugfixes.blocks.blockoverlay.UTBlockOverlayLists;
import mod.acgaming.universaltweaks.bugfixes.entities.desync.UTEntityDesync;
import mod.acgaming.universaltweaks.core.UTLoadingPlugin;

@Config(modid = UniversalTweaks.MODID, name = UniversalTweaks.NAME + " - Bugfixes")
public class UTConfigBugfixes
{
    @Config.LangKey("cfg.universaltweaks.config.blocks")
    @Config.Name("Blocks")
    public static final BlocksCategory BLOCKS = new BlocksCategory();

    @Config.LangKey("cfg.universaltweaks.config.entities")
    @Config.Name("Entities")
    public static final EntitiesCategory ENTITIES = new EntitiesCategory();

    @Config.LangKey("cfg.universaltweaks.config.misc")
    @Config.Name("Misc")
    public static final MiscCategory MISC = new MiscCategory();

    @Config.LangKey("cfg.universaltweaks.config.world")
    @Config.Name("World")
    public static final WorldCategory WORLD = new WorldCategory();

    public static class BlocksCategory
    {
        @Config.LangKey("cfg.universaltweaks.bugfixes.blocks.blockoverlay")
        @Config.Name("Block Overlay")
        public final BlockOverlayCategory BLOCK_OVERLAY = new BlockOverlayCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.blocks.miningglitch")
        @Config.Name("Mining Glitch")
        public final MiningGlitchCategory MINING_GLITCH = new MiningGlitchCategory();

        @Config.RequiresMcRestart
        @Config.Name("Banner Bounding Box")
        @Config.Comment
            ({
                "Fixes rendering issues with banners by correctly sizing their render bounding boxes",
                "Incompatible with RenderLib"
            })
        public boolean utBannerBoundingBoxToggle = true;

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
        @Config.Name("Piston Progress")
        @Config.Comment("Properly saves the last state of pistons to tags")
        public boolean utPistonTileToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Piston Retraction")
        @Config.Comment("Improves retraction behavior on double piston extenders")
        public boolean utPistonRetractionToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Sleep Resets Weather")
        @Config.Comment("Fixes sleeping always resetting rain and thunder times")
        public boolean utSleepResetsWeatherToggle = true;

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

        public static class MiningGlitchCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Mining Glitch Toggle")
            @Config.Comment("Prevents ghost blocks by sending additional movement packets")
            public boolean utMiningGlitchToggle = true;

            @Config.Name("[2] Maximum Movement Packets")
            @Config.Comment
                ({
                    "Defines the maximum number of movement packets that can be sent per tick",
                    "Vanilla default is 5"
                })
            public int utMiningGlitchPackets = 10;
        }
    }

    public static class EntitiesCategory
    {
        @Config.LangKey("cfg.universaltweaks.bugfixes.entities.desync")
        @Config.Name("Entity Desync")
        public final EntityDesyncCategory ENTITY_DESYNC = new EntityDesyncCategory();

        @Config.LangKey("cfg.universaltweaks.bugfixes.entities.entitylists")
        @Config.Name("Entity Lists")
        public final EntityListsCategory ENTITY_LISTS = new EntityListsCategory();

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
        @Config.Comment("Fixes item duplications when players are dropping items and disconnecting")
        public boolean utDisconnectDupeToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Dimension Change Player States")
        @Config.Comment("Fixes missing player states when changing dimensions by sending additional packets")
        public boolean utDimensionChangeToggle = true;

        @Config.Name("Double Consumption")
        @Config.Comment("Fixes consuming an item having a chance of also consuming a second item without any animation")
        public boolean utDoubleConsumptionToggle = true;

        @Config.Name("Donkey/Mule Dupe")
        @Config.Comment("Fixes a duplication exploit connected to the inventories of donkeys and mules")
        public boolean utDonkeyMuleDupeToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Elytra Deployment & Landing")
        @Config.Comment("Relocate elytra deployment and landing to client side to prevent issues with high latencies")
        public boolean utElytraDeploymentLandingToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Fixes Invisible Player when Flying with Elytra")
        @Config.Comment("Fixes the player model occasionally disappearing when flying with elytra in a straight line in third-person mode")
        public boolean utFixInvisiblePlayerModelWithElytra = true;

        @Config.RequiresMcRestart
        @Config.Name("Entity Bounding Box")
        @Config.Comment("Saves entity bounding boxes to tags to prevent breakouts and suffocation")
        public boolean utEntityAABBToggle = true;

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

        @Config.Name("Horse Falling")
        @Config.Comment("Modifies falling logic of horses, listening to LivingFallEvent and taking jump boost into account")
        public boolean utHorseFallingToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Max Player Health")
        @Config.Comment("Corrects maximum player health on joining by setting the last saved health value")
        public boolean utMaxHealthToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Minecart AI")
        @Config.Comment("Fixes non-player entities being able to control minecarts")
        public boolean utMinecartAIToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Mount Desync")
        @Config.Comment("Fixes mounts and boats sometimes disappearing after dismounting")
        public boolean utMountDesyncToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Player Saturation")
        @Config.Comment("Fixes saturation depleting in peaceful mode")
        public boolean utExhaustionToggle = true;

        @Config.Name("Shear Mooshroom Dupe")
        @Config.Comment("Fixes a duplication exploit connected to shearing mooshrooms")
        public boolean utShearMooshroomDupeToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Skeleton Aim")
        @Config.Comment("Fixes skeletons not looking at their targets when strafing")
        public boolean utSkeletonAimToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Untipped Arrow Particles")
        @Config.Comment("Fixes untipped arrows emitting blue tipped arrow particles upon reloading a world")
        public boolean utUntippedArrowParticlesToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Villager Mantle Hoods")
        @Config.Comment("Returns missing hoods to villager mantles")
        public boolean utVillagerMantleToggle = true;

        public static class EntityDesyncCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Entity Desync Toggle")
            @Config.Comment("Fixes entity motion desyncs most notable with arrows and thrown items")
            public boolean utEntityDesyncToggle = true;

            @Config.Name("[2] Entity Blacklist")
            @Config.Comment
                ({
                    "Syntax:  modid:entity",
                    "Example: minecraft:minecart"
                })
            public String[] utEntityDesyncBlacklist = new String[]
                {
                    "minecraft:minecart",
                    "pixelmon:empty_pokeball",
                    "pixelmon:occupied_pokeball"
                };
        }

        public static class EntityListsCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("Chunk Updates")
            @Config.Comment("Fixes chunk entity lists often not getting updated correctly")
            public boolean utChunkUpdatesToggle = true;

            @Config.RequiresMcRestart
            @Config.Name("World Additions")
            @Config.Comment("Fixes client-side memory leak where some entity ids are not set before being added to the world's entity list")
            public boolean utWorldAdditionsToggle = true;
        }
    }

    public static class MiscCategory
    {
        @Config.LangKey("cfg.universaltweaks.bugfixes.misc.modelgap")
        @Config.Name("Model Gap")
        public final ModelGapCategory MODEL_GAP = new ModelGapCategory();

        @Config.RequiresMcRestart
        @Config.Name("Accurate Smooth Lighting")
        @Config.Comment("Improves the accuracy of smooth lighting by checking for suffocation and light opacity")
        public boolean utAccurateSmoothLighting = true;

        @Config.RequiresMcRestart
        @Config.Name("Crafted Item Statistics")
        @Config.Comment("Fixes crafted item statistics not increasing correctly when items are crafted with shift-click or drop methods")
        public boolean utCraftedItemStatisticsToggle = true;

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

        @Config.RequiresMcRestart
        @Config.Name("Particle Spawning")
        @Config.Comment("Fixes various particle types not showing up on the client")
        public boolean utParticleSpawningToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Potion Amplifier Visibility")
        @Config.Comment("Fixes potion effects not displaying their level above 'IV'")
        public boolean utPotionAmplifierVisibilityToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Spectator Menu")
        @Config.Comment("Fixes the spectator menu not showing player skins")
        public boolean utSpectatorMenuToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Packet Size")
        @Config.Comment
            ({
                "Increases the packet size limit to account for large packets in modded environments",
                "Vanilla default is 0x200000",
                "Incompatible with SpongeForge and RandomPatches"
            })
        public int utPacketSize = 0x1000000;

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

    public static class WorldCategory
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
        @Config.Name("Portal Traveling Dupe")
        @Config.Comment("Fixes duplication issues that can occur when entities travel through portals")
        public boolean utPortalTravelingDupeToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Witch Huts")
        @Config.Comment("Fixes witch hut structure data not accounting for the height it is generated at")
        public boolean utWitchStructuresToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Tile Entity Map")
        @Config.Comment
            ({
                "Changes the data table of tile entities to resolve issues",
                "HASHMAP:                   Vanilla default",
                "LINKED_HASHMAP:            Keeps the loading order of tile entities to prevent issues during the first ticks of chunk loading",
                "CONCURRENT_HASHMAP:        Allows simultaneous access to tile entities to prevent concurrent modification exceptions",
                "CONCURRENT_LINKED_HASHMAP: Combines LINKED_HASHMAP and CONCURRENT_HASHMAP, may have random side effects",
            })
        public EnumMaps utTileEntityMap = EnumMaps.LINKED_HASHMAP;

        public enum EnumMaps
        {
            HASHMAP,
            LINKED_HASHMAP,
            CONCURRENT_HASHMAP,
            CONCURRENT_LINKED_HASHMAP
        }
    }

    static
    {
        ConfigAnytime.register(UTConfigBugfixes.class);
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
                if (ENTITIES.ENTITY_DESYNC.utEntityDesyncToggle) UTEntityDesync.initBlacklistedEntityEntries();
                if (UTLoadingPlugin.isClient)
                {
                    if (BLOCKS.BLOCK_OVERLAY.utBlockOverlayToggle) UTBlockOverlayLists.initLists();
                }
            }
        }
    }
}