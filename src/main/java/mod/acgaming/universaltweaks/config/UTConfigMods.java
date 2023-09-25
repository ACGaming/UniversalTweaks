package mod.acgaming.universaltweaks.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.cleanroommc.configanytime.ConfigAnytime;
import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.core.UTLoadingPlugin;
import mod.acgaming.universaltweaks.mods.botania.UTBotaniaFancySkybox;

@Config(modid = UniversalTweaks.MODID, name = UniversalTweaks.NAME + " - Mod Integration")
public class UTConfigMods
{
    @Config.LangKey("cfg.universaltweaks.modintegration.abyssalcraft")
    @Config.Name("AbyssalCraft")
    public static final AbyssalCraftCategory ABYSSALCRAFT = new AbyssalCraftCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.actuallyadditions")
    @Config.Name("Actually Additions")
    public static final ActuallyAdditionsCategory ACTUALLY_ADDITIONS = new ActuallyAdditionsCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.arcanearchives")
    @Config.Name("Arcane Archives")
    public static final ArcaneArchivesCategory ARCANE_ARCHIVES = new ArcaneArchivesCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.aoa")
    @Config.Name("Advent of Ascension")
    public static final AOACategory AOA = new AOACategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.bop")
    @Config.Name("Biomes O' Plenty")
    public static final BiomesOPlentyCategory BIOMES_O_PLENTY = new BiomesOPlentyCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.bm")
    @Config.Name("Blood Magic")
    public static final BloodMagicCategory BLOOD_MAGIC = new BloodMagicCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.botania")
    @Config.Name("Botania")
    public static final BotaniaCategory BOTANIA = new BotaniaCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.chisel")
    @Config.Name("Chisel")
    public static final ChiselCategory CHISEL = new ChiselCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.cqrepoured")
    @Config.Name("Chocolate Quest Repoured")
    public static final ChocolateQuestCategory CHOCOLATE_QUEST = new ChocolateQuestCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.cofhcore")
    @Config.Name("CoFH Core")
    public static final CoFHCoreCategory COFH_CORE = new CoFHCoreCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.elementarystaffs")
    @Config.Name("Elementary Staffs")
    public static final ElementaryStaffsCategory ELEMENTARY_STAFFS = new ElementaryStaffsCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.elenaidodge2")
    @Config.Name("Elenai Dodge 2")
    public static final ElenaiDodge2Category ELENAI_DODGE_2 = new ElenaiDodge2Category();

