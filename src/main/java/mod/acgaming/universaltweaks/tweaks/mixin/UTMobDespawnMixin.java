package mod.acgaming.universaltweaks.tweaks.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of frikinjay
@Mixin(EntityLiving.class)
public abstract class UTMobDespawnMixin extends EntityLivingBase
{
    public boolean pickedItems = false;
    @Shadow
    private boolean persistenceRequired;

    public UTMobDespawnMixin(World worldIn)
    {
        super(worldIn);
    }

    @Inject(at = @At("TAIL"), method = "updateEquipmentIfNeeded")
    public void utUpdateEquipmentIfNeeded(CallbackInfo info)
    {
        if (!UTConfig.TWEAKS_ENTITIES.utMobDespawnToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTMobDespawn ::: Update equipment");
        this.pickedItems = true;
        this.persistenceRequired = this.hasCustomName();
    }

    @Redirect(method = "despawnEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLiving;setDead()V"))
    public void utDespawnEntity(EntityLiving instance)
    {
        if (!UTConfig.TWEAKS_ENTITIES.utMobDespawnToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTMobDespawn ::: Despawn entity");
        if (this.pickedItems) this.dropEquipmentOnDespawn();
        this.setDead();
    }

    public void dropEquipmentOnDespawn()
    {
        for (EntityEquipmentSlot entityequipmentslot : EntityEquipmentSlot.values())
        {
            ItemStack itemstack = this.getItemStackFromSlot(entityequipmentslot);
            if (!itemstack.isEmpty() && !EnchantmentHelper.hasVanishingCurse(itemstack)) this.entityDropItem(itemstack, 0.0F);
        }
    }
}