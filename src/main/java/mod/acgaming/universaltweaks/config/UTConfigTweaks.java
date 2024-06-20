package mod.acgaming.universaltweaks.config;

import java.util.HashMap;
import java.util.Map;
import com.cleanroommc.configanytime.ConfigAnytime;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.core.UTLoadingPlugin;
import mod.acgaming.universaltweaks.tweaks.blocks.breakablebedrock.UTBreakableBedrock;
import mod.acgaming.universaltweaks.tweaks.items.parry.UTParry;
import mod.acgaming.universaltweaks.tweaks.items.rarity.UTCustomRarity;
import mod.acgaming.universaltweaks.tweaks.items.useduration.UTCustomUseDuration;
import mod.acgaming.universaltweaks.tweaks.misc.armorcurve.UTArmorCurve;
import mod.acgaming.universaltweaks.tweaks.misc.incurablepotions.UTIncurablePotions;
import mod.acgaming.universaltweaks.tweaks.misc.loadsound.UTLoadSound;
import mod.acgaming.universaltweaks.tweaks.misc.swingthroughgrass.UTSwingThroughGrassLists;
import mod.acgaming.universaltweaks.tweaks.performance.autosave.UTAutoSaveOFCompat;
import mod.acgaming.universaltweaks.tweaks.performance.entityradiuscheck.UTEntityRadiusCheck;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = UniversalTweaks.MODID, name = UniversalTweaks.NAME + " - Tweaks")
public class UTConfigTweaks
{
    @Config.LangKey("cfg.universaltweaks.config.blocks")
    @Config.Name("Blocks")
    public static final BlocksCategory BLOCKS = new BlocksCategory();

    @Config.LangKey("cfg.universaltweaks.config.entities")
    @Config.Name("Entities")
    public static final EntitiesCategory ENTITIES = new EntitiesCategory();

    @Config.LangKey("cfg.universaltweaks.config.items")
    @Config.Name("Items")
    public static final ItemsCategory ITEMS = new ItemsCategory();

    @Config.LangKey("cfg.universaltweaks.config.misc")
    @Config.Name("Misc")
    public static final MiscCategory MISC = new MiscCategory();

    @Config.LangKey("cfg.universaltweaks.config.performance")
    @Config.Name("Performance")
    public static final PerformanceCategory PERFORMANCE = new PerformanceCategory();

    @Config.LangKey("cfg.universaltweaks.config.world")
    @Config.Name("World")
    public static final WorldCategory WORLD = new WorldCategory();

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

    public enum TrampleOptions
    {
        DEFAULT,
        NEVER,
        ONLY_PLAYER,
        NOT_PLAYER,
        FEATHER_FALLING
    }

    public enum EnumBeacon
    {
        MODIFIER(false, true),
        ENFORCED(true, false),
        ENFORCED_MODIFIER(true, true);

        public final boolean isEnforce;
        public final boolean isModifier;

        EnumBeacon(boolean isEnforce, boolean isModifier)
        {
            this.isEnforce = isEnforce;
            this.isModifier = isModifier;
        }
    }

    public static class BlocksCategory
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

        @Config.LangKey("cfg.universaltweaks.tweaks.blocks.overhaulbeacon")
        @Config.Name("Overhaul Beacon")
        public final OverhaulBeaconCategory OVERHAUL_BEACON = new OverhaulBeaconCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.blocks.sapling")
        @Config.Name("Sapling Behavior")
        public final SaplingBehaviorCategory SAPLING_BEHAVIOR = new SaplingBehaviorCategory();

        @Config.RequiresMcRestart
        @Config.Name("Bed Obstruction Replacement")
        @Config.Comment("Replaces bed obstruction checks with an improved version")
        public boolean utBedObstructionToggle = true;

        @Config.Name("Better Harvest")
        @Config.Comment("Prevents breaking lower parts of sugar cane and cacti as well as unripe crops, unless sneaking")
        public boolean utBetterHarvestToggle = false;

        @Config.Name("Farmland Trample")
        @Config.Comment
            ({
                "Controls when and if farmland can be trampled into dirt",
                "Default: Farmland is trampled normally (vanilla default)",
                "Never: Farmland can never be trampled",
                "Only Player: Prevents farmland from being trampled by a non-EntityPlayer",
                "Not Player: Prevents farmland from being trampled by an EntityPlayer",
                "Feather Falling: Prevents farmland from being trampled if the entity has the Feather Falling enchantment on equipped boots",
            })
        public TrampleOptions utFarmlandTrample = TrampleOptions.DEFAULT;

        @Config.RequiresMcRestart
        @Config.Name("Block Hit Delay")
        @Config.Comment("Sets the delay in ticks between breaking blocks")
        public int utBlockHitDelay = 5;

        @Config.RequiresMcRestart
        @Config.Name("Cactus Size")
        @Config.Comment("Determines how tall cacti can grow")
        public int utCactusSize = 3;

        @Config.RequiresMcRestart
        @Config.Name("Explosion Block Drop Chance")
        @Config.Comment
            ({
                "Determines the numerator of the block drop formula on explosions",
                "Formula: chance ÷ explosionSize"
            })
        public double utExplosionDropChance = 1.0D;