    @Config.LangKey("cfg.universaltweaks.modintegration.esm")
    @Config.Name("Epic Siege Mod")
    public static final EpicSiegeModCategory EPIC_SIEGE_MOD = new EpicSiegeModCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.erebus")
    @Config.Name("The Erebus")
    public static final ErebusCategory EREBUS = new ErebusCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.extrautilities")
    @Config.Name("Extra Utilities 2")
    public static final ExtraUtilitiesCategory EXTRA_UTILITIES = new ExtraUtilitiesCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.forestry")
    @Config.Name("Forestry")
    public static final ForestryCategory FORESTRY = new ForestryCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.industrialcraft")
    @Config.Name("IndustrialCraft 2")
    public static final IndustrialCraftCategory INDUSTRIALCRAFT = new IndustrialCraftCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.industrialforegoing")
    @Config.Name("Industrial Foregoing")
    public static final IndustrialForegoingCategory INDUSTRIAL_FOREGOING = new IndustrialForegoingCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.infernalmobs")
    @Config.Name("Infernal Mobs")
    public static final InfernalMobsCategory INFERNAL_MOBS = new InfernalMobsCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.ironbackpacks")
    @Config.Name("Iron Backpacks")
    public static final IronBackpacksCategory IRON_BACKPACKS = new IronBackpacksCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.itemstages")
    @Config.Name("Item Stages")
    public static final ItemStagesCategory ITEM_STAGES = new ItemStagesCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.mekanism")
    @Config.Name("Mekanism")
    public static final MekanismCategory MEKANISM = new MekanismCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.mobstages")
    @Config.Name("Mob Stages")
    public static final MobStagesCategory MOB_STAGES = new MobStagesCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.netherchest")
    @Config.Name("Nether Chest")
    public static final NetherChestCategory NETHER_CHEST = new NetherChestCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.netherrocks")
    @Config.Name("Netherrocks")
    public static final NetherrocksCategory NETHERROCKS = new NetherrocksCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.nuclearcraft")
    @Config.Name("NuclearCraft")
    public static final NuclearCraftCategory NUCLEARCRAFT = new NuclearCraftCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.projectred")
    @Config.Name("ProjectRed")
    public static final ProjectRedCategory PROJECTRED = new ProjectRedCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.quark")
    @Config.Name("Quark")
    public static final QuarkCategory QUARK = new QuarkCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.roost")
    @Config.Name("Roost")
    public static final RoostCategory ROOST = new RoostCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.simpledifficulty")
    @Config.Name("Simple Difficulty")
    public static final SimpleDifficultyCategory SIMPLE_DIFFICULTY = new SimpleDifficultyCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.simplyjetpacks")
    @Config.Name("Simply Jetpacks")
    public static final SimplyJetpacksCategory SIMPLY_JETPACKS = new SimplyJetpacksCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.spiceoflife")
    @Config.Name("Spice Of Life")
    public static final SpiceOfLifeCategory SPICE_OF_LIFE = new SpiceOfLifeCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.sd")
    @Config.Name("Storage Drawers")
    public static final StorageDrawersCategory STORAGE_DRAWERS = new StorageDrawersCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.tardis")
    @Config.Name("Tardis Mod")
    public static final TardisCategory TARDIS = new TardisCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.tr")
    @Config.Name("Tech Reborn")
    public static final TechRebornCategory TECH_REBORN = new TechRebornCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.tc")
    @Config.Name("Thaumcraft")
    public static final ThaumcraftCategory THAUMCRAFT = new ThaumcraftCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.tc.entities")
    @Config.Name("Thaumcraft: Entities")
    public static final ThaumcraftEntitiesCategory THAUMCRAFT_ENTITIES = new ThaumcraftEntitiesCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.tc.foci")
    @Config.Name("Thaumcraft: Foci")
    public static final ThaumcraftFociCategory THAUMCRAFT_FOCI = new ThaumcraftFociCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.thaumicwonders")
    @Config.Name("Thaumic Wonders")
    public static final ThaumicWondersCategory THAUMIC_WONDERS = new ThaumicWondersCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.thefarlanders")
    @Config.Name("The Farlanders")
    public static final TheFarlandersCategory THE_FARLANDERS = new TheFarlandersCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.te")
    @Config.Name("Thermal Expansion")
    public static final ThermalExpansionCategory THERMAL_EXPANSION = new ThermalExpansionCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.tcon")
    @Config.Name("Tinkers' Construct")
    public static final TinkersConstructCategory TINKERS_CONSTRUCT = new TinkersConstructCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.tinyprogressions")
    @Config.Name("Tiny Progressions")
    public static final TinyProgressionsCategory TINY_PROGRESSIONS = new TinyProgressionsCategory();

