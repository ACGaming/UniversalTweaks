package mod.acgaming.universaltweaks.tweaks.mixin;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;

// Courtesy of frikinjay
@Mixin(EntityMob.class)
public abstract class UTMobDespawnMixin extends EntityCreature
{
    public boolean pickedItems = false;

    public UTMobDespawnMixin(World worldIn)
    {
        super(worldIn);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void updateEquipmentIfNeeded(EntityItem itemEntity)
    {
        super.updateEquipmentIfNeeded(itemEntity);
        if (!UTConfig.TWEAKS_ENTITIES.utMobDespawnToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTMobDespawn ::: Update equipment");
        this.pickedItems = true;
        ((EntityLivingAccessor) this).setPersistenceRequired(this.hasCustomName());
    }

    @Override
    public void despawnEntity()
    {
        if (!UTConfig.TWEAKS_ENTITIES.utMobDespawnToggle)
        {
            super.despawnEntity();
            return;
        }
        net.minecraftforge.fml.common.eventhandler.Event.Result result;
        if (((EntityLivingAccessor) this).getPersistenceRequired()) this.idleTime = 0;
        else if ((this.idleTime & 0x1F) == 0x1F && (result = net.minecraftforge.event.ForgeEventFactory.canEntityDespawn(this)) != net.minecraftforge.fml.common.eventhandler.Event.Result.DEFAULT)
        {
            if (result == net.minecraftforge.fml.common.eventhandler.Event.Result.DENY) this.idleTime = 0;
            else dropEquipmentAndDespawn();
        }
        else
        {
            Entity entity = this.world.getClosestPlayerToEntity(this, -1.0D);
            if (entity != null)
            {
                double d0 = entity.posX - this.posX;
                double d1 = entity.posY - this.posY;
                double d2 = entity.posZ - this.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (this.canDespawn() && d3 > 16384.0D) dropEquipmentAndDespawn();
                if (this.idleTime > 600 && this.rand.nextInt(800) == 0 && d3 > 1024.0D && this.canDespawn()) dropEquipmentAndDespawn();
                else if (d3 < 1024.0D) this.idleTime = 0;
            }
        }
    }

    public void dropEquipmentAndDespawn()
    {
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTMobDespawn ::: Despawn entity");
        if (this.pickedItems)
        {
            for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values())
            {
                ItemStack itemstack = this.getItemStackFromSlot(entityequipmentslot);
                if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) this.entityDropItem(itemstack, 0.0F);
            }
        }
        this.setDead();
    }
}