package mod.acgaming.universaltweaks.tweaks.entities.griefing;

import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

public class UTMobGriefing
{
    @SubscribeEvent
    public static void onMobGriefing(EntityMobGriefingEvent event)
    {
        ResourceLocation resourceLocation = EntityList.getKey(event.getEntity());
        if (resourceLocation == null) return;
        List<String> list = Arrays.asList(UTConfigTweaks.ENTITIES.MOB_GRIEFING.utEntityList);
        boolean isBlacklist = UTConfigTweaks.ENTITIES.MOB_GRIEFING.utEntityListMode == UTConfigTweaks.EnumLists.BLACKLIST;
        if (list.contains(resourceLocation.toString()) == isBlacklist)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTMobGriefing ::: Mob griefing denied for {}", event.getEntity().getName());
            event.setResult(Event.Result.DENY);
        }
    }
}
