package mod.acgaming.universaltweaks.tweaks.entities.minecart.mixin;

import net.minecraft.entity.item.*;
import net.minecraft.item.ItemStack;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of WaitingIdly
@Mixin(value = EntityMinecart.class)
public abstract class UTEntityMinecartMixin
{
    @WrapOperation(method = "killMinecart", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityMinecart;entityDropItem(Lnet/minecraft/item/ItemStack;F)Lnet/minecraft/entity/item/EntityItem;"))
    private EntityItem utDropMinecartType(EntityMinecart instance, ItemStack item, float y, Operation<EntityItem> original)
    {
        if (!UTConfigTweaks.ENTITIES.utMinecartDropsType || !(instance instanceof EntityMinecartFurnace || instance instanceof EntityMinecartChest || instance instanceof EntityMinecartTNT || instance instanceof EntityMinecartHopper))
        {
            return original.call(instance, instance, y);
        }
        ItemStack itemstack = instance.getCartItem().copy();
        if (instance.hasCustomName()) itemstack.setStackDisplayName(instance.getCustomNameTag());
        return original.call(instance, itemstack, y);
    }
}