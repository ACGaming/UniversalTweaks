package mod.acgaming.universaltweaks.tweaks.swingthroughgrass;

import java.util.List;
import java.util.function.Function;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

// Courtesy of Exidex
@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTSwingThroughGrass
{
    public static final List<Function<EntityLivingBase, Boolean>> PREDICATES = Lists.newArrayList();

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void utSwingThroughGrass(PlayerInteractEvent.LeftClickBlock event)
    {
        if (!UTConfig.tweaks.utSwingThroughGrassToggle) return;
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTSwingThroughGrass ::: Left click block event");
        IBlockState state = event.getWorld().getBlockState(event.getPos()).getActualState(event.getWorld(), event.getPos());
        Block block = state.getBlock();
        if ((UTSwingThroughGrassLists.blacklistedBlocks.contains(block) || state.getCollisionBoundingBox(event.getWorld(), event.getPos()) != Block.NULL_AABB) && !UTSwingThroughGrassLists.whitelistedBlocks.contains(block)) return;
        EntityPlayer player = event.getEntityPlayer();
        if (player == null) return;
        float blockReachDistance = 4.5F;
        Vec3d from = new Vec3d(player.posX, player.posY + (double) player.getEyeHeight(), player.posZ);
        Vec3d vec3d = player.getLook(1.0F);
        Vec3d to = from.add(vec3d.x * blockReachDistance, vec3d.y * blockReachDistance, vec3d.z * blockReachDistance);
        EntityLivingBase targetEntity = getEntityClosestToStartPos(player, event.getWorld(), from, to);
        if (targetEntity != null)
        {
            if (!event.getWorld().isRemote)
            {
                player.attackTargetEntityWithCurrentItem(targetEntity);
                player.resetCooldown();
            }
            event.setCanceled(true);
        }
    }

    public static EntityLivingBase getEntityClosestToStartPos(EntityPlayer player, World world, Vec3d startPos, Vec3d endPos)
    {
        EntityLivingBase entityLiving = null;
        List<Entity> list = world.getEntitiesInAABBexcluding(player, new AxisAlignedBB(startPos.x, startPos.y, startPos.z, endPos.x, endPos.y, endPos.z), Predicates.and(EntitySelectors.CAN_AI_TARGET, e -> {
            boolean filter = e != null && e.canBeCollidedWith() && e instanceof EntityLivingBase && !(e instanceof FakePlayer);
            if (filter) for (Function<EntityLivingBase, Boolean> predicate : PREDICATES) filter &= predicate.apply((EntityLivingBase) e);
            return filter;
        }));
        double d0 = 0.0D;
        AxisAlignedBB axisAlignedBB;
        for (Entity entity : list)
        {
            axisAlignedBB = entity.getEntityBoundingBox().expand(0.3D, 0.3D, 0.3D);
            RayTraceResult raytraceResult = axisAlignedBB.calculateIntercept(startPos, endPos);
            if (raytraceResult != null)
            {
                double d1 = startPos.squareDistanceTo(raytraceResult.hitVec);
                if (d1 < d0 || d0 == 0.0D)
                {
                    entityLiving = (EntityLivingBase) entity;
                    d0 = d1;
                }
            }
        }
        return entityLiving;
    }
}