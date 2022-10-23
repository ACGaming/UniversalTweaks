package mod.acgaming.universaltweaks.tweaks.stronghold;

import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import mod.acgaming.universaltweaks.tweaks.stronghold.worldgen.MapGenSafeStronghold;
import mod.acgaming.universaltweaks.tweaks.stronghold.worldgen.SafeStrongholdWorldGenerator;

// Courtesy of yungnickyoung
@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTStronghold
{
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void utOnStrongholdGen(InitMapGenEvent event)
    {
        if (!UTConfig.tweaks.utStrongholdToggle) return;
        if (event.getType() == InitMapGenEvent.EventType.STRONGHOLD)
        {
            MapGenSafeStronghold safeStronghold = new MapGenSafeStronghold();
            event.setNewGen(safeStronghold);
            SafeStrongholdWorldGenerator.activeStronghold = safeStronghold;
        }
    }
}