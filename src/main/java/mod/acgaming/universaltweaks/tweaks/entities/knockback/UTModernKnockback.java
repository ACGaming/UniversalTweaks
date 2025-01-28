package mod.acgaming.universaltweaks.tweaks.entities.knockback;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTModernKnockback
{
    @SubscribeEvent
    public static void utModernKnockback(LivingKnockBackEvent event)
    {
        if (!UTConfigTweaks.ENTITIES.utModernKnockbackToggle) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTModernKnockback ::: Living knock back event");
        event.setCanceled(true);
        EntityLivingBase entity = event.getEntityLiving();
        double strength = event.getStrength();
        double xRatio = event.getRatioX();
        double zRatio = event.getRatioZ();
        strength = strength * (1.0D - entity.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue());
        if (strength > 0.0F)
        {
            entity.isAirBorne = true;
            float f = MathHelper.sqrt(xRatio * xRatio + zRatio * zRatio);
            entity.motionX /= 2.0D;
            entity.motionZ /= 2.0D;
            entity.motionX -= xRatio / f * strength;
            entity.motionZ -= zRatio / f * strength;
            if (entity.onGround)
            {
                entity.motionY /= 2.0D;
                entity.motionY += strength;
                if (entity.motionY > 0.4D) entity.motionY = 0.4D;
            }
        }
    }
}