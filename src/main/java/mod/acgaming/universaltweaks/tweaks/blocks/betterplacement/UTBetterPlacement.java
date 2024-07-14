package mod.acgaming.universaltweaks.tweaks.blocks.betterplacement;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

// Courtesy of tterrag1098, BucketOfCompasses
public class UTBetterPlacement
{
    private static BlockPos lastTargetPos;
    private static Vec3d lastPlayerPos;
    private static EnumFacing lastTargetSide;

    @SubscribeEvent
    public static void utBetterPlacement(TickEvent.ClientTickEvent event)
    {
        if (Minecraft.getMinecraft().world == null || Minecraft.getMinecraft().player == null) return;
        if (event.phase == Phase.START && (!UTConfigTweaks.BLOCKS.BETTER_PLACEMENT.utBetterPlacementCreative || Minecraft.getMinecraft().player.isCreative()))
        {
            RayTraceResult hover = Minecraft.getMinecraft().objectMouseOver;
            if (hover != null && hover.typeOfHit == Type.BLOCK)
            {
                int timer = Minecraft.getMinecraft().rightClickDelayTimer;
                BlockPos pos = hover.getBlockPos();
                EnumFacing side = hover.sideHit;
                Vec3d playerVector = Minecraft.getMinecraft().player.getPositionVector();
                if (timer > 0)
                {
                    if (!pos.equals(lastTargetPos) && (lastTargetPos == null || !pos.equals(lastTargetPos.offset(lastTargetSide))))
                    {
                        Minecraft.getMinecraft().rightClickDelayTimer = 0;
                    }
                }
                else
                {
                    BlockPos playerPos = Minecraft.getMinecraft().player.getPosition();
                    if (side == EnumFacing.UP && !playerVector.equals(lastPlayerPos) && playerPos.getX() == pos.getX() && playerPos.getZ() == pos.getZ())
                    {
                        Minecraft.getMinecraft().rightClickDelayTimer = 0;
                    }
                    else if (UTConfigTweaks.BLOCKS.BETTER_PLACEMENT.utBetterPlacementNewLoc && pos.equals(lastTargetPos) && side == lastTargetSide && !isInteractableAt(pos))
                    {
                        Minecraft.getMinecraft().rightClickDelayTimer = 4;
                    }
                }
                lastTargetPos = pos.toImmutable();
                lastPlayerPos = playerVector;
                lastTargetSide = side;
            }
        }
    }

    private static boolean isInteractableAt(BlockPos pos)
    {
        return ((IInteractable) Minecraft.getMinecraft().world.getBlockState(pos).getBlock()).isInteractable();
    }
}