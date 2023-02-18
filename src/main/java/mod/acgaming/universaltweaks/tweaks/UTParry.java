package mod.acgaming.universaltweaks.tweaks;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;

// Courtesy of Drullkus
@Mod.EventBusSubscriber(modid = UniversalTweaks.MODID)
public class UTParry
{
    public static Enchantment enchantment;
    public static EnumEnchantmentType enchantmentTypeShield;

    @SubscribeEvent
    public static void utRegisterEnchantment(RegistryEvent.Register<Enchantment> event)
    {
        if (!UTConfig.TWEAKS_ITEMS.PARRY.utParryToggle || !UTConfig.TWEAKS_ITEMS.PARRY.utParryReboundToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTParry ::: Register enchantment");
        enchantmentTypeShield = EnumHelper.addEnchantmentType("shield", input -> input instanceof ItemShield || input != null && input.isShield(new ItemStack(input, 1, 0), null));
        enchantment = new EnchantmentRebound(Enchantment.Rarity.COMMON).setRegistryName(new ResourceLocation("parry", "parry")).setName("parry");
        event.getRegistry().register(enchantment);
    }

    @SubscribeEvent
    public static void utArrowParry(ProjectileImpactEvent.Arrow event)
    {
        if (!UTConfig.TWEAKS_ITEMS.PARRY.utParryToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTParry ::: Parry arrow");
        final EntityArrow projectile = event.getArrow();
        if (!projectile.getEntityWorld().isRemote)
        {
            Entity entity = event.getRayTraceResult().entityHit;
            if (event.getEntity() != null)
            {
                if (entity instanceof EntityLivingBase)
                {
                    EntityLivingBase entityBlocking = (EntityLivingBase) entity;
                    if (entityBlocking.canBlockDamageSource(new DamageSource("parry_this")
                    {
                        public Vec3d getDamageLocation()
                        {
                            return projectile.getPositionVector();
                        }
                    }) && (entityBlocking.getActiveItemStack().getItem().getMaxItemUseDuration(entityBlocking.getActiveItemStack()) - entityBlocking.getItemInUseCount()) <= applyTimerBonus(UTConfig.TWEAKS_ITEMS.PARRY.utParryArrowTimeWindow, entityBlocking.getActiveItemStack(), UTConfig.TWEAKS_ITEMS.PARRY.utParryReboundMultiplier))
                    {
                        Vec3d playerVec3 = entityBlocking.getLookVec();
                        projectile.shoot(playerVec3.x, playerVec3.y, playerVec3.z, 1.1F, 0.1F);
                        projectile.shootingEntity = entityBlocking;
                        entityBlocking.world.playSound(null, entityBlocking.getPosition(), SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.PLAYERS, 1.0F, 0.8F + entityBlocking.world.rand.nextFloat() * 0.4F);
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void utFireballParry(ProjectileImpactEvent.Fireball event)
    {
        if (!UTConfig.TWEAKS_ITEMS.PARRY.utParryToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTParry ::: Parry fireball");
        final EntityFireball projectile = event.getFireball();
        if (!projectile.getEntityWorld().isRemote)
        {
            Entity entity = event.getRayTraceResult().entityHit;
            if (event.getEntity() != null)
            {
                if (entity instanceof EntityLivingBase)
                {
                    EntityLivingBase entityBlocking = (EntityLivingBase) entity;
                    if (entityBlocking.canBlockDamageSource(new DamageSource("parry_this")
                    {
                        public Vec3d getDamageLocation()
                        {
                            return projectile.getPositionVector();
                        }
                    }) && (entityBlocking.getActiveItemStack().getItem().getMaxItemUseDuration(entityBlocking.getActiveItemStack()) - entityBlocking.getItemInUseCount()) <= applyTimerBonus(UTConfig.TWEAKS_ITEMS.PARRY.utParryFireballTimeWindow, entityBlocking.getActiveItemStack(), UTConfig.TWEAKS_ITEMS.PARRY.utParryReboundMultiplier))
                    {
                        Vec3d playerVec3 = entityBlocking.getLookVec();
                        projectile.motionX = playerVec3.x;
                        projectile.motionY = playerVec3.y;
                        projectile.motionZ = playerVec3.z;
                        projectile.accelerationX = projectile.motionX * 0.1D;
                        projectile.accelerationY = projectile.motionY * 0.1D;
                        projectile.accelerationZ = projectile.motionZ * 0.1D;
                        projectile.shootingEntity = entityBlocking;
                        entityBlocking.world.playSound(null, entityBlocking.getPosition(), SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.PLAYERS, 1.0F, 0.8F + entityBlocking.world.rand.nextFloat() * 0.4F);
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void utThrowableParry(ProjectileImpactEvent.Throwable event)
    {
        if (!UTConfig.TWEAKS_ITEMS.PARRY.utParryToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTParry ::: Parry throwable");
        final EntityThrowable projectile = event.getThrowable();
        if (!projectile.getEntityWorld().isRemote)
        {
            Entity entity = event.getRayTraceResult().entityHit;
            if (event.getEntity() != null && entity instanceof EntityLivingBase)
            {
                EntityLivingBase entityBlocking = (EntityLivingBase) entity;
                if (entityBlocking.canBlockDamageSource(new DamageSource("parry_this")
                {
                    public Vec3d getDamageLocation()
                    {
                        return projectile.getPositionVector();
                    }
                }) && (entityBlocking.getActiveItemStack().getItem().getMaxItemUseDuration(entityBlocking.getActiveItemStack()) - entityBlocking.getItemInUseCount()) <= applyTimerBonus(UTConfig.TWEAKS_ITEMS.PARRY.utParryThrowableTimeWindow, entityBlocking.getActiveItemStack(), UTConfig.TWEAKS_ITEMS.PARRY.utParryReboundMultiplier))
                {
                    Vec3d playerVec3 = entityBlocking.getLookVec();
                    projectile.shoot(playerVec3.x, playerVec3.y, playerVec3.z, 1.1F, 0.1F);
                    projectile.thrower = entityBlocking;
                    entityBlocking.world.playSound(null, entityBlocking.getPosition(), SoundEvents.ITEM_SHIELD_BLOCK, SoundCategory.PLAYERS, 1.0F, 0.8F + entityBlocking.world.rand.nextFloat() * 0.4F);
                    event.setCanceled(true);
                }
            }
        }
    }

    public static int applyTimerBonus(int base, ItemStack stack, float multiplier)
    {
        return (int) (base + base * getEnchantedLevel(stack) * multiplier);
    }

    public static int getEnchantedLevel(ItemStack stack)
    {
        int level = 0;
        for (NBTBase nbt : stack.getEnchantmentTagList())
        {
            if (nbt instanceof NBTTagCompound)
            {
                NBTTagCompound compound = (NBTTagCompound) nbt;
                if (Enchantment.getEnchantmentByID(compound.getShort("id")) == enchantment) level += compound.getShort("lvl");
            }
        }
        return level;
    }

    public static class EnchantmentRebound extends Enchantment
    {
        EnchantmentRebound(Rarity rarityIn)
        {
            super(rarityIn, enchantmentTypeShield, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND, EntityEquipmentSlot.OFFHAND});
        }

        @Override
        public int getMaxLevel()
        {
            return UTConfig.TWEAKS_ITEMS.PARRY.utParryReboundMaxLevel;
        }

        @Override
        public boolean isTreasureEnchantment()
        {
            return UTConfig.TWEAKS_ITEMS.PARRY.utParryReboundTreasure;
        }

        @Override
        public boolean canApplyAtEnchantingTable(ItemStack stack)
        {
            return stack.getItem() instanceof ItemShield || stack.getItem().isShield(stack, null);
        }
    }
}