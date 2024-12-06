package mod.acgaming.universaltweaks.tweaks.items.eating.mixin;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

import com.llamalad7.mixinextras.sugar.Local;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemFood.class)
public abstract class UTSmartEatMixin
{
    @Shadow
    public boolean alwaysEdible;

    @Shadow
    public abstract int getHealAmount(ItemStack stack);

    @Redirect(method = "onItemRightClick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/EntityPlayer;canEat(Z)Z"))
    public boolean utSmartEat(EntityPlayer player, boolean ignoreHunger, @Local ItemStack itemstack)
    {
        if (!UTConfigTweaks.ITEMS.utSmartEatToggle || this.alwaysEdible) return player.canEat(true);
        return player.canEat(false) && this.getHealAmount(itemstack) <= (20 - player.getFoodStats().getFoodLevel());
    }
}