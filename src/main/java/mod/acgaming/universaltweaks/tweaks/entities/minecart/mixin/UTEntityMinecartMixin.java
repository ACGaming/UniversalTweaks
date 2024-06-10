package mod.acgaming.universaltweaks.tweaks.entities.minecart.mixin;

import net.minecraft.entity.item.*;
import net.minecraft.item.ItemStack;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of WaitingIdly
@Mixin(value = EntityMinecart.class)
public abstract class UTEntityMinecartMixin
{
    @WrapWithCondition(method = "killMinecart", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityMinecart;entityDropItem(Lnet/minecraft/item/ItemStack;F)Lnet/minecraft/entity/item/EntityItem;"))
    private boolean utPreventDroppingNormalMinecart(EntityMinecart instance, ItemStack item, float y)
    {
        return !UTConfigTweaks.ENTITIES.utMinecartDropsType || !(instance instanceof EntityMinecartFurnace || instance instanceof EntityMinecartChest || instance instanceof EntityMinecartTNT || instance instanceof EntityMinecartHopper);
    }
}