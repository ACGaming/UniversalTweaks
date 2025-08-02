package mod.acgaming.universaltweaks.tweaks.misc.damagetilt;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.util.UTPacketHandler;

// Courtesy of Charles445
public class UTDamageTilt
{
    @SubscribeEvent
    public static void utDamageTilt(LivingKnockBackEvent event)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTDamageTilt ::: Tilt view");
        if (event.getEntityLiving() instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (player.world.isRemote) return;
            UTPacketHandler.instance.sendTo(new UTDamageTiltMessage(player), (EntityPlayerMP) player);
        }
    }
}