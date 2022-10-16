package mod.acgaming.hkntweaks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

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
    public void init(FMLInitializationEvent event)
    {
        LOGGER.info("HkN Tweaks initialized");
    }
}