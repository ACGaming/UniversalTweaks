package mod.acgaming.universaltweaks.tweaks.misc.hotbarscroll.mixin;

import net.minecraft.entity.player.InventoryPlayer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of WaitingIdly
@Mixin(InventoryPlayer.class)
public abstract class UTInventoryPlayerMixin
{
    @Shadow
    public int currentItem;

    @Inject(method = "changeCurrentItem", at = @At("HEAD"), cancellable = true)
    private void utCancelOnRollover(int direction, CallbackInfo ci)
    {
        if (!UTConfigTweaks.MISC.utDisableHotbarScrollWrapping) return;
        int target = currentItem - (int) Math.signum(direction);
        if (target < 0 || target >= InventoryPlayer.getHotbarSize())
        {
            ci.cancel();
        }
    }
}