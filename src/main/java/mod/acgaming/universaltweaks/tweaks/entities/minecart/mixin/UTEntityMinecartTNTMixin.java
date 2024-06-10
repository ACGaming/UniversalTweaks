package mod.acgaming.universaltweaks.tweaks.entities.minecart.mixin;

import net.minecraft.entity.item.*;
import net.minecraft.item.ItemStack;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of WaitingIdly
@Mixin(value = EntityMinecartTNT.class)
public abstract class UTEntityMinecartTNTMixin
{
    @WrapOperation(method = "killMinecart", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityMinecartTNT;entityDropItem(Lnet/minecraft/item/ItemStack;F)Lnet/minecraft/entity/item/EntityItem;"))
    private EntityItem utDropMinecartType(EntityMinecartTNT instance, ItemStack item, float offsetY, Operation<EntityItem> original)
    {
        if (!UTConfigTweaks.ENTITIES.utMinecartDropsType)
        {
            return original.call(instance, item, offsetY);
        }
        ItemStack itemstack = instance.getCartItem().copy();
        if (instance.hasCustomName()) itemstack.setStackDisplayName(instance.getCustomNameTag());
        return instance.entityDropItem(itemstack, offsetY);
    }
}