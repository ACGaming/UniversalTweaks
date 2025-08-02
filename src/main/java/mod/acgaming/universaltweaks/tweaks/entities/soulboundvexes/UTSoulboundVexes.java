package mod.acgaming.universaltweaks.tweaks.entities.soulboundvexes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class UTSoulboundVexes
{
    @SubscribeEvent
    public static void soulboundVexesEvent(LivingUpdateEvent event)
    {
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