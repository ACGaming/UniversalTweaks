package mod.acgaming.universaltweaks.tweaks.entities.sleeping;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayer.SleepResult;
import net.minecraft.util.math.BlockPos;
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
        boolean debugEnabled = UTConfigGeneral.DEBUG.utDebugToggle;
        EntityPlayer player = event.getEntityPlayer();
        BlockPos pos = event.getPos();

        if (player.world.isRemote) return;
        if (player.isPlayerSleeping() || !player.isEntityAlive()) return;

        SleepResult result = event.getResultStatus();

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

                if (result == SleepResult.TOO_FAR_AWAY) return;

                if (player.isRiding()) player.dismountRidingEntity();
                player.setSpawnPoint(pos, false);
                player.sendStatusMessage(new TextComponentTranslation("msg.universaltweaks.sleep.spawnpoint"), true);
                event.setResult(SleepResult.OTHER_PROBLEM);

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