package mod.acgaming.universaltweaks.tweaks.stronghold;

import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import mod.acgaming.universaltweaks.tweaks.stronghold.worldgen.MapGenSafeStronghold;
import mod.acgaming.universaltweaks.tweaks.stronghold.worldgen.SafeStrongholdWorldGenerator;

// Courtesy of yungnickyoung
public class UTStronghold
{
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void utOnStrongholdGen(InitMapGenEvent event)
    {
        if (!UTConfig.tweaks.utStrongholdToggle) return;
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTStronghold ::: Init map gen event");
        if (event.getType() == InitMapGenEvent.EventType.STRONGHOLD)
        {
            MapGenSafeStronghold safeStronghold = new MapGenSafeStronghold();
            event.setNewGen(safeStronghold);
            SafeStrongholdWorldGenerator.activeStronghold = safeStronghold;
        }
    }
}