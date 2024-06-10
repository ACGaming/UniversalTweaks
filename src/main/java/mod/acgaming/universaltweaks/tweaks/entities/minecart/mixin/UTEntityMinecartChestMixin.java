package mod.acgaming.universaltweaks.tweaks.entities.minecart.mixin;

import net.minecraft.entity.item.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of WaitingIdly
@Mixin(value = EntityMinecartChest.class)
public abstract class UTEntityMinecartChestMixin
{
    @WrapOperation(method = "killMinecart", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityMinecartChest;dropItemWithOffset(Lnet/minecraft/item/Item;IF)Lnet/minecraft/entity/item/EntityItem;"))
    private EntityItem utDropMinecartType(EntityMinecartChest instance, Item item, int size, float offsetY, Operation<EntityItem> original)
    {
        if (!UTConfigTweaks.ENTITIES.utMinecartDropsType)
        {
            return original.call(instance, item, size, offsetY);
        }
        ItemStack itemstack = instance.getCartItem().copy();
        if (instance.hasCustomName()) itemstack.setStackDisplayName(instance.getCustomNameTag());
        return instance.entityDropItem(itemstack, offsetY);
    }
}