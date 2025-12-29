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

    @Config.LangKey("cfg.universaltweaks.modintegration.aether_legacy")
    @Config.Name("Aether Legacy")
    public static final AetherLegacyCategory AETHER_LEGACY = new AetherLegacyCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.agricraft")
    @Config.Name("Agricraft")
    public static final AgricraftCategory AGRICRAFT = new AgricraftCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.arcanearchives")
    @Config.Name("Arcane Archives")
    public static final ArcaneArchivesCategory ARCANE_ARCHIVES = new ArcaneArchivesCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.astralsorcery")
    @Config.Name("Astral Sorcery")
    public static final AstralSorceryCategory ASTRAL_SORCERY = new AstralSorceryCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.aoa")
    @Config.Name("Advent of Ascension")
    public static final AOACategory AOA = new AOACategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.backpack")
    @Config.Name("Backpacks")
    public static final BackpacksCategory BACKPACKS = new BackpacksCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.bewitchment")
    @Config.Name("Bewitchment")
    public static final BewitchmentCategory BEWITCHMENT = new BewitchmentCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.bibliocraft")
    @Config.Name("BiblioCraft")
    public static final BiblioCraftCategory BIBLIOCRAFT = new BiblioCraftCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.bop")
    @Config.Name("Biomes O' Plenty")
    public static final BiomesOPlentyCategory BIOMES_O_PLENTY = new BiomesOPlentyCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.bm")
    @Config.Name("Blood Magic")
    public static final BloodMagicCategory BLOOD_MAGIC = new BloodMagicCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.botania")
    @Config.Name("Botania")
    public static final BotaniaCategory BOTANIA = new BotaniaCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.bwm")
    @Config.Name("Better with Mods")
    public static final BWMCategory BWM = new BWMCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.cbmultipart")
    @Config.Name("CB Multipart/Forge Multipart CBE")
    public static final CBMultipartCategory CB_MULTIPART = new CBMultipartCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.ccl")
    @Config.Name("CodeChicken Lib")
    public static final CodeChickenLibCategory CCL = new CodeChickenLibCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.chisel")
    @Config.Name("Chisel")
    public static final ChiselCategory CHISEL = new ChiselCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.cqrepoured")
    @Config.Name("Chocolate Quest Repoured")
    public static final ChocolateQuestCategory CHOCOLATE_QUEST = new ChocolateQuestCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.cofhcore")
    @Config.Name("CoFH Core")
    public static final CoFHCoreCategory COFH_CORE = new CoFHCoreCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.cofhworld")
    @Config.Name("CoFH World")
    public static final CoFHWorldCategory COFH_WORLD = new CoFHWorldCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.collective")
    @Config.Name("Collective")
    public static final CollectiveCategory COLLECTIVE = new CollectiveCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.compactmachines")
    @Config.Name("Compact Machines")
    public static final CompactMachinesCoreCategory COMPACT_MACHINES = new CompactMachinesCoreCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.cookingforblockheads")
    @Config.Name("Cooking For Blockheads")
    public static final CookingForBlockheadsCategory COOKING_FOR_BLOCKHEADS = new CookingForBlockheadsCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.corpse")
    @Config.Name("Corpse")
    public static final CorpseCategory CORPSE = new CorpseCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.cyclic")
    @Config.Name("Cyclic")
    public static final CyclicCategory CYCLIC = new CyclicCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.dankstorage")
    @Config.Name("Dank Storage")
    public static final DankStorageCategory DANK_STORAGE = new DankStorageCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.divineprg")
    @Config.Name("Divine RPG")
    public static final DivineRPGCategory DIVINE_RPG = new DivineRPGCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.effortlessbuilding")
    @Config.Name("Effortless Building")
    public static final EffortlessBuildingCategory EFFORTLESS_BUILDING = new EffortlessBuildingCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.elementarystaffs")
    @Config.Name("Elementary Staffs")
    public static final ElementaryStaffsCategory ELEMENTARY_STAFFS = new ElementaryStaffsCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.electroblobswizardry")
    @Config.Name("Electroblob's Wizardry")
    public static final ElectroblobsWizardryCategory ELECTROBLOBS_WIZARDRY = new ElectroblobsWizardryCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.elenaidodge2")
    @Config.Name("Elenai Dodge 2")
    public static final ElenaiDodge2Category ELENAI_DODGE_2 = new ElenaiDodge2Category();

    @Config.LangKey("cfg.universaltweaks.modintegration.enderio")
    @Config.Name("Ender IO")
    public static final EnderIOCategory ENDER_IO = new EnderIOCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.enderstorage")
    @Config.Name("Ender Storage")
    public static final EnderStorageCategory ENDER_STORAGE = new EnderStorageCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.esm")
    @Config.Name("Epic Siege Mod")
    public static final EpicSiegeModCategory EPIC_SIEGE_MOD = new EpicSiegeModCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.erebus")
    @Config.Name("The Erebus")
    public static final ErebusCategory EREBUS = new ErebusCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.evilcraft")
    @Config.Name("EvilCraft")
    public static final EvilCraftCategory EVIL_CRAFT = new EvilCraftCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.extrautilities")
    @Config.Name("Extra Utilities 2")
    public static final ExtraUtilitiesCategory EXTRA_UTILITIES = new ExtraUtilitiesCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.forestry")
    @Config.Name("Forestry")
    public static final ForestryCategory FORESTRY = new ForestryCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.fpsreducer")
    @Config.Name("FPS Reducer")
    public static final FpsReducerCategory FPS_REDUCER = new FpsReducerCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.gaia_dimension")
    @Config.Name("Gaia Dimension")
    public static final GaiaDimensionCategory GAIA_DIMENSION = new GaiaDimensionCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.immersiveengineering")
    @Config.Name("Immersive Engineering")
    public static final ImmersiveEngineeringCategory IMMERSIVE_ENGINEERING = new ImmersiveEngineeringCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.incontrol")
    @Config.Name("In Control!")
    public static final InControlCategory INCONTROL = new InControlCategory();

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

    @Config.LangKey("cfg.universaltweaks.modintegration.ironchests")
    @Config.Name("Iron Chests")
    public static final IronChestsCategory IRON_CHESTS = new IronChestsCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.itemfavorites")
    @Config.Name("Item Favorites")
    public static final ItemFavoritesCategory ITEM_FAVORITES = new ItemFavoritesCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.itemstages")
    @Config.Name("Item Stages")
    public static final ItemStagesCategory ITEM_STAGES = new ItemStagesCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.jurassicreborn")
    @Config.Name("Jurassic Reborn")
    public static final JurassicRebornCategory JURASSIC_REBORN = new JurassicRebornCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.mekanism")
    @Config.Name("Mekanism")
    public static final MekanismCategory MEKANISM = new MekanismCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.moartinkers")
    @Config.Name("Moar Tinkers")
    public static final MoarTinkersCategory MOAR_TINKERS = new MoarTinkersCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.mobstages")
    @Config.Name("Mob Stages")
    public static final MobStagesCategory MOB_STAGES = new MobStagesCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.modularmagic")
    @Config.Name("Modular Magic")
    public static final ModularMagicCategory MODULAR_MAGIC = new ModularMagicCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.modularrouters")
    @Config.Name("Modular Routers")
    public static final ModularRoutersCategory MODULAR_ROUTERS = new ModularRoutersCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.mrtjpcore")
    @Config.Name("MrTJPCore")
    public static final MrTJPCoreCategory MRTJPCORE = new MrTJPCoreCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.netherchest")
    @Config.Name("Nether Chest")
    public static final NetherChestCategory NETHER_CHEST = new NetherChestCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.netherrocks")
    @Config.Name("Netherrocks")
    public static final NetherrocksCategory NETHERROCKS = new NetherrocksCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.nuclearcraft")
    @Config.Name("NuclearCraft")
    public static final NuclearCraftCategory NUCLEARCRAFT = new NuclearCraftCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.openblocks")
    @Config.Name("OpenBlocks")
    public static final OpenBlocksCategory OPEN_BLOCKS = new OpenBlocksCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.projectred")
    @Config.Name("ProjectRed")
    public static final ProjectRedCategory PROJECTRED = new ProjectRedCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.proper_pumpkin")
    @Config.Name("Proper Pumpkins Mod")
    public static final ProperPumpkinCategory PROPER_PUMPKIN = new ProperPumpkinCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.quark")
    @Config.Name("Quark")
    public static final QuarkCategory QUARK = new QuarkCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.railcraft")
    @Config.Name("Railcraft")
    public static final RailcraftCategory RAILCRAFT = new RailcraftCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.randomthings")
    @Config.Name("Random Things")
    public static final RandomThingsCategory RANDOM_THINGS = new RandomThingsCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.requiousfrakto")
    @Config.Name("Requious Frakto")
    public static final RequiousFraktoCategory REQUIOUS_FRAKTO = new RequiousFraktoCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.rftoolsdimensions")
    @Config.Name("RFTools Dimensions")
    public static final RFToolsDimensionsCategory RFTOOLS_DIMENSIONS = new RFToolsDimensionsCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.roost")
    @Config.Name("Roost")
    public static final RoostCategory ROOST = new RoostCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.roots")
    @Config.Name("Roots")
    public static final RootsCategory ROOTS = new RootsCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.simpledifficulty")
    @Config.Name("Simple Difficulty")
    public static final SimpleDifficultyCategory SIMPLE_DIFFICULTY = new SimpleDifficultyCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.simplyjetpacks")
    @Config.Name("Simply Jetpacks")
    public static final SimplyJetpacksCategory SIMPLY_JETPACKS = new SimplyJetpacksCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.spiceoflife")
    @Config.Name("Spice Of Life")
    public static final SpiceOfLifeCategory SPICE_OF_LIFE = new SpiceOfLifeCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.steamworld")
    @Config.Name("SteamWorld")
    public static final SteamWorldCategory STEAMWORLD = new SteamWorldCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.sd")
    @Config.Name("Storage Drawers")
    public static final StorageDrawersCategory STORAGE_DRAWERS = new StorageDrawersCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.tardis")
    @Config.Name("Tardis Mod")
    public static final TardisCategory TARDIS = new TardisCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.tr")
    @Config.Name("Tech Reborn")
    public static final TechRebornCategory TECH_REBORN = new TechRebornCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.test_dummy")
    @Config.Name("MmmMmmMmmMmm")
    public static final TestDummyCategory TEST_DUMMY = new TestDummyCategory();

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

    @Config.LangKey("cfg.universaltweaks.modintegration.tombmanygraves")
    @Config.Name("TombManyGraves2")
    public static final TombManyGravesCategory TOMBMANYGRAVES = new TombManyGravesCategory();

    @Config.LangKey("cfg.universaltweaks.modintegration.woot")
    @Config.Name("Woot")
    public static final WootCategory WOOT = new WootCategory();

    public static class AbyssalCraftCategory
    {
        @Config.Name("Disable Plague Potion Clouds")
        @Config.Comment("Disables AbyssalCraft's Plague-type mobs spawning lingering potion effects on death")
        public boolean utDisablePlaguePotionClouds = false;
    }

    public static class ActuallyAdditionsCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;

        @Config.Name("Laser Upgrade Voiding")
        @Config.Comment("Fixes Laser Upgrades voiding instead of applying if there is only one item in the stack")
        public boolean utLaserUpgradeVoid = true;

        @Config.Name("Item Laser Particles Graphics")
        @Config.Comment
            ({
                "Sets what level of Particles Setting is required to disable the Item Particles generated by Item Lasers transferring items",
                "The config setting here is complex due to how Vanilla Minecraft handles the Particles setting",
                "-1 or lower will not register the mixin",
                "0 disables the creation of these particles entirely",
                "1 disables the creation of these particles when the Particle setting is on Minimal only",
                "2 disables the creation of these particles when the Particle setting is on Decreased or Minimal",
                "3 or higher will never disable these particles"
            })
        public int utItemLaserParticlesGraphics = -1;
    }

    public static class AetherLegacyCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Capture Accessory Drops")
        @Config.Comment("When dropping equipped Accessories, add the drops to the event instead of dropping them directly, allowing compatibility with various grave mods")
        public boolean utCaptureAccessoryDrops = true;
    }

    public static class AgricraftCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Ender IO Integration Fix")
        @Config.Comment("Fixes crash when Ender IO's Farming Station attempts to harvest Agricraft crops")
        public boolean utEnderIOPluginFixToggle = true;
    }

    public static class ArcaneArchivesCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
    }

    public static class AstralSorceryCategory
    {
        @Config.Name("Downgrade Missing Player Log Level")
        @Config.Comment("Downgrades the message when completing a recipe without an initializing player from a warning to a debug")
        public boolean utMissingPlayerLogLevelDowngrade = true;

        @Config.Name("Sooty Marble Rendering Fix")
        @Config.Comment("Fixes Sooty Marble Pillar blocking the proper rendering of adjacent fluids due to inverted logic")
        public boolean utSootyMarbleRendering = true;

        @Config.Name("Clear Particle Effects")
        @Config.Comment("Fixes a bug where particle effects would continue to render after changing dimensions")
        public boolean utClearEffectsOnDimensionChange = true;

        @Config.Name("Fix Division By Zero Crystal Tool")
        @Config.Comment("Fixes a bug where merging Crystal Tool Properties could result in a division by zero")
        public boolean utEmptyPropertiesZero = true;

        @Config.Name("Fix Neromantic Prime Fluid Overflow")
        @Config.Comment("Fixes a bug where the fluid veins for Neromantic Prime could overflow, resulting in empty veins (just water)")
        public boolean utNeromanticPrimeFluidOverflow = true;

        @Config.Name("Allow Unlimited Fluid Veins")
        @Config.Comment("Make it so max size fluid veins never run out")
        public boolean utNeromanticPrimeUnlimitedVeins = true;

        @Config.Name("Neromantic Prime Extraction Quantity")
        @Config.Comment("Amount of fluid extracted per Neromantic Prime operation (in mB per 20-30 ticks)")
        public int utNeromanticPrimeExtractionQuantity = 300;

        @Config.Name("Neromantic Prime Fluid Vein Dimensions")
        @Config.Comment({
            "Extends the fluid vein configuration to allow specifying in which dimensions a fluid can generate",
            "This should be done as a list of comma-separated dimension IDs at the 5th field. Leave blank to allow all dimensions.",
            "Example: 'lava;100000;200000;50;0,-1,7' allows lava veins to spawn in the Overworld (0) and Nether (-1)",
            "Example: 'essence;50000;150000;30' allows essence veins to spawn in all dimensions"
        })
        public boolean utNeromanticPrimeFluidVeinDimensions = true;
    }

    public static class AOACategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Improved Player Tick")
        @Config.Comment("Improves AoA player ticking by only sending inventory changes when necessary")
        public boolean utImprovedPlayerTickToggle = true;
    }

    public static class BackpacksCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("No Offhand Interaction")
        @Config.Comment("Prevents unintended offhand right-click behavior when opening backpacks")
        public boolean utBPNoOffhandInteractionToggle = true;
    }

    public static class BewitchmentCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Witches' Oven Fix")
        @Config.Comment("Fixes Witches' Oven consuming container fuel items")
        public boolean utWitchesOvenFixToggle = true;
    }

    public static class BiblioCraftCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Allow Any Black Dye for Printing Press")
        @Config.Comment("Allow the Printing Press to properly work with any itemstack with the dyeBlack oredict, instead of only processing with Ink Sacs")
        public boolean utPrintingPressAnyBlackDyeToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Disable Version Check")
        @Config.Comment("Fixes client-side memory leak by disabling version check")
        public boolean utDisableVersionCheckToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Fix Armor Stand Slots")
        @Config.Comment("Fix BiblioCraft's custom Armor Stand using the incorrect slots for items or not recognizing the items as valid")
        public boolean utArmorStandSlotFixToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Fix Armor Stand Binding")
        @Config.Comment("Fix BiblioCraft's custom Armor Stand not respecting the Curse of Binding")
        public boolean utArmorStandBindingCurseToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Fix Fancy Sign Rotation")
        @Config.Comment("Fix Fancy Signs rendering items and blocks in different ways between the GUI and in-world")
        public boolean utFancySignRotationToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Fix Left Handed Rendering")
        @Config.Comment("Fixes the Antique Atlas and Clipboard rendering incorrect when the Main Hand is set to Left")
        public boolean utSwapDisplayHandWhenLeftHanded = true;

        @Config.RequiresMcRestart
        @Config.Name("Fix Using Incorrect Hand")
        @Config.Comment("Fix the Clipboard and the Stockroom Catalogue behaving incorrectly when used in the offhand")
        public boolean utFixHandConsumption = true;

        @Config.RequiresMcRestart
        @Config.Name("Fix IItemHandler Method Not Existing")
        @Config.Comment
            ({
                "Fix IItemHandler#getStackInSlot method not existing due to being obfuscated by IInventory, preventing countless errors",
                "This occurs because BiblioCraft's Tile Entities implement both IInventory and IItemHandler, and getStackInSlot is an obfuscated method in IInventory but not IItemHandler",
            })
        public boolean utEnsureIItemHandlerMethodToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Fix Item Transfer")
        @Config.Comment("Make BiblioCraft actually use simulate properly when inserting and extracting items, fixing many item transfer methods")
        public boolean utFixItemTransferToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Fix ItemStack Copying")
        @Config.Comment("Fixes removing an ItemStack not copying all data correctly, particularly for backpacks")
        public boolean utCopyItemStackCorrectlyToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Multiplayer Sound Fix")
        @Config.Comment("Register all sounds, fixing bugs when attempting to play them on servers")
        public boolean utRegisterSoundToggle = true;
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
        @Config.Name("Ritual Fix")
        @Config.Comment("Fixes ritual resetting on chunk/world unload")
        public boolean utBMRitualToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Memory Leak Fix")
        @Config.Comment("Fixes memory leak when unloading worlds/switching dimensions")
        public boolean utBMWorldUnloadToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Fluid Routing Fix")
        @Config.Comment("Fixes Routing Node fluid routing unable to support multiple liquids and soft-locking when encountering a full fluid tank")
        public boolean utFluidRoutingFixToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Bound Tool Harvest Tweak")
        @Config.Comment("Improves performance when harvesting blocks with Bound Tool's right-click and exposes block drops to HarvestDropsEvent")
        public boolean utBoundToolTweakToggle = true;
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

        @Config.Name("Alfheim Portal NBT Fix")
        @Config.Comment({
            "Greatly reduces the size of Alfheim Portal NBT data to prevent crashes",
            "This will also make their performance at high item count much better",
            "NOTE: Toggling this option off after it has been enabled will erase existing Alfheim Portals' NBT data!"
        })
        public boolean utAlfheimPortalNBTFix = true;
    }

    public static class BWMCategory
    {
        @Config.Name("Beacon NBT Loading Fix")
        @Config.Comment("Fixes BWM beacons reading null tags from vanilla beacons")
        public boolean utBeaconNBTLoadingFix = true;
    }

    public static class CBMultipartCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Memory Leak Fix")
        @Config.Comment("Fixes a memory leak associated with EntityPlayer")
        public boolean utMemoryLeakFixToggle = true;
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

    public static class CodeChickenLibCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Packet Leak Fix")
        @Config.Comment("Fixes network ByteBuf leaks from PacketCustom")
        public boolean utPacketLeakFixToggle = true;
    }

    public static class CoFHCoreCategory
    {
        @Config.Name("Multishot Enchantment For Any Bow")
        @Config.Comment("Applies the Multishot enchantment to most bows instead of vanilla and CoFH only")
        public boolean utCoFHMultishotToggle = true;

        @Config.Name("Vorpal Enchantment Damage")
        @Config.Comment("Sets the damage multiplier of the Vorpal enchantment")
        public double utCoFHVorpalDamage = 10.0D;
    }

    public static class CoFHWorldCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Prevent Superflat Generation")
        @Config.Comment("Prevents CoFH World features from generating in superflat world types")
        public boolean utCoFHSuperflatToggle = false;
    }

    public static class CollectiveCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Memory Leak Fix")
        @Config.Comment("Fixes memory leak when unloading worlds/switching dimensions")
        public boolean utMemoryLeakFixToggle = true;
    }

    public static class CompactMachinesCoreCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Allowed Spawns Improvement")
        @Config.Comment
            ({
                "Improves server performance by properly controlling spawn checks (effectiveness depends on CM's config)",
                "Disable both 'allowHostileSpawns' and 'allowPeacefulSpawns' in the CM config for best performance",
                "Does nothing if both config values are true"
            })
        public boolean utAllowedSpawnsImprovementToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Invisible Wall Render Fix")
        @Config.Comment("Fixes some compact machine walls being invisible if Nothirium 0.2.x (and up) or Vintagium is installed")
        public boolean utCMRenderFixToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Memory Leak Fix")
        @Config.Comment("Fixes client-side memory leak associated with miniaturization recipes")
        public boolean utMemoryLeakFixToggle = true;
    }

    public static class CookingForBlockheadsCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Oven Fix")
        @Config.Comment("Fixes CFB Oven consuming container fuel items")
        public boolean utOvenFixToggle = true;
    }

    public static class CorpseCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Opening GUIs Off-thread Fix")
        @Config.Comment("Fixes opening up GUIs on a non-client thread")
        public boolean utOpeningGuisOffThreadFixToggle = true;
    }

    public static class CyclicCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Memory Leak Fix")
        @Config.Comment("Fixes memory leak associated with advancements when creating FakePlayers")
        public boolean utMemoryLeakFixToggle = true;
    }

    public static class DankStorageCategory
    {
        @Config.Name("Fix Slot voiding")
        @Config.Comment("Fixes Max Int (2.1B) stacks being voided when right clicking on them in a Dank")
        public boolean utFixMaxIntVoid = true;
    }

    public static class DivineRPGCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Change Water Mob Creature Type")
        @Config.Comment("Changes the creature type for DivineRPG Water Mobs to be WATER_CREATURE, fixing issues with hostile mob spawn caps and infinite water mob spawning")
        public boolean utChangeWaterMobCreatureType = false;

        @Config.RequiresMcRestart
        @Config.Name("Fix Aquamarine Stack Size")
        @Config.Comment
            ({
                "Aquamarine nominally has durability, but does not set the max stack size to 1.",
                "As the tweak \"Fix Consuming Incorrect Hand\" fixes the bug preventing durability from being used, fixing the stack size is also needed."
            })
        public boolean utFixAquamarineStackSize = true;

        @Config.RequiresMcRestart
        @Config.Name("Fix Armor Set Effect Cleanup")
        @Config.Comment("Fixes issues with armor set effects being cleaned up under the wrong conditions that caused crashes")
        public boolean utFixArmorSetCleanup = true;

        @Config.RequiresMcRestart
        @Config.Name("Fix Consuming Incorrect Hand")
        @Config.Comment("Fix various DivineRPG items consuming the item in the main hand regardless of the hand actually used")
        public boolean utFixHandConsumption = true;
    }

    public static class EffortlessBuildingCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Block Transmutation Fix")
        @Config.Comment("Fixes Effortless Building ignoring Metadata when checking for items in inventory")
        public boolean utEFTransmutationFixToggle = true;
    }

    public static class ElectroblobsWizardryCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Construct's Armory Armor Fix")
        @Config.Comment("Fixes crash when wearing armors from Construct's Armory")
        public boolean utConstructsArmoryFixToggle = true;
    }

    public static class ElementaryStaffsCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Electric Staff Port")
        @Config.Comment("Reintroduces the 1.5 electric staff behavior along with some subtle particles")
        public boolean utESElectricStaffToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Health Staff Player Healing")
        @Config.Comment("Lets the health staff also heal other players (and potentially more living entities)")
        public boolean utESHealthStaffToggle = true;
    }

    public static class ElenaiDodge2Category
    {
        @Config.RequiresMcRestart
        @Config.Name("Extinguishing Dodges")
        @Config.Comment("Chance per dodge to extinguish the player when burning")
        public double utED2ExtinguishingDodgeChance = 0;

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

    public static class EnderIOCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Fix Chorus Farming StackOverflow")
        @Config.Comment("Fixes the Farming Station Chorus Walker being able to loop though and check the same positions endlessly, causing a StackOverflow")
        public boolean utChorusStackOverflow = true;

        @Config.RequiresMcRestart
        @Config.Name("Fix Soul Binder JEI Appearance")
        @Config.Comment("Fix the Soul Binder having empty ingredients or displaying filled soul vials in the output slot incorrectly")
        public boolean utFixSoulBinderJEI = true;

        @Config.RequiresMcRestart
        @Config.Name("Replace Obelisk Renderer")
        @Config.Comment("Fixes client-side memory leak by replacing obelisk renderer with a simpler one")
        public boolean utReplaceItemRenderer = true;

        @Config.RequiresMcRestart
        @Config.Name("Save Filter Cycle Buttons Properly")
        @Config.Comment("Fixes an issue where Cycle Buttons for Damage do not report being clicked when in the Picker Overlay, preventing changing Damage values until clicked again")
        public boolean utSaveFilterCycleButtonProperly = true;
    }

    public static class EnderStorageCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Fix Frequency Tracking")
        @Config.Comment("Fixes storage frequencies being tracked multiple times")
        public boolean utFrequencyTrackFixToggle = true;
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
        @Config.Name("Disable Death Compass")
        @Config.Comment("Disables granting a death compass upon demise without disabling Block o' Bones")
        public boolean utDeathCompassToggle = false;

        @Config.RequiresMcRestart
        @Config.Name("Fix Cabbage Drop")
        @Config.Comment("Fixes Cabbage not dropping the correct items in some situations")
        public boolean utCabbageDropToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Fix Quake Hammer Texture")
        @Config.Comment("Fixes the Quake Hammer using the incorrect config option to control its size")
        public boolean utFixQuakeHammerTextureToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Preserved Blocks Fix")
        @Config.Comment("Prevents HWYLA/TOP crashes with preserved blocks")
        public boolean utEBPreservedBlocksToggle = true;
    }

    public static class EvilCraftCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Vengeance Spirit Regex Cache")
        @Config.Comment("Cache the result of Vengeance Spirit checks against the config, which may attempt to build and check against hundreds of Regex Patterns every tick")
        public boolean utVengeanceSpiritCache = true;

        @Config.RequiresMcRestart
        @Config.Name("Vengeance Spirit Random Performance")
        @Config.Comment("Avoid repeatedly running intensive calculations involving spawning a random Vengeance Spirit")
        public boolean utVengeanceSpiritRandom = true;
    }

    public static class ExtraUtilitiesCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Catch Radar Exception")
        @Config.Comment
            ({
                "When near some inventories, the Radar feature (find in nearby inventories) will entirely break",
                "this catches the AbstractMethodException thrown, allowing other nearby inventories to be searched"
            })
        public boolean utCatchRadarException = true;

        @Config.RequiresMcRestart
        @Config.Name("Make Radar skip ungenerated chests")
        @Config.Comment("Makes the Radar skip inventories when the loottable for it has not yet been generated")
        public boolean utRadarSkipsLoottables = false;

        @Config.RequiresMcRestart
        @Config.Name("Fix Deep Dark Stats")
        @Config.Comment("Fixes Mob Attack and Health Statistics being repeatedly doubled")
        public boolean utDeepDarkStats = true;

        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Creative Mill Harvestability")
        @Config.Comment("Fixes the Creative Mill Generator not respecting the Creative Block Breaking config")
        public boolean utFixCreativeMillHarvestability = true;

        @Config.RequiresMcRestart
        @Config.Name("Mutable Machine Block Drops")
        @Config.Comment("Fixes Machine Block drops being immutable, causing a crash on attempting to remove entries from the list")
        public boolean utMutableBlockDrops = true;

        @Config.RequiresMcRestart
        @Config.Name("Downgrade Potion Recipes Log Level")
        @Config.Comment("Downgrades the message when creating a potion recipe from info to a debug")
        public boolean utDowngradePotionLogging = true;
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
        @Config.Name("Extra Trees: Gather Windfall")
        @Config.Comment("Allows Forestry farms to pick up ExtraTrees fruit")
        public boolean utFOGatherWindfallToggle = true;
    }

    public static class FpsReducerCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Correct FPS Display")
        @Config.Comment("Makes the HUD display the actual FPS of the game, instead of the FPS of the HUD itself")
        public boolean utCorrectFpsValue = true;
    }

    public static class GaiaDimensionCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Fix Restructurer Crash")
        @Config.Comment("Safely access a nullable array when checking recipes in the Restructurer")
        public boolean utFixNPERestructurerRecipe = true;
    }

    public static class ImmersiveEngineeringCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Tool Break Fire Event")
        @Config.Comment("Fires the PlayerDestroyItemEvent when an Immersive Engineering tool breaks, fixing a number of cross-compatibility issues")
        public boolean utFireBreakEvent = true;

        @Config.RequiresMcRestart
        @Config.Name("Tool Break Hand Replacement")
        @Config.Comment("Fixes the tool breaking setting the main hand to empty regardless of what hand the tool is in")
        public boolean utFixIncorrectHandReplacement = true;
    }

    public static class InControlCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Spawn Rule Stats Fix")
        @Config.Comment("Fixes onJoin spawn rules repeatedly modifying mob attack/health/speed")
        public boolean utStatsFixToggle = true;
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

        @Config.RequiresMcRestart
        @Config.Name("Machines Max Range Off-By-One Fix")
        @Config.Comment("Fixes an off-by-one error where IF Machines would display the max tier of range addon as one less than the actual maximum")
        public boolean utRangeAddonNumberFix = true;
    }

    public static class InfernalMobsCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Better Entity Names")
        @Config.Comment("Gets the actual display names of entities for improved naming")
        public boolean utIMBetterEntityNamesToggle = true;

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

    public static class IronChestsCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Replace Crystal Chest/Shulker Renderer")
        @Config.Comment
            ({
                "Fixes client-side memory leak by replacing the crystal chest/shulker box renderer with a simpler one",
                "Note: Stack sizes are not rendered, similar to modern versions of this mod"
            })
        public boolean utReplaceItemRenderer = true;
    }

    public static class ItemFavoritesCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Path correction")
        @Config.Comment("Fixes issues with favorite items not being saved/loaded correctly on Unix-based systems")
        public boolean utUnixPathFix = true;
    }

    public static class ItemStagesCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Ingredient Matching")
        @Config.Comment("Changes item matching code to CraftTweaker's ingredient matching system, fixes item NBT issues")
        public boolean utIngredientMatching = true;
    }

    public static class JurassicRebornCategory
    {
        @Config.Name("Geneticist Villager House Generation")
        @Config.Comment("Generates the geneticist house in villages")
        public boolean utGeneticistHouseGenToggle = true;
    }

    public static class MekanismCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Fluid Tank Extraction")
        @Config.Comment
            ({
                "Fixes a logic error allowing extracting fluids from fluid tanks regardless of requested fluid",
                "Has no effect when playing with Mekanism CEu"
            })
        public boolean utFluidTankExtraction = true;
    }

    public static class MoarTinkersCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Baubles Compatibility")
        @Config.Comment("Enables Energy Eater/Repair to pull from baubles")
        public boolean utBaublesCompatibility = true;
    }

    public static class MobStagesCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Spawning Rules Fixes")
        @Config.Comment("Fixes mob replacement ignoring entity spawning rules")
        public boolean utSpawningRules = true;
    }

    public static class ModularMagicCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Fix Null Ingredient")
        @Config.Comment("Fix a Null Pointer Exception in a few places caused by not checking if the ingredient is null before attempting to rendering it")
        public boolean utEnsureIngredientNotNull = true;
    }

    public static class ModularRoutersCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Particle Thread Fix")
        @Config.Comment("Fixes particles being added from the wrong thread which corrupted the particle manager")
        public boolean utParticleThreadToggle = true;
    }

    public static class MrTJPCoreCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Memory Leak Fix")
        @Config.Comment("Fixes a memory leak associated with EntityPlayer")
        public boolean utMemoryLeakFixToggle = true;
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
                "Changes the data table of the radiation environment handler to improve tick time",
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

    public static class OpenBlocksCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Last Stand Trigger Fix")
        @Config.Comment
            ({
                "Fixes the Last Stand enchantment triggering too early on pre-mitigation damage (before enchants, potions, etc)",
                "instead of on post-mitigation damage."
            })
        public boolean utLastStandFixToggle = true;
    }

    public static class ProjectRedCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
    }

    public static class ProperPumpkinCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Facing Crash Fix")
        @Config.Comment("Fixes a bug where converting a pumpkin from a non-horizontal face would crash")
        public boolean utFacingFix = true;
    }

    public static class QuarkCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Fix Untranslated Link Items")
        @Config.Comment("When using the Link Items feature, if playing on a server, items that are not localized serverside will display the lang code in chat. This causes it to be translated.")
        public boolean utLinkItemsServer = true;
    }

    public static class RailcraftCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("No Beta Warning")
        @Config.Comment("Disables the beta message warning on world join")
        public boolean utNoBetaWarningToggle = true;
    }

    public static class RandomThingsCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Anvil Crafting Fix")
        @Config.Comment("Fixes a bug where crafting the output of an Anvil recipe would modify the recipe, preventing crafts until restart")
        public boolean utAnvilCraftFix = true;

        @Config.RequiresMcRestart
        @Config.Name("Fix Spectre Dimension Teleport Stall")
        @Config.Comment
            ({
                "Fix a bug where teleporting to the Spectre dimension on servers can leave the player stalled out in the void",
                "Only applies to servers"
            })
        public boolean utTeleportStall = false;

        @Config.RequiresMcRestart
        @Config.Name("Item Collector Dupe")
        @Config.Comment("Fixes a duplication exploit connected to the Advanced Item Collector")
        public boolean utItemCollectorDupe = true;
    }

    public static class RequiousFraktoCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Particle Fixes")
        @Config.Comment("Fixes server world being leaked to various particles")
        public boolean utParticleFixesToggle = true;
    }

    public static class RFToolsDimensionsCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Fully Unregister Dimensions")
        @Config.Comment("Fixes a bug where joining a world or server with any RFTools Dimension registered would disallow entering another world without that dimension until restarting")
        public boolean utFullyUnregisterDimensions = true;
    }

    public static class RoostCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("ContentTweaker: Early Register CT Chickens")
        @Config.Comment
            ({
                "Improves load time by registering ContentTweaker chickens early for Roost to detect them",
                "Note: If you would like to use ContentTweaker's MaterialSystem Parts for the layed item, in your script you must:",
                "1) Use '#loader finalize_contenttweaker', not '#loader contenttweaker'",
                "2) Use the Material Part Bracket Handler to reference the item"
            })
        public boolean utRoostEarlyRegisterCTChickens = true;
    }

    public static class RootsCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Creative Pouch GUI Crash")
        @Config.Comment("Disable opening the Creative Pouch GUI as it immediately crashes")
        public boolean utDisableCreativePouchGUI = true;

        @Config.RequiresMcRestart
        @Config.Name("Modifier GUI Voiding")
        @Config.Comment("Fix an issue where disabled modifiers would still consume their material, voiding the ")
        public boolean utFixDisableModifierVoiding = true;

        @Config.RequiresMcRestart
        @Config.Name("Icicle Type Saving")
        @Config.Comment("Save the Icicle entity type as NBT, fixing a crash where the type was null")
        public boolean utFixIcicleSaving = true;

        @Config.RequiresMcRestart
        @Config.Name("Mortar Spell Dust Fix")
        @Config.Comment("Make the Mortar Spell Dust crafting output a copy of the ItemStack, avoiding issues where the recipe is inadvertently modified")
        public boolean utFixMortarSpellDust = true;

        @Config.RequiresMcRestart
        @Config.Name("Check Spirit Drop Oredict")
        @Config.Comment("Check if the oredict is registered before adding an entry to the list, preventing a bug when copper or silver ingots or nuggets are disabled")
        public boolean utFixSpiritDrops = true;

        @Config.RequiresMcRestart
        @Config.Name("Summon Creatures Ritual Infinite loop")
        @Config.Comment("Make the Summon Creatures Ritual stop when checking if blocks out of world are air, preventing an infinite loop when running the ritual above the void")
        public boolean utFixSummoningInfiniteDescent = true;

        @Config.RequiresMcRestart
        @Config.Name("Prevent Shatter Spell Breaking Unbreakable")
        @Config.Comment("Check if the target block is unbreakable for the Shatter Spell")
        public boolean utPreventShatterOnUnbreakable = true;

        @Config.RequiresMcRestart
        @Config.Name("Disable Element Soil Growth Acceleration")
        @Config.Comment("Disable Elemental Soils triggering updates on redstone updates")
        public boolean utPreventSoilNeighborUpdates = true;
    }

    public static class SimpleDifficultyCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Iron Canteen Interaction Fix")
        @Config.Comment("Fixes the interaction of iron canteens with rain collectors")
        public boolean utRainCollectorCanteenToggle = true;

        @Config.RequiresMcRestart
        @Config.Name("Altitude Modifier: Sea Level")
        @Config.Comment("Sets the sea level for altitude modifier calculations")
        public int utAltitudeSeaLevel = 64;

        @Config.RequiresMcRestart
        @Config.Name("Altitude Modifier: Above Sea Level Multiplier")
        @Config.Comment("Sets the multiplier above sea level for altitude modifier calculations")
        public double utAltitudeMultiplierAboveSeaLevel = 1.0D;

        @Config.RequiresMcRestart
        @Config.Name("Altitude Modifier: Below Sea Level Multiplier")
        @Config.Comment("Sets the multiplier below sea level for altitude modifier calculations")
        public double utAltitudeMultiplierBelowSeaLevel = 1.0D;
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

    public static class SteamWorldCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Sky of Old Dimension Fix")
        @Config.Comment("Fixes a Stack Overflow crash when entering the Sky of Old Dimension")
        public boolean utSkyOfOldFixToggle = true;
    }

    public static class StorageDrawersCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Item Voiding Fix")
        @Config.Comment("Prevents voiding of items when near capacity limits")
        public boolean utSDItemVoidingFixToggle = false;

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

    public static class TestDummyCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Copy Armor Stacks to Dummy")
        @Config.Comment
            ({
                "Instead of deleting the original itemstack being equipped, use a copy of it and do not drop armor.",
                "This is primarily relevant for fixing a duplication bug involving EnderIO Armor and its interact with being \"destroyed\""
            })
        public boolean utCopyArmor = true;
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

        @Config.Name("Maximum Items to Render in Smeltery")
        @Config.Comment
            ({
                "Determines the maximum number of possible items to display before not rendering any to prevent substantial lag",
                "0 to disable rendering items in the smeltery entirely",
                "-1 for the default, which is always rendering items"
            })
        public int utMaximumItemRendersInSmeltery = -1;

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

        @Config.RequiresMcRestart
        @Config.Name("Particle Fixes")
        @Config.Comment("Fixes server world being leaked to various particles")
        public boolean utParticleFixesToggle = true;

        @Config.LangKey("cfg.universaltweaks.modintegration.tcon.toolcustomization")
        @Config.Name("Tool Customization")
        public final ToolCustomizationCategory TOOL_CUSTOMIZATION = new ToolCustomizationCategory();

        public static class ToolCustomizationCategory
        {
            @Config.RequiresMcRestart
            @Config.Name("[1] Tool Customization")
            @Config.Comment("Enables usage of tweaks in below category to customize Tinkers' tools stats")
            public boolean utTConToolCustomizationToggle = true;

            @Config.Name("[2] General Attack Damage Cutoff")
            @Config.Comment
                ({
                    "Sets the attack damage cutoff at which diminishing returns start for any Tinkers' tool not listed here",
                    "Default value: 15.0"
                })
            public float utTConToolGeneralDamageCutoff = 15.0f;

            @Config.Name("[3] Cleaver Attack Damage Cutoff")
            @Config.Comment
                ({
                    "Sets the attack damage cutoff at which diminishing returns start for the cleaver",
                    "Default value: 25.0"
                })
            public float utTConToolCleaverDamageCutoff = 25.0f;

            @Config.Name("[4] Longsword Attack Damage Cutoff")
            @Config.Comment
                ({
                    "Sets the attack damage cutoff at which diminishing returns start for the longsword",
                    "Default value: 18.0"
                })
            public float utTConToolLongswordDamageCutoff = 18.0f;

            @Config.Name("[5] Rapier Attack Damage Cutoff")
            @Config.Comment
                ({
                    "Sets the attack damage cutoff at which diminishing returns start for the rapier",
                    "Default value: 13.0"
                })
            public float utTConToolRapierDamageCutoff = 13.0f;

            @Config.Name("[6] PlusTiC: Katana Attack Damage Cutoff")
            @Config.Comment
                ({
                    "Sets the attack damage cutoff at which diminishing returns start for the PlusTiC katana",
                    "Default value: 22.0"
                })
            public float utTConToolKatanaDamageCutoff = 22.0f;

            @Config.Name("[7] Attack Damage Decay Rate")
            @Config.Comment
                ({
                    "Sets the rate at which a tool's attack damage incrementally decays depending on its damage cutoff",
                    "Default value: 0.9",
                    "Range: 0.0 - 1.0",
                    "Note: A rate of 1.0 means there is no damage decay",
                    "Note: The damage curve will cap the maximum value to (tool's damage cutoff)/(1 - decay rate)"
                })
            public String utTConToolDamageDecayRate = "0.9";
        }
    }

    public static class TinyProgressionsCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Duplication Fixes")
        @Config.Comment("Fixes various duplication exploits")
        public boolean utDuplicationFixesToggle = true;
    }

    public static class TombManyGravesCategory
    {
        @SuppressWarnings("unused")
        @Config.RequiresMcRestart
        @Config.Name("Aether Legacy Accessory Compat")
        @Config.Comment
            ({
                "Universal Tweaks always adds AetherLegacyInventory compat to TombManyGraves2,",
                "but this functionality will only work properly if Aether Legacy: Capture Accessory Drops is enabled.",
                "This cannot be disabled, and this config only exists to notify that this functionality exists."
            })
        public String utUnusedAccessoryCompatEnabled = "ALWAYS_ENABLED";

        @Config.RequiresMcRestart
        @Config.Name("Change Timestamp")
        @Config.Comment("Change the timestamp used from 'MM_dd_YYYY_HH_mm_ss' to an ISO 8601 based format of 'yyyy-MM-dd'T'HH:mm:ss'")
        public boolean utISOTimestamp = false;

        @Config.RequiresMcRestart
        @Config.Name("Proper World Size Check")
        @Config.Comment("Fix TombManyGraves not spawning the grave due to incorrectly checking world height")
        public boolean utProperWorldSizeCheck = true;
    }

    public static class WootCategory
    {
        @Config.RequiresMcRestart
        @Config.Name("Cleanup Simulated Kills")
        @Config.Comment("Remove any leftover entities spawned on simulated mob's death")
        public boolean utCleanupSimulatedKillsToggle = true;
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