package mod.acgaming.universaltweaks.tweaks.entities.sleeping;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayer.SleepResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of Funwayguy
public class UTSleepingTweak
{
    @SubscribeEvent
    public static void utSleepTweak(PlayerSleepInBedEvent event)
    {
        EntityPlayer player = event.getEntityPlayer();
        if (player.world.isRemote || player.isPlayerSleeping() || !player.isEntityAlive()) return;
        boolean debugEnabled = UTConfigGeneral.DEBUG.utDebugToggle;
        switch (UTConfigTweaks.ENTITIES.SLEEPING.utBedSetSpawnMode)
        {
            case DEFAULT:
                if (debugEnabled) UniversalTweaks.LOGGER.debug("UTSleepTweak ::: Set spawn point on wake up");
                break;

            case DISABLED:
                if (debugEnabled) UniversalTweaks.LOGGER.debug("UTSleepTweak ::: Set spawn point is disabled");
                break;

            case ANYTIME:
                if (debugEnabled) UniversalTweaks.LOGGER.debug("UTSleepTweak ::: Set spawn point without sleep");
                if (event.getResultStatus() == SleepResult.TOO_FAR_AWAY) return;
                if (player.isRiding()) player.dismountRidingEntity();
                player.setSpawnPoint(event.getPos(), false);
                player.sendStatusMessage(new TextComponentTranslation("msg.universaltweaks.sleep.spawnpoint"), false);
                break;
        }

        if (UTConfigTweaks.ENTITIES.SLEEPING.utDisableSleepingToggle)
        {
            if (debugEnabled) UniversalTweaks.LOGGER.debug("UTSleepTweak ::: Sleeping disabled");
            player.sendStatusMessage(new TextComponentTranslation("msg.universaltweaks.sleep.disabled"), false);
            event.setResult(SleepResult.OTHER_PROBLEM);
        }
    }
}
