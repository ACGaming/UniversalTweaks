package mod.acgaming.hkntweaks.tweaks.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of frikinjay
@Mixin(EntityLiving.class)
public abstract class HkNMobDespawnMixin extends EntityLivingBase
{
    public boolean pickedItems = false;
    @Shadow
    private boolean persistenceRequired;

    public HkNMobDespawnMixin(World worldIn)
    {
        super(worldIn);
    }

    @Inject(at = @At("TAIL"), method = "updateEquipmentIfNeeded")
    public void hknUpdateEquipmentIfNeeded(CallbackInfo info)
    {
        this.pickedItems = true;
        this.persistenceRequired = this.hasCustomName();
    }

    @Redirect(method = "despawnEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLiving;setDead()V"))
    public void hknDespawnEntity(EntityLiving instance)
    {
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