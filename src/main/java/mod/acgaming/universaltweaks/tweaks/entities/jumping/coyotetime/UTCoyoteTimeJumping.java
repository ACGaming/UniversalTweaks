package mod.acgaming.universaltweaks.tweaks.entities.jumping.coyotetime;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;

// Courtesy of sparkflo
public class UTCoyoteTimeJumping
{
    private static boolean jumped = false;

    @SubscribeEvent
    public static void utCoyoteTimeJumping(TickEvent.PlayerTickEvent event)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTCoyoteTimeJumping ::: Player tick event");
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        if (event.phase != TickEvent.Phase.START || player == null || player.movementInput == null || player.dataManager == null) return;
        UTCoyoteTimeJumping.attemptJump(player);
    }

    private static void attemptJump(EntityPlayerSP player)
    {
        if (player.onGround) jumped = false;
        if (player.motionY > 0.1) jumped = true;
        if (jumped || player.isOnLadder() || player.isInWater() || player.isInLava() || player.world.collidesWithAnyBlock(player.getEntityBoundingBox().expand(0, -0.001, 0))) return;
        if (player.motionY < 0.1 && player.movementInput.jump && player.fallDistance < 1 && !player.isElytraFlying())
        {
            jumped = true;
            player.jump();
        }
    }
}
