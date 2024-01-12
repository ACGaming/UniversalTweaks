package mod.acgaming.universaltweaks.mods.elenaidodge2;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.elenai.elenaidodge2.api.DodgeEvent;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import mod.acgaming.universaltweaks.util.UTRandomUtil;

public class UTED2Burning
{
    @SubscribeEvent
    public void utED2Burning(DodgeEvent.ServerDodgeEvent event)
    {
        if (event.getPlayer().isBurning() && UTRandomUtil.chance(UTConfigMods.ELENAI_DODGE_2.utED2ExtinguishingDodgeChance))
        {
            event.getPlayer().extinguish();
        }
    }
}