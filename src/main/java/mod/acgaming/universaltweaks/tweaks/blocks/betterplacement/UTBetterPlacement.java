package mod.acgaming.universaltweaks.tweaks.blocks.betterplacement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of tterrag1098, BucketOfCompasses
public class UTBetterPlacement
{
    private static Type lastHitType = Type.MISS;
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
            if (hover != null)
            {
                Type hitType = hover.typeOfHit;
                if (hitType == Type.BLOCK)
                {
                    int timer = Minecraft.getMinecraft().rightClickDelayTimer;
                    BlockPos pos = hover.getBlockPos();
                    EnumFacing side = hover.sideHit;
                    Vec3d playerVector = Minecraft.getMinecraft().player.getPositionVector();

                    // Timer not cooled down
                    if (timer > 0)
                    {
                        // Newly targeting a block, or targeting a changed blockPos that is nor what you just placed, neither what you just removed.[*]
                        if (lastHitType == Type.MISS || (lastHitType == Type.BLOCK && !pos.equals(lastTargetPos)
                            // You just placed this block.
                            && !pos.equals(lastTargetPos.offset(lastTargetSide))
                            // [*]: Special case for blocks that you right-click to remove, e.g. by holding a Chest Transporter.
                            && !lastTargetPos.equals(pos.offset(side))))
                        {
                            Minecraft.getMinecraft().rightClickDelayTimer = 0;
                        }
                    }
                    else
                    {
                        EntityPlayerSP player = Minecraft.getMinecraft().player;
                        // This is the default behaviour of Entity#getPosition(), which is correct here instead of EntityPlayerSP#getPosition()
                        BlockPos playerPos = new BlockPos(player.posX, player.posY + 0.5, player.posZ);
                        // Player is building straight-up
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
                lastHitType = hitType;
            }
        }
    }

    private static boolean isInteractableAt(BlockPos pos)
    {
        return ((IInteractable) Minecraft.getMinecraft().world.getBlockState(pos).getBlock()).isInteractable();
    }
}