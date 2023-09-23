package mod.acgaming.universaltweaks.tweaks.misc.xp.linear.mixin;

import net.minecraft.entity.player.EntityPlayer;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public class UTLinearXPMixin
{
    @Inject(method = "xpBarCap", at = @At("HEAD"), cancellable = true)
    public void utLinearXP(CallbackInfoReturnable<Integer> cir)
    {
        if (UTConfigTweaks.MISC.utLinearXP < 1) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTLinearXP ::: XP bar cap");
        cir.setReturnValue(UTConfigTweaks.MISC.utLinearXP);
    }
}