        @Config.RequiresMcRestart
        @Config.Name("Falling Block Lifespan")
        @Config.Comment("Determines how long falling blocks remain in ticks until they are dropped under normal circumstances")
        public int utFallingBlockLifespan = 600;

        @Config.RequiresMcRestart
        @Config.Name("Improve Barrier Particle Display")
        @Config.Comment("Causes Barrier Particles to always be displayed to players in Creative mode")
        public boolean utBarrierParticleDisplay = false;

        @Config.RequiresMcRestart
        @Config.Name("Prevent Observer Activating on Placement")
        @Config.Comment("Controls if the observer activates itself on the first tick when it is placed")
        public boolean utPreventObserverActivatesOnPlacement = false;

        @Config.RequiresMcRestart
        @Config.Name("Render End Portal Bottom")
        @Config.Comment("Controls if the End Portal renders its texture on the bottom face")
        public boolean utRenderEndPortalBottom = true;

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

        @Config.Name("Unsupported Pumpkin Placing")
        @Config.Comment("Allows placing Pumpkins and Jack'O'Lanterns without a supporting block")
        public boolean utUnsupportedPumpkinPlacing = false;

        @Config.RequiresMcRestart
        @Config.Name("End Crystal Placing")
        @Config.Comment("Allows placing End Crystals without requiring Obsidian or Bedrock below")
        public boolean utEndCrystalAnywherePlacing = false;

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

        public static class OverhaulBeaconCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Overhaul Beacon Toggle")
            @Config.Comment("Overhaul beacon construction and range")
            public boolean utOverhaulBeaconToggle = false;

            @Config.Name("[2] Mode")
            @Config.Comment
                ({
                    "Modifier: Use per block modifier for range calculation, replacing vanilla implementation",
                    "Enforced: Enforce usage of only 1 beacon base type per level",
                })
            public EnumBeacon utOverhaulBeaconMode = EnumBeacon.ENFORCED;

            @Config.Name("[3] Global Modifier")
            @Config.Comment("Global range for block, change it by modify beacon range config")
            public double utOverhaulBeaconGlobalModifier = 1D;

            @Config.Name("[4] Level Scaling")
            @Config.Comment
                ({
                    "Scaling beacon range per level (1 -> 4)",
                    "Don't try add more value to this scale as this only use first 4 values"
                })
            public double[] utOverhaulBeaconLevelScale = {1D, 0.8D, 0.6D, 0.4D};

            @Config.Name("[5] Per Block Modifier")
            @Config.Comment
                ({
                    "Block modifier for range calculate, only apply for beacon base block",
                    "Add new one required restart, modify doesn't required so",
                })
            public Map<String, Double> utOverhaulBeaconBlocksModifier = new HashMap<>();

