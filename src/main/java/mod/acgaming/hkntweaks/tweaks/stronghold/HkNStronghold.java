package mod.acgaming.hkntweaks.tweaks.stronghold;

import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.hkntweaks.HkNTweaks;
import mod.acgaming.hkntweaks.config.HkNTweaksConfig;
import mod.acgaming.hkntweaks.tweaks.stronghold.worldgen.MapGenSafeStronghold;
import mod.acgaming.hkntweaks.tweaks.stronghold.worldgen.SafeStrongholdWorldGenerator;

// Courtesy of yungnickyoung
@Mod.EventBusSubscriber(modid = HkNTweaks.MODID)
public class HkNStronghold
{
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onStrongholdGen(InitMapGenEvent event)
    {
        if (!HkNTweaksConfig.tweaks.hknStrongholdToggle) return;
        if (event.getType() == InitMapGenEvent.EventType.STRONGHOLD)
        {
            MapGenSafeStronghold safeStronghold = new MapGenSafeStronghold();
            event.setNewGen(safeStronghold);
            SafeStrongholdWorldGenerator.activeStronghold = safeStronghold;
        }
    }
}