package mod.acgaming.universaltweaks.tweaks.world.stronghold;

import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import mod.acgaming.universaltweaks.tweaks.world.stronghold.worldgen.MapGenSafeStronghold;
import mod.acgaming.universaltweaks.tweaks.world.stronghold.worldgen.SafeStrongholdWorldGenerator;

// Courtesy of yungnickyoung
public class UTStronghold
{
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void utOnStrongholdGen(InitMapGenEvent event)
    {
        if (!UTConfig.TWEAKS_WORLD.utStrongholdToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTStronghold ::: Init map gen event");
        if (event.getType() == InitMapGenEvent.EventType.STRONGHOLD)
        {
            MapGenSafeStronghold safeStronghold = new MapGenSafeStronghold();
            event.setNewGen(safeStronghold);
            SafeStrongholdWorldGenerator.activeStronghold = safeStronghold;
        }
    }
}