            public OverhaulBeaconCategory()
            {
                utOverhaulBeaconBlocksModifier.put("modid:example", 1D);
            }
        }

        public static class SaplingBehaviorCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Sapling Behavior Toggle")
            @Config.Comment("Allows customization of sapling behavior while utilizing an optimized method")
            public boolean utSaplingBehaviorToggle = true;

            @Config.Name("[2] Minimum Light Level")
            @Config.Comment("Inclusive minimum light level at which saplings grow into trees")
            public int utSaplingLightLevel = 9;

            @Config.Name("[3] Growth Chance")
            @Config.Comment
                ({
                    "Chance per update tick at which saplings grow into trees",
                    "Note: General growth rate is still affected by the random tick speed"
                })
            @Config.RangeDouble(min = 0.0D, max = 1.0D)
            public double utSaplingGrowthChance = 0.125D;
        }
    }

    public static class EntitiesCategory
    {
        @Config.LangKey("cfg.universaltweaks.tweaks.entities.attributes")
        @Config.Name("Attributes")
        public final AttributesCategory ATTRIBUTES = new AttributesCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.entities.betterburning")
        @Config.Name("Better Burning")
        public final BetterBurningCategory BETTER_BURNING = new BetterBurningCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.entities.chickenshedding")
        @Config.Name("Chicken Shedding")
        public final ChickenSheddingCategory CHICKEN_SHEDDING = new ChickenSheddingCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.entities.cobwebslowness")
        @Config.Name("Cobweb Slowness")
        public final CobwebSlownessCategory COBWEB_SLOWNESS = new CobwebSlownessCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.entities.collisiondamage")
        @Config.Name("Collision Damage")
        public final CollisionDamageCategory COLLISION_DAMAGE = new CollisionDamageCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.entities.creeperconfetti")
        @Config.Name("Creeper Confetti")
        public final CreeperConfettiCategory CREEPER_CONFETTI = new CreeperConfettiCategory();

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

        @Config.LangKey("cfg.universaltweaks.tweaks.entities.sleeping")
        @Config.Name("Sleeping")
        public final SleepingCategory SLEEPING = new SleepingCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.entities.spawncaps")
        @Config.Name("Spawn Caps")
        public final SpawnCapsCategory SPAWN_CAPS = new SpawnCapsCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.entities.undeadhorses")
        @Config.Name("Undead Horses")
        public final UndeadHorsesCategory UNDEAD_HORSES = new UndeadHorsesCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.entities.waterfalldamage")
        @Config.Name("Water Fall Damage")
        public final WaterFallDamageCategory WATER_FALL_DAMAGE = new WaterFallDamageCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.entities.voidteleport")
        @Config.Name("Void Teleport")
        public final VoidTeleportCategory VOID_TELEPORT = new VoidTeleportCategory();

        @Config.Name("Adaptive XP Drops")
        @Config.Comment
            ({
                "Scales dropped experience from entities based on their health",
                "Formula: max_health * factor",
                "0 for vanilla default"
            })
        public double utAdaptiveXPFactor = 0;

        @Config.RequiresMcRestart
        @Config.Name("AI Replacement")
        @Config.Comment("Replaces entity AI for improved server performance")
        public boolean utAIReplacementToggle = true;

        @Config.Name("AI Removal")
        @Config.Comment("Removes entity AI for improved server performance")
        public boolean utAIRemovalToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Armed Armor Stands")
        @Config.Comment("Enables arms for armor stands by default")
        public boolean utArmedArmorStandsToggle = false;

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

        @Config.RequiresMcRestart
        @Config.Name("Burning Baby Zombies")
        @Config.Comment("Lets baby zombies burn in daylight as in Minecraft 1.13+")
        public boolean utBurningBabyZombiesToggle = true;

        @Config.Name("Creeper Charged Spawning Chance")
        @Config.Comment("Sets the chance for creepers to spawn charged")
        @Config.RangeDouble(min = 0.0D, max = 1.0D)
        public double utCreeperChargedChance = 0.0D;

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

        @Config.RequiresMcRestart
        @Config.Name("Disable Villager Trade Leveling")
        @Config.Comment("Disables leveling of villager careers, only allowing base level trades")
        public boolean utVillagerTradeLevelingToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Disable Villager Trade Restock")
        @Config.Comment("Disables restocking of villager trades, only allowing one trade per offer")
        public boolean utVillagerTradeRestockToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Disable Wither Targeting AI")
        @Config.Comment("Disables withers targeting animals")
        public boolean utWitherAIToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Weaken Wither Structure Requirements")
        @Config.Comment("Allows creating Withers with non-air blocks in the bottom corners of the structure")
        public boolean utWitherPlacement = false;

        @Config.RequiresMcRestart
        @Config.Name("Weaken Golem Structure Requirements")
        @Config.Comment("Allows creating Iron Golems with non-air blocks in the bottom corners of the structure")
        public boolean utGolemPlacement = false;

        @Config.RequiresMcRestart
        @Config.Name("First Person Burning Overlay")
        @Config.Comment("Sets the offset for the fire overlay in first person when the player is burning")
        public double utFirstPersonBurningOverlay = -0.3D;

        @Config.RequiresMcRestart
        @Config.Name("Husk & Stray Spawning")
        @Config.Comment("Lets husks and strays spawn underground like regular zombies and skeletons")
        public boolean utHuskStraySpawningToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Mob Despawn Improvement")
        @Config.Comment("Mobs carrying picked up items will drop their equipment and despawn properly")
        public boolean utMobDespawnToggle = true;

        @Config.Name("Modern Knockback")
        @Config.Comment("Backports 1.16+ knockback to 1.12: Knockback resistance is now a scale instead of a probability")
        public boolean utModernKnockbackToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Minecart Drops Itself")
        @Config.Comment("Replaces vanilla Minecarts dropping a Minecart and the contained item, and instead drop the combined item")
        public boolean utMinecartDropsType = false;

        @Config.RequiresMcRestart
        @Config.Name("No Portal Spawning")
        @Config.Comment("Prevents zombie pigmen spawning from nether portals")
        public boolean utPortalSpawningToggle = false;

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

        @Config.RequiresMcRestart
        @Config.Name("Riding Exhaustion")
        @Config.Comment("Sets the exhaustion value per cm when riding mounts")
        @Config.RangeDouble(min = 0.0D, max = 1.0D)
        public double utRidingExhaustion = 0.0D;

        @Config.RequiresMcRestart
        @Config.Name("Soulbound Vexes")
        @Config.Comment("Summoned vexes will also die when their summoner is killed")
        public boolean utSoulboundVexesToggle = true;

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

        public static class ChickenSheddingCategory
        {
            @Config.Name("[1] Chicken Shedding")
            @Config.Comment("Enables chickens to have a chance to shed a feather")
            public boolean utChickenSheddingToggle = true;

            @Config.Name("[2] Shed Frequency")
            @Config.Comment("How frequently feathers shed from chickens (lower means more)")
            public int utChickenSheddingFrequency = 28000;

            @Config.Name("[3] Baby Chickens Shed Feathers")
            @Config.Comment("Allows baby chickens to also shed feathers")
            public boolean utChickenSheddingBabyToggle = false;
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

        public static class CobwebSlownessCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Cobweb Slowness Toggle")
            @Config.Comment("Modifies the applied slowness factor when entities are moving in cobwebs")
            public boolean utCobwebSlownessToggle = false;

            @Config.Name("[2] Horizontal Slowness Factor")
            @Config.Comment("The slowness factor that gets multiplied with the horizontal entity speed")
            public double utCobwebSlownessFactorH = 0.25D;

            @Config.Name("[3] Vertical Slowness Factor")
            @Config.Comment("The slowness factor that gets multiplied with the vertical entity speed")
            public double utCobwebSlownessFactorV = 0.05000000074505806D;
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

        public static class CreeperConfettiCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Creeper Confetti Chance")
            @Config.Comment("Sets the chance to replace deadly creeper explosions with delightful confetti")
            @Config.RangeDouble(min = 0.0D, max = 1.0D)
            public double utCreeperConfettiChance = 0.0D;

            @Config.Name("[2] Creeper Confetti Damage")
            @Config.Comment("Sets the damage dealt by confetti explosions")
            public double utCreeperConfettiDamage = 0.0D;
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

        public static class SleepingCategory
        {
            @Config.Name("Disable Sleeping")
            @Config.Comment("Disables skipping night by using a bed while making it still able to set spawn")
            public boolean utDisableSleepingToggle = false;

            @Config.Name("Sleeping Time")
            @Config.RangeInt(min = -1, max = 23999)
            @Config.Comment
                ({
                    "Determines at which time of day sleeping is allowed in ticks (0 - 23999)",
                    "-1 for vanilla default"
                })
            public int utSleepingTime = -1;
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

        public static class VoidTeleportCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[01] Void Teleport Toggle")
            @Config.Comment("Enables Void Teleport, where falling out below a dimension will teleport you to the top of the dimension")
            public boolean utVoidTeleportToggle = false;

            @Config.Name("[02] Prevent Void Damage")
            @Config.Comment
                ({
                    "Prevents taking a tick of void damage before being teleported",
                    "If this is false, entities will take 4 damage every time Void Teleport activates, preventing infinite looping"
                })
            public boolean utPreventVoidDamage = true;

            @Config.Name("[03] Target Y-Level")
            @Config.Comment
                ({
                    "Y-level to teleport the entity",
                    "If the target Y-level is lower than the highest block in that coordinate, will teleport the entity to the highest location instead"
                })
            public int utTargetYLevel = 300;

            @Config.Name("[04] Apply Blindness on Teleport")
            @Config.Comment("Applies the blindness effect for 3 seconds when teleporting")
            public boolean utTeleportBlindness = true;

            @Config.Name("[05] Clamp Falling Speed")
            @Config.Comment("Prevents Y motion from being less than this")
            public double utClampSpeedTo = -1;

            @Config.Name("[06] Fall Distance Height")
            @Config.Comment
                ({
                    "Height to override the fallDistance variable with when landing after having teleported",
                    "When set to less than 0, [07] Fall Damage Taken applies instead"
                })
            public float utFallHeight = -1;

            @Config.Name("[07] Fall Damage Taken")
            @Config.Comment
                ({
                    "Amount of fall damage taken when landing on a block",
                    "Negative numbers deal damage relative to the entity's max health",
                    "Only applies if [06] Fall Distance Height is less than 0"
                })
            public float utFallDamageTaken = -1;

            @Config.Name("[08] Allow Fall Damage Taken to Kill")
            @Config.Comment
                ({
                    "Sets if [07] Fall Damage Taken can kill entities",
                    "Does not apply to fall damage taken due to [06] Fall Distance Height"
                })
            public boolean utAllowSpecificFallDamageToKill = true;

            @Config.Name("[09] Maximum Times to Teleport Consecutively")
            @Config.Comment("Maximum number of times to teleport the entity without the entity landing before no longer teleporting. Used to prevent infinite loops")
            public int utMaxCombo = 100;

            @Config.Name("[10] Apply Void Teleport to Players")
            @Config.Comment("Controls if players are teleported by Void Teleport")
            public boolean utForgivePlayers = true;

            @Config.Name("[11] Entity List")
            @Config.Comment
                ({
                    "List of the resource location names for entities concerning Void Teleport",
                    "Behavior depends on the list mode"
                })
            public String[] utEntityList = new String[] {};

            @Config.Name("[12] Entity List Mode")
            @Config.Comment
                ({
                    "Blacklist Mode: Entities that won't be impacted by Void Teleport, others will",
                    "Whitelist Mode: Entities that will be impacted by Void Teleport, others won't"
                })
            public EnumLists utEntityListMode = EnumLists.WHITELIST;

            @Config.Name("[13] Dimension List")
            @Config.Comment
                ({
                    "List of dimensions concerning Void Teleport",
                    "Behavior depends on the list mode",
                    "Can be dimension name or ID"
                })
            public String[] utDimensionList = new String[] {};

            @Config.Name("[14] Dimension List Mode")
            @Config.Comment
                ({
                    "Blacklist Mode: Dimensions that don't have Void Teleport enabled, others do",
                    "Whitelist Mode: Dimensions that have Void Teleport enabled, others don't"
                })
            public EnumLists utDimensionListMode = EnumLists.BLACKLIST;
        }
    }

    public static class ItemsCategory
    {
        @Config.LangKey("cfg.universaltweaks.tweaks.items.attackcooldown")
        @Config.Name("Attack Cooldown")
        public final AttackCooldownCategory ATTACK_COOLDOWN = new AttackCooldownCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.items.infinity")
        @Config.Name("Mending")
        public final InfinityCategory INFINITY = new InfinityCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.items.itementities")
        @Config.Name("Item Entities")
        public final ItemEntitiesCategory ITEM_ENTITIES = new ItemEntitiesCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.items.mending")
        @Config.Name("Mending")
        public final MendingCategory MENDING = new MendingCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.items.parry")
        @Config.Name("Shield Parry")
        public final ParryCategory PARRY = new ParryCategory();

        @Config.Name("Always Eat")
        @Config.Comment("Allows the consumption of food at any time, regardless of the hunger bar")
        public boolean utAlwaysEatToggle = false;

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
        @Config.Name("Prevent Placing Buckets in Portals")
        @Config.Comment("Prevents placing of liquid source blocks overriding portal blocks")
        public boolean utPreventBucketPlacingInPortal = false;

        @Config.RequiresMcRestart
        @Config.Name("No Leftover Breath Bottles")
        @Config.Comment("Disables dragon's breath from being a container item and leaving off empty bottles when a stack is brewed with")
        public boolean utLeftoverBreathBottleToggle = true;

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

        public static class InfinityCategory
        {
            @Config.Name("[1] Arrowless Infinity")
            @Config.Comment("Bows enchanted with Infinity no longer require arrows")
            public boolean utBowInfinityToggle = true;

            @Config.Name("[2] Infinity Conflict")
            @Config.Comment("Allows the Infinity Enchantment to be combined with Mending")
            public boolean utInfinityEnchantmentConflicts = false;

            @Config.Name("[3] Infinity Affects All Arrows")
            @Config.Comment("Allows the Infinity Enchantment to apply to all arrows (e.g. Tipped Arrows)")
            public boolean utAllArrowsAreInfinite = false;
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

            @Config.Name("[17] Slowed Movement")
            @Config.Comment("Slows how often item entities update their position to improve performance")
            public boolean utIEUpdateToggle = false;
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

    public static class MiscCategory
    {
        @Config.LangKey("cfg.universaltweaks.tweaks.misc.armorcurve")
        @Config.Name("Armor Curve")
        public final ArmorCurveCategory ARMOR_CURVE = new ArmorCurveCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.misc.chat")
        @Config.Name("Chat")
        public final ChatCategory CHAT = new ChatCategory();

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

        @Config.Name("Accurate Potion Duration")
        @Config.Comment("Always displays the actual potion duration instead of `**:**`")
        public boolean utPotionDurationToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Always Return to Main Menu")
        @Config.Comment("Always returns the player to the main menu when quitting the game")
        public boolean utReturnToMainMenu = false;

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
        @Config.Name("Disable Advancements")
        @Config.Comment("Prevents the advancement system from loading entirely")
        public boolean utDisableAdvancementsToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Disable Narrator")
        @Config.Comment("Disables the narrator functionality entirely")
        public boolean utDisableNarratorToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Disable Text Shadows")
        @Config.Comment("Disables all text shadowing, where text has a darker version of itself rendered behind the normal text, changing the appearance and can improve fps on some screens")
        public boolean utDisableTextShadow = false;

        @Config.RequiresMcRestart
        @Config.Name("End Portal Parallax")
        @Config.Comment("Re-implements parallax rendering of the end portal from 1.10 and older")
        public boolean utEndPortalParallaxToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Infinite Music")
        @Config.Comment("Lets background music play continuously without delays")
        public boolean utInfiniteMusicToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("LAN Server Properties")
        @Config.Comment("Enhance the vanilla 'Open to LAN' GUI for listening port customization, removal of enforced authentication and more")
        public boolean utLANServerProperties = true;

        @Config.RequiresMcRestart
        @Config.Name("Better Ping Display")
        @Config.Comment("Displays the ping in milliseconds of players when viewing the server list")
        public boolean utBetterPing = true;

        @Config.RequiresMcRestart
        @Config.Name("Disable Glint Overlay on Potions")
        @Config.Comment("Disables the glint overlay on potions")
        public boolean utDisablePotionGlint = false;

        @Config.RequiresMcRestart
        @Config.Name("Disable Glint Overlay on Enchantment Books")
        @Config.Comment("Disables the glint overlay on enchantment books")
        public boolean utDisableEnchantmentBookGlint = false;

        @Config.RequiresMcRestart
        @Config.Name("Disable Hotbar Scroll Wrapping")
        @Config.Comment("Disables using the scroll wheel to change hotbar slots wrapping")
        public boolean utDisableHotbarScrollWrapping = false;

        @Config.RequiresMcRestart
        @Config.Name("Prevent Keybinds from Overflowing Screen")
        @Config.Comment("Always indent keybind entries from the screen edge, preventing them from overflowing off the left side when particularly long keybind names are present")
        public boolean utPreventKeybindingEntryOverflow = true;

        @Config.RequiresMcRestart
        @Config.Name("Linear XP Amount")
        @Config.Comment
            ({
                "Sets the amount of XP needed for each level, effectively removing the increasing level scaling",
                "0 for vanilla default"
            })
        public int utLinearXP = 0;

        @Config.RequiresMcRestart
        @Config.Name("More Banner Layers")
        @Config.Comment
            ({
                "Sets the amount of applicable pattern layers for banners",
                "6 for vanilla default"
            })
        public int utBannerLayers = 6;

        @Config.RequiresMcRestart
        @Config.Name("No Night Vision Flash")
        @Config.Comment("Disables the flashing effect when the night vision potion effect is about to run out")
        public boolean utNightVisionFlashToggle = false;

        @Config.Name("No Potion Shift")
        @Config.Comment("Disables the inventory shift when potion effects are active")
        public boolean utPotionShiftToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Hide Personal Effect Particles")
        @Config.Comment("Disables potion effect particles emitting from yourself")
        public boolean utPoVEffectParticles = false;

        @Config.RequiresMcRestart
        @Config.Name("Particle Limit")
        @Config.Comment
            ({
                "Limits particles to a set amount. Should not be set too low, as it will cause particles to appear for a single tick before vanishing",
                "Vanilla default is 16384",
                "Less than or equal to 0 is set to the default"
            })
        public int utParticleLimit = -1;

        @Config.RequiresMcRestart
        @Config.Name("No Smelting XP")
        @Config.Comment("Disables the experience reward when smelting items in furnaces")
        public boolean utSmeltingXPToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Improved Entity Tracker Warning")
        @Config.Comment("Provides more information to addPacket removed entity warnings")
        public boolean utImprovedEntityTrackerToggle = true;

        @Config.Name("Offhand Improvement")
        @Config.Comment("Prevents placing offhand blocks when blocks or food are held in the mainhand")
        public boolean utOffhandToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Overlay Message Height")
        @Config.Comment
            ({
                "Sets the Y value of the overlay message (action bar), displayed for playing records etc.",
                "-4 for vanilla default"
            })
        public int utOverlayMessageHeight = -4;

        @Config.RequiresMcRestart
        @Config.Name("Remove 3D Anaglyph Button")
        @Config.Comment("Removes the 3D Anaglyph button from the video settings menu")
        public boolean ut3DAnaglyphButtonToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Remove Realms Button")
        @Config.Comment
            ({
                "Removes the redundant Minecraft Realms button from the main menu and silences notifications",
                "Incompatible with RandomPatches"
            })
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
        @Config.Name("Selected Item Tooltip Height")
        @Config.Comment
            ({
                "Sets the Y value of the selected item tooltip, displayed when held items are changed",
                "59 for vanilla default"
            })
        public int utSelectedItemTooltipHeight = 59;

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

        @Config.RequiresMcRestart
        @Config.Name("Use Separate Dismount Key")
        @Config.Comment("Makes the dismount keybind separate from LSHIFT, allowing it to be rebound independently")
        public boolean utUseSeparateDismountKey = false;

        @Config.RequiresMcRestart
        @Config.Name("Use Separate Narrator Key")
        @Config.Comment("Allows using a custom Narrator key, instead of being stuck with CTRL+B")
        public boolean utUseCustomNarratorKeybind = false;

        @Config.Name("Toggle Cheats Button")
        @Config.Comment("Adds a button to the pause menu to toggle cheats")
        public boolean utToggleCheatsToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("XP Level Cap")
        @Config.Comment
            ({
                "Sets the maximum experience level players can reach",
                "0 to effectively disable gaining of experience",
                "-1 for vanilla default"
            })
        public int utXPLevelCap = -1;

        public static class ArmorCurveCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Armor Curve Toggle")
            @Config.Comment("Adjusts the armor scaling and degradation formulae for mobs and players")
            public boolean utArmorCurveToggle = false;

            @Config.Name("[2] Armor Damage Reduction Formula")
            @Config.Comment
                ({
                    "Configure how much armor does against damage",
                    "Valid values are 'armor', 'damage', and 'toughness'",
                    "Set to 1 to not modify damage at this step"
                })
            public String utArmorCurveArmor = "damage-(damage>(40/(toughness+1)))*((40/(toughness+1)))/2";

            @Config.Name("[3] Armor Toughness Damage Reduction Formula")
            @Config.Comment
                ({
                    "Configure sudden death protection for armor toughness",
                    "Valid values are 'armor', 'damage', and 'toughness'",
                    "Set to 1 to not modify damage at this step"
                })
            public String utArmorCurveArmorToughness = "damage*MAX(10/(10+armor),0.2)";

            @Config.Name("[4] Enchantment Damage Reduction Formula")
            @Config.Comment
                ({
                    "Configure the efficiency of protection enchantments",
                    "Valid values are 'enchant' and 'damage'",
                    "Set to 1 to not modify damage at this step"
                })
            public String utArmorCurveEnchantments = "damage*10/(10+enchant)";

            @Config.Name("[5] Armor Degradation Formula")
            @Config.Comment
                ({
                    "Configure how armor degrades",
                    "Valid values are 'remaining' and 'max'",
                    "Set to 1 to disable"
                })
            public String utArmorCurveDegradation = "remaining/MAX(max,1)";

            @Config.Name("[6] Debug Logging")
            @Config.Comment("Enables debug logging for easier config validation")
            public boolean utArmorCurveLogging = false;
        }

        public static class ChatCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Chat Lines")
            @Config.Comment
                ({
                    "Sets the maximum number of chat lines to display",
                    "100 is the vanilla default",
                    "0 or less functionally disables the chat"
                })
            public int utChatLines = 100;

            @Config.RequiresMcRestart
            @Config.Name("[2] Keep Sent Messages")
            @Config.Comment("Don't clear sent message history on leaving the world")
            public boolean utKeepSentMessageHistory = false;

            @Config.RequiresMcRestart
            @Config.Name("[3] Compact Messages")
            @Config.Comment("Removes duplicate messages and instead put a number behind the message how often it was repeated")
            public boolean utCompactMessagesToggle = false;
        }


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

            @Config.RequiresMcRestart
            @Config.Name("No Lightning Item Destruction")
            @Config.Comment("Prevents lightning bolts from destroying items")
            public boolean utLightningItemDestructionToggle = false;
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

            @Config.Name("[6] Toast Control List")
            @Config.Comment
                ({
                    "List of class names of Toasts to prevent displaying",
                    "Behavior depends on the list mode",
                    "Syntax: full class name"
                })
            public String[] utToastControlClassList = new String[] {};

            @Config.Name("[7] List Mode")
            @Config.Comment
                ({
                    "Blacklist Mode: Toast classes which can't be added to the queue, others can",
                    "Whitelist Mode: Toast classes which can be added to the queue, others can't"
                })
            public EnumLists utToastControlClassListMode = EnumLists.BLACKLIST;

            @Config.Name("[8] Debug Logging")
            @Config.Comment("Enables debug logging to log class names of displayed toasts to the log")
            public boolean utToastNameLogging = false;

            @Config.Name("[9] Clear Toast Keybind")
            @Config.Comment("Enables a keybind (default: CTRL+0) to clear all active toasts")
            public boolean utClearToastKeybind = true;
        }
    }

    public static class PerformanceCategory
    {
        @Config.LangKey("cfg.universaltweaks.tweaks.performance.entityradiuscheck")
        @Config.Name("Entity Radius Check")
        public final EntityRadiusCheckCategory ENTITY_RADIUS_CHECK = new EntityRadiusCheckCategory();

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
        @Config.Name("Disable Mob Spawner Entity")
        @Config.Comment("Improves rendering performance by disabling rendering the entity inside mob spawners")
        public boolean utDisableMobSpawnerRendering = false;

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
        @Config.Name("Improve Language Switching Speed")
        @Config.Comment("Improves the speed of switching languages in the Language GUI")
        public boolean utImproveLanguageSwitchingSpeed = true;

        @Config.RequiresMcRestart
        @Config.Name("Improve Server Connection Speed")
        @Config.Comment
            ({
                "Improves the speed of connecting to servers by setting the InetAddress host name to the IP in situations",
                "where it can be represented as the IP address, preventing getHostFromNameService from being to be run"
            })
        public boolean utImproveServerConnectionSpeed = true;

        @Config.RequiresMcRestart
        @Config.Name("Mute Advancement Errors")
        @Config.Comment("Silences advancement errors")
        public boolean utAdvancementCheckToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Mute Ore Dictionary Errors")
        @Config.Comment("Silences ore dictionary errors")
        public boolean utOreDictionaryCheckToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Mute Texture Map Errors")
        @Config.Comment("Silences texture map errors")
        public boolean utTextureMapCheckToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("No Pathfinding Chunk Loading")
        @Config.Comment("Disables mob pathfinding from loading new/unloaded chunks when building chunk caches")
        public boolean utPathfindingChunkCacheFixToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("No Redstone Lighting")
        @Config.Comment("Disables lighting of active redstone, repeaters, and comparators to improve performance")
        public boolean utRedstoneLightingToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Uncap FPS")
        @Config.Comment("Removes the hardcoded 30 FPS limit in screens like the main menu")
        public boolean utUncapFPSToggle = true;

        public static class EntityRadiusCheckCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Entity Radius Check Toggle")
            @Config.Comment
                ({
                    "Toggles all tweaks in this category",
                    "IMPORTANT: These tweaks are only effective if you have mod(s) that increase World.MAX_ENTITY_RADIUS!",
                    "(Lycanites Mobs, Advanced Rocketry, Immersive Railroading, etc.)"
                })
            public boolean utEntityRadiusCheckCategoryToggle = true;

            @Config.Name("[2] Reduce Search Size Toggle")
            @Config.Comment("Reduces the search size of various AABB functions for specified entity types")
            public boolean utReduceSearchSizeToggle = true;

            @Config.Name("[3] Reduce Search Size Targets")
            @Config.Comment
                ({
                    "The entity types to reduce the search size for",
                    "Syntax - modid:name"
                })
            public String[] utReduceSearchSizeTargets = new String[]
                {
                    "minecraft:item",
                    "minecraft:player"
                };

            @Config.Name("[4] Less Collisions Toggle")
            @Config.Comment("Reduces size of collision checks for most vanilla and specified entity types")
            public boolean utLessCollisionsToggle = true;

            @Config.Name("[5] Less Collisions Extra Targets")
            @Config.Comment
                ({
                    "The extra entity types to reduce the size of collision checks for",
                    "Syntax - modid:name;radius",
                    "Vanilla ids aren't allowed because they are already included",
                    "Most types should be specified with the vanilla default radius: 2.0"
                })
            public String[] utLessCollisionsExtraTargets = new String[] {};
        }
    }

    public static class WorldCategory
    {
        @Config.LangKey("cfg.universaltweaks.tweaks.world.chunkgenlimit")
        @Config.Name("Chunk Gen Limit")
        public final ChunkGenLimitCategory CHUNK_GEN_LIMIT = new ChunkGenLimitCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.world.dimensionunload")
        @Config.Name("Dimension Unload")
        public final DimensionUnloadCategory DIMENSION_UNLOAD = new DimensionUnloadCategory();

        @Config.LangKey("cfg.universaltweaks.tweaks.world.voidfog")
        @Config.Name("Void Fog")
        public final VoidFogCategory VOID_FOG = new VoidFogCategory();

        @Config.RequiresMcRestart
        @Config.Name("Sea Level")
        @Config.Comment
            ({
                "Sets the default height of the overworld's sea level",
                "Supported world types: Default, Biomes O' Plenty",
                "Vanilla default is 63"
            })
        public int utSeaLevel = 63;

        @Config.RequiresMcRestart
        @Config.Name("Stronghold Enforcement")
        @Config.Comment("Enforces stronghold generation to generate all blocks, regardless of air")
        public boolean utStrongholdToggle = true;

        @Config.Name("Tidy Chunk")
        @Config.Comment("Tidies newly generated chunks by removing scattered item entities")
        public boolean utTidyChunkToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Village Distance")
        @Config.Comment
            ({
                "Sets the village generation distance in chunks",
                "Vanilla default is 32"
            })
        public int utVillageDistance = 32;

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

        public static class VoidFogCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Void Fog Toggle")
            @Config.Comment("Re-implements pre-1.8 void fog and void particles")
            public boolean utVoidFogToggle = false;

            @Config.Name("[2] Dimension List")
            @Config.Comment
                ({
                    "List of dimensions concerning void fog and particles",
                    "Behavior depends on the list mode",
                    "Can be dimension name or ID"
                })
            public String[] utVoidFogDimensionList = new String[] {"overworld"};

            @Config.Name("[3] Dimension List Mode")
            @Config.Comment
                ({
                    "Blacklist Mode: Dimensions that don't have void fog and particles enabled, others do",
                    "Whitelist Mode: Dimensions that have void fog and particles enabled, others don't"
                })
            public EnumLists utVoidFogDimensionListMode = EnumLists.WHITELIST;

            @Config.Name("[4] Fog In Creative/Spectator")
            @Config.Comment("Renders void fog in creative and spectator mode")
            public boolean utVoidFogCreativeSpectator = false;

            @Config.Name("[5] Fog In Superflat")
            @Config.Comment("Renders void fog in the superflat world type")
            public boolean utVoidFogSuperflat = false;

            @Config.Name("[6] Fog On Night Vision")
            @Config.Comment("Renders void fog when the player has night vision")
            public boolean utVoidFogNightVision = false;

            @Config.Name("[7] Particles In Creative/Spectator")
            @Config.Comment("Renders void particles in creative and spectator mode")
            public boolean utVoidParticlesCreativeSpectator = true;

            @Config.Name("[8] Particles In Superflat")
            @Config.Comment("Renders void particles in the superflat world type")
            public boolean utVoidParticlesSuperflat = false;

            @Config.Name("[9] Particle Spawn Y Level")
            @Config.Comment("Determines the maximum Y level of the player at which void particles are spawned")
            public int utVoidParticleSpawnYLevel = 8;

            @Config.Name("[10] Particle Spawn Iterations")
            @Config.Comment("Determines the amount of iterations for checking void particle spawns per animate tick")
            public int utVoidParticleSpawnIterations = 1000;
        }
    }

    static
    {
        ConfigAnytime.register(UTConfigTweaks.class);
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
                if (BLOCKS.BREAKABLE_BEDROCK.utBreakableBedrockToggle) UTBreakableBedrock.initToolList();
                if (MISC.ARMOR_CURVE.utArmorCurveToggle) UTArmorCurve.initExpressions();
                if (MISC.SWING_THROUGH_GRASS.utSwingThroughGrassToggle) UTSwingThroughGrassLists.initLists();
                if (MISC.INCURABLE_POTIONS.utIncurablePotionsToggle) UTIncurablePotions.initPotionList();
                if (PERFORMANCE.ENTITY_RADIUS_CHECK.utEntityRadiusCheckCategoryToggle)
                {
                    if (PERFORMANCE.ENTITY_RADIUS_CHECK.utReduceSearchSizeToggle) UTEntityRadiusCheck.initSearchTargets();
                    if (PERFORMANCE.ENTITY_RADIUS_CHECK.utLessCollisionsToggle) UTEntityRadiusCheck.initCollisionTargets();
                }
                if (ITEMS.utCustomRarities.length > 0) UTCustomRarity.initItemRarityMap();
                if (ITEMS.utCustomUseDurations.length > 0) UTCustomUseDuration.initItemUseMaps();
                if (ITEMS.PARRY.utParryToggle) UTParry.initProjectileList();
                if (UTLoadingPlugin.isClient)
                {
                    if (MISC.LOAD_SOUNDS.utLoadSoundMode != MiscCategory.LoadSoundsCategory.EnumSoundModes.NOTHING) UTLoadSound.initLists();
                    UTAutoSaveOFCompat.updateOFConfig();
                }
            }
        }
    }
}
