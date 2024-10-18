package mod.acgaming.universaltweaks.tweaks.blocks.slimeblock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlime;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of CAS-ual-TY
@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTSlimeBlockProjectiles
{
    @SubscribeEvent
    public static void utSlimeBlockProjectiles(ProjectileImpactEvent event)
    {
        if (!UTConfigTweaks.BLOCKS.utSlimeBlockProjectiles) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTSlimeBlockProjectiles ::: Projectile impact event");
        RayTraceResult rayTraceResult = event.getRayTraceResult();
        if (rayTraceResult.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            BlockPos pos = rayTraceResult.getBlockPos();
            World world = event.getEntity().world;
            Block block = world.getBlockState(pos).getBlock();
            if (block instanceof BlockSlime)
            {
                EnumFacing direction = rayTraceResult.sideHit;
                if (direction.getXOffset() != 0) event.getEntity().motionX *= -0.1D;
                else if (direction.getYOffset() != 0) event.getEntity().motionY *= -0.1D;
                else if (direction.getZOffset() != 0) event.getEntity().motionZ *= -0.1D;
                world.playSound(null, pos, SoundEvents.BLOCK_SLIME_HIT, SoundCategory.BLOCKS, 1.0F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F + 1.0F);
                event.setCanceled(true);
            }
        }
    }
}
