package mod.acgaming.universaltweaks.tweaks.entities.burning;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent.OverlayType;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of Darkhax
public class UTBetterBurning
{
    // Fixes some edge cases where fire damage sources won't cause mobs to drop their cooked items
    @SubscribeEvent
    public static void utLivingDeath(LivingDeathEvent event)
    {
        if (UTConfigTweaks.ENTITIES.BETTER_BURNING.utBBCookedToggle && event.getSource().isFireDamage() && !event.getEntityLiving().isBurning() && !event.getEntity().world.isRemote)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTBetterBurning ::: Living death event");
            event.getEntityLiving().setFire(1);
        }
    }

    // Allows skeletons to shoot flaming arrows when on fire
    @SubscribeEvent
    public static void utEntityJoinWorld(EntityJoinWorldEvent event)
    {
        if (UTConfigTweaks.ENTITIES.BETTER_BURNING.utBBArrowsToggle && event.getEntity() instanceof EntityArrow && !event.getEntity().world.isRemote)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTBetterBurning ::: Entity join world event");
            final EntityArrow arrowEntity = (EntityArrow) event.getEntity();
            final Entity shooter = arrowEntity.shootingEntity;
            float regionalDifficulty = event.getEntity().world.getDifficultyForLocation(new BlockPos(event.getEntity())).getAdditionalDifficulty();
            if (shooter instanceof AbstractSkeleton && shooter.isBurning() && !shooter.isDead && tryPercentage(regionalDifficulty * 0.3))
            {
                arrowEntity.setFire(100);
            }
        }
    }

    // If entities have fire resistance, they get extinguished right away when on fire
    @SubscribeEvent
    public static void utLivingTick(LivingUpdateEvent event)
    {
        if (UTConfigTweaks.ENTITIES.BETTER_BURNING.utBBExtinguishToggle && !event.getEntityLiving().world.isRemote && event.getEntityLiving().isBurning() && event.getEntityLiving().isPotionActive(MobEffects.FIRE_RESISTANCE))
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTBetterBurning ::: Living update event");
            event.getEntityLiving().extinguish();
        }
    }

    // Allows fire to spread from entity to entity
    @SubscribeEvent
    public static void utLivingAttack(LivingAttackEvent event)
    {
        if (UTConfigTweaks.ENTITIES.BETTER_BURNING.utBBSpreadingToggle && !event.getEntity().world.isRemote)
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTBetterBurning ::: Living attack event");
            final Entity sourceEntity = event.getSource().getTrueSource();
            if (sourceEntity instanceof EntityLivingBase)
            {
                final EntityLivingBase sourceLiving = (EntityLivingBase) sourceEntity;
                final ItemStack heldItem = sourceLiving.getHeldItemMainhand();
                float regionalDifficulty = event.getEntity().world.getDifficultyForLocation(new BlockPos(event.getEntity())).getAdditionalDifficulty();
                if (!(sourceLiving instanceof EntityZombie) && heldItem.isEmpty() && sourceLiving.isBurning() && tryPercentage(regionalDifficulty * 0.3))
                {
                    final float damage = Math.max(1, regionalDifficulty);
                    event.getEntityLiving().setFire(2 * (int) damage);
                }
            }
        }
    }

    // Courtesy of Turkey9002
    // Prevents the first person fire overlay from displaying when the player is immune to fire, has fire resistance, or is in creative mode (backported)
    @SubscribeEvent
    public static void utBlockOverlay(RenderBlockOverlayEvent event)
    {
        if (UTConfigTweaks.ENTITIES.BETTER_BURNING.utBBOverlayToggle && event.getOverlayType() == OverlayType.FIRE && (event.getPlayer().isImmuneToFire() || event.getPlayer().isPotionActive(MobEffects.FIRE_RESISTANCE) || event.getPlayer().isCreative()))
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTBetterBurning ::: Render block overlay event");
            event.setCanceled(true);
        }
    }

    public static boolean tryPercentage(double chance)
    {
        return Math.random() < chance;
    }
}