package mod.acgaming.universaltweaks.tweaks.items.eating.mixin;

import net.minecraft.entity.player.EntityPlayer;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public class UTAlwaysEatMixin
{
    @Inject(method = "canEat", at = @At(value = "RETURN"), cancellable = true)
    public void utAlwaysEat(boolean ignoreHunger, CallbackInfoReturnable<Boolean> cir)
    {
        if (UTConfigTweaks.ITEMS.utAlwaysEatToggle) cir.setReturnValue(true);
    }
}