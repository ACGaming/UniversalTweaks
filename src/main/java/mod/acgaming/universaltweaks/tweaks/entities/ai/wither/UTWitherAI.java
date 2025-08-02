package mod.acgaming.universaltweaks.tweaks.entities.ai.wither;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;

public class UTWitherAI
{
    @SubscribeEvent
    public static void utWitherAI(EntityJoinWorldEvent event)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTWitherAI ::: Entity join world event");
        Entity entity = event.getEntity();
        if (entity instanceof EntityWither)
        {
            EntityWither wither = (EntityWither) entity;
            wither.targetTasks.taskEntries.clear();
            wither.targetTasks.addTask(1, new EntityAIHurtByTarget(wither, false));
            wither.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(wither, EntityPlayer.class, false, false));
        }
    }
}