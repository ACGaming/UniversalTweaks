package mod.acgaming.hkntweaks;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.Side;

import mod.acgaming.hkntweaks.config.HkNTweaksConfig;
import mod.acgaming.hkntweaks.tweaks.HkNAttributes;
import mod.acgaming.hkntweaks.tweaks.stronghold.HkNStronghold;
import mod.acgaming.hkntweaks.tweaks.stronghold.worldgen.SafeStrongholdWorldGenerator;

@Mod(modid = HkNTweaks.MODID,
    name = HkNTweaks.NAME,
    version = HkNTweaks.VERSION,
    acceptedMinecraftVersions = "[1.12.2]",
    dependencies = HkNTweaks.DEPENDENCIES)
public class HkNTweaks
{
    public static final String MODID = "hkntweaks";
    public static final String NAME = "HkN Tweaks";
    public static final String VERSION = "1.12.2-1.0.0";
    public static final String DEPENDENCIES = "required-after:mixinbooter;" +
        "after:attributefix;" +
        "after:bedbreakbegone;" +
        "after:blockoverlayfix;" +
        "after:bowinfinityfix;" +
        "after:experiencebugfix;" +
        "after:letmedespawn;" +
        "after:loginhpfix;" +
        "after:mendingfix;" +
        "after:savemystronghold;" +
        "after:stepupfix";
    public static final Logger LOGGER = LogManager.getLogger();

    public static void throwIncompatibility()
    {
        List<String> messages = new ArrayList<>();
        messages.add("HkN Tweaks has replaced and improved upon functionalities from the following mods.");
        messages.add("Therefore, these mods are now incompatible with HkN Tweaks:");
        messages.add("");
        if (Loader.isModLoaded("attributefix")) messages.add("AttributeFix");
        if (Loader.isModLoaded("bedbreakbegone")) messages.add("BedBreakBegone");
        if (Loader.isModLoaded("blockoverlayfix")) messages.add("Block Overlay Fix");
        if (Loader.isModLoaded("bowinfinityfix")) messages.add("Bow Infinity Fix");
        if (Loader.isModLoaded("experiencebugfix")) messages.add("Fix Experience Bug");
        if (Loader.isModLoaded("letmedespawn")) messages.add("Let Me Despawn");
        if (Loader.isModLoaded("loginhpfix")) messages.add("Login HP Fix");
        if (Loader.isModLoaded("mendingfix")) messages.add("Mending Fix");
        if (Loader.isModLoaded("savemystronghold")) messages.add("Save My Stronghold!");
        if (Loader.isModLoaded("stepupfix")) messages.add("StepupFixer");
        if (messages.size() > 3)
        {
            if (FMLLaunchHandler.side() == Side.CLIENT) HkNObsoleteModsHandler.throwException(messages);
            else
            {
                messages.add("");
                throw new RuntimeException(String.join(System.lineSeparator(), messages));
            }
        }
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        throwIncompatibility();
        if (HkNTweaksConfig.tweaks.hknAttributesToggle) HkNAttributes.hknSetAttributes();
        if (HkNTweaksConfig.tweaks.hknStrongholdToggle) GameRegistry.registerWorldGenerator(new SafeStrongholdWorldGenerator(), Integer.MAX_VALUE);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        if (HkNTweaksConfig.tweaks.hknStrongholdToggle) MinecraftForge.TERRAIN_GEN_BUS.register(new HkNStronghold());
        LOGGER.info("HkN Tweaks initialized");
    }
}