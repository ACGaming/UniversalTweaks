package mod.acgaming.universaltweaks.tweaks.blocks.fastladderclimbing;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class UTFastLadderClimbing
{
    @SubscribeEvent
    public static void utOnPlayerClimb(LivingEvent.LivingUpdateEvent event) {

        if(!(event.getEntityLiving() instanceof EntityPlayer)) return;

        EntityPlayer player = (EntityPlayer) event.getEntityLiving();

        if(!player.isOnLadder() || player.isSneaking()) return;

        if(UTConfigTweaks.BLOCKS.FAST_LADDER_CLIMBING.utEnableFastAscent && player.rotationPitch < 0 && player.moveForward > 0)
        {
            player.move(MoverType.SELF, 0, verticalMovement(player, UTConfigTweaks.BLOCKS.FAST_LADDER_CLIMBING.utAscentSpeedModifier), 0);
        }
        else if(UTConfigTweaks.BLOCKS.FAST_LADDER_CLIMBING.utEnableFastDescent && player.rotationPitch > 0 && player.moveForward == 0)
        {
            player.move(MoverType.SELF, 0, -verticalMovement(player, UTConfigTweaks.BLOCKS.FAST_LADDER_CLIMBING.utDescentSpeedModifier), 0);
        }

    }

    private static double verticalMovement(EntityPlayer player, double speedModifier) {
        return Math.abs(player.rotationPitch / 90.0D) * speedModifier;
    }
}
