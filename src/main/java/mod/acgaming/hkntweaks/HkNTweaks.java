package mod.acgaming.hkntweaks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
    public static final String DEPENDENCIES = "required-after:mixinbooter";
    public static final Logger LOGGER = LogManager.getLogger();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
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