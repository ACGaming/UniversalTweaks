package mod.acgaming.universaltweaks.tweaks.entities.sleeping;

import java.util.List;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;

// Courtesy of Funwayguy
public class UTDisableSleeping
{
    @SubscribeEvent
    public static void utDisableSleeping(PlayerSleepInBedEvent event)
    {
        if (event.getEntityPlayer().world.isRemote) return;
        if (event.getEntityPlayer().isPlayerSleeping() || !event.getEntityPlayer().isEntityAlive()) return;
        if (!(event.getEntityPlayer()).world.provider.canRespawnHere() || (event.getEntityPlayer()).world.isDaytime())
        {
            if (Math.abs((event.getEntityPlayer()).posX - event.getPos().getX()) > 3.0D || Math.abs((event.getEntityPlayer()).posY - event.getPos().getY()) > 2.0D || Math.abs((event.getEntityPlayer()).posZ - event.getPos().getZ()) > 3.0D) return;
        }
        double xOff = 8.0D;
        double yOff = 5.0D;
        List<?> list = (event.getEntityPlayer()).world.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(event.getPos().getX() - xOff, event.getPos().getY() - yOff, event.getPos().getZ() - xOff, event.getPos().getX() + xOff, event.getPos().getY() + yOff, event.getPos().getZ() + xOff));
        if (!list.isEmpty()) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTDisableSleeping ::: Player sleep in bed event");
        event.setResult(EntityPlayer.SleepResult.OTHER_PROBLEM);
        if (event.getEntityPlayer().isRiding()) event.getEntityPlayer().dismountRidingEntity();
        event.getEntityPlayer().setSpawnChunk(event.getPos(), false, (event.getEntityPlayer()).dimension);
        event.getEntityPlayer().sendStatusMessage(new TextComponentTranslation("msg.universaltweaks.sleep.spawnpoint"), true);
    }
}
