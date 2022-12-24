package mod.acgaming.universaltweaks.bugfixes.mixin;

import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of fonnymunkey
@Mixin(EntityItemFrame.class)
public abstract class UTItemFrameVoidMixin
{
    @Inject(method = "processInitialInteract", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/item/EntityItemFrame;setDisplayedItem(Lnet/minecraft/item/ItemStack;)V"), cancellable = true)
    public void utItemFrameVoid(EntityPlayer player, EnumHand hand, CallbackInfoReturnable<Boolean> cir)
    {
        if (UTConfig.bugfixes.utItemFrameVoidToggle && ((EntityItemFrame) (Object) this).isDead)
        {
            if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTItemFrameVoid ::: Process initial interact");
            cir.setReturnValue(false);
        }
    }
}