package mod.acgaming.universaltweaks.tweaks.entities.soulboundvexes;

import org.apache.logging.log4j.Level;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTSoulboundVexes
{
    @SubscribeEvent
    public static void soulboundVexesEvent(LivingUpdateEvent event)
	{
    	if (!(UTConfigTweaks.ENTITIES.utSoulboundVexesToggle)) return;
    	
        if (event.getEntity() instanceof EntityVex)
		{
            Entity summoner = ((EntityVex) event.getEntity()).getOwner();

            if (summoner != null && summoner.isDead && !(event.getEntity().isDead))
			{
                event.getEntity().attackEntityFrom(DamageSource.GENERIC.setDamageIsAbsolute().setDamageBypassesArmor(), event.getEntityLiving().getMaxHealth());
            }
        }
    }
}