package mod.acgaming.universaltweaks.tweaks.entities.ai;

import java.util.Iterator;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of BuiltBrokenModding
@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTRemoveAI
{
    @SubscribeEvent
    public static void utRemoveAI(EntityJoinWorldEvent event)
    {
        if (!UTConfigTweaks.ENTITIES.utAIRemovalToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRemoveAI ::: Entity join world event");
        Entity entity = event.getEntity();
        if (entity instanceof EntityLiving)
        {
            EntityLiving living = (EntityLiving) entity;
            Iterator<EntityAITasks.EntityAITaskEntry> it = living.tasks.taskEntries.iterator();
            while (it.hasNext())
            {
                EntityAITasks.EntityAITaskEntry obj = it.next();
                if (obj != null)
                {
                    if (obj.action instanceof EntityAIWatchClosest) it.remove();
                    else if (obj.action instanceof EntityAILookIdle) it.remove();
                }
            }
        }
    }
}