    public static class AbyssalCraftCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Optimized Item Transport")
        @Config.Comment("Makes an optimization to reduce tick overhead of AbyssalCraft's item transport system")
        public boolean utOptimizedItemTransferToggle = true;
    }

    public static class ActuallyAdditionsCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
    }

    public static class ArcaneArchivesCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
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

    public static class BloodMagicCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Optimized Hellfire Forge")
        @Config.Comment("Optimizes the Hellfire/Soul Forge to reduce tick time")
        public boolean utBMOptimizeSoulForgeToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("World Unload Memory Leak Fix")
        @Config.Comment("Fixes memory leak related to unloading worlds/switching dimensions")
        public boolean utBMWorldUnloadToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
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

        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
    }

    public static class ChiselCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
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

        @Config.RequiresMcRestart
        @Config.Name("Sprinting Feather Consumption")
        @Config.Comment("Sets the amount of consumed half-feathers per interval when the player is sprinting")
        public int utED2SprintingFeatherConsumption = 0;

        @Config.Name("Sprinting Feather Interval")
        @Config.Comment("Sets the rate feathers are consumed in ticks when the player is sprinting")
        public int utED2SprintingFeatherInterval = 20;

        @Config.Name("Sprinting Feather Requirement")
        @Config.Comment("Sets the amount of half-feathers required to start sprinting")
        public int utED2SprintingFeatherRequirement = 6;
    }

    public static class EpicSiegeModCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Disable Digger AI Debug")
        @Config.Comment("Disables leftover debug logging inside the digger AI of the beta builds")
        public boolean utESMDiggerDebugToggle = true;
    }

    public static class ErebusCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Preserved Blocks Fix")
        @Config.Comment("Prevents HWYLA/TOP crashes with preserved blocks")
        public boolean utEBPreservedBlocksToggle = true;
    }

    public static class ExtraUtilitiesCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
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

        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
    }

    public static class IndustrialCraftCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
    }

    public static class IndustrialForegoingCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
    }

    public static class InfernalMobsCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Sticky Recall Compatibility")
        @Config.Comment("Enables compatibility between Infernal Mobs' Sticky effect and Capsule's Recall enchantment")
        public boolean utIMStickyRecallToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Sticky Pedestal Compatibility")
        @Config.Comment("Enables compatibility between Infernal Mobs' Sticky effect and Reliquary's Pedestal")
        public boolean utIMStickyPedestalToggle = true;
    }

    public static class IronBackpacksCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
    }

    public static class ItemStagesCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Ingredient Matching")
        @Config.Comment("Changes item matching code to CraftTweaker's ingredient matching system, fixes item NBT issues")
        public boolean utIngredientMatching = true;
    }

    public static class MekanismCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
    }

    public static class MobStagesCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Spawning Rules Fixes")
        @Config.Comment("Fixes mob replacement ignoring entity spawning rules")
        public boolean utSpawningRules = true;
    }

    public static class NetherChestCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
    }

    public static class NetherrocksCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Right Click Harvesting Fix")
        @Config.Comment("Prevents crashing with mods implementing right click crop harvesting")
        public boolean utNRRightClickHarvestingToggle = true;
    }

    public static class NuclearCraftCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Radiation Environment Map")
        @Config.Comment
            ({
                "Changes the data table of the radiation environment handler to improve performance",
                "CONCURRENT_HASHMAP:        NuclearCraft default",
                "CONCURRENT_LINKED_HASHMAP: Keeps order of radiation environment info to improve iteration - Better performance",
                "CONCURRENT_LINKED_QUEUE:   Uses a queue to avoid iteration - Best performance"
            })
        public EnumMaps utNCRadiationEnvironmentMap = EnumMaps.CONCURRENT_LINKED_QUEUE;

        public enum EnumMaps
        {
            CONCURRENT_HASHMAP,
            CONCURRENT_LINKED_HASHMAP,
            CONCURRENT_LINKED_QUEUE
        }
    }

    public static class ProjectRedCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
    }

    public static class QuarkCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
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

    public static class SimplyJetpacksCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Memory Leak Fix")
        @Config.Comment("Fixes a client-side memory leak associated with EntityPlayer")
        public boolean utMemoryLeakFixToggle = true;
    }

    public static class SpiceOfLifeCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
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

    public static class TardisCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Memory Leak Fix")
        @Config.Comment("Fixes a client-side memory leak associated with EntityPlayer")
        public boolean utMemoryLeakFixToggle = true;
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

        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
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

    public static class ThaumicWondersCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
    }

    public static class TheFarlandersCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
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

        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
    }

    public static class TinkersConstructCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Gaseous Fluids")
        @Config.Comment("Excludes gaseous fluids from being transferable via faucets")
        public boolean utTConGaseousFluidsToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Material Blacklist")
        @Config.Comment
            ({
                "Hides tool/bow materials in the 'Materials and You' book",
                "Syntax: material",
                "Enabling debug logging prints all materials on game launch"
            })
        public String[] utTConMaterialBlacklist = new String[] {};

        @Config.RequiresMcRestart
        @Config.Name("Offhand Shuriken")
        @Config.Comment("Suppresses special abilities of long swords and rapiers when shurikens are wielded in the offhand")
        public boolean utTConShurikenToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Ore Dictionary Cache")
        @Config.Comment("Caches all ore dictionary smelting recipes to speed up game loading")
        public boolean utTConOreDictCacheToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Projectile Despawning")
        @Config.Comment("Despawns unbreakable projectiles faster to improve framerates")
        public boolean utTConProjectileToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
    }

    public static class TinyProgressionsCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
    }

    static
    {
        ConfigAnytime.register(UTConfigMods.class);
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
                if (UTLoadingPlugin.isClient)
                {
                    if (Loader.isModLoaded("botania")) UTBotaniaFancySkybox.initDimList();
                }
            }
        }
    }
}