package mod.acgaming.universaltweaks.tweaks.misc.gui.mixin;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Potion.class)
public class UTPotionDurationMixin
{
    @Redirect(method = "getPotionDurationString", at = @At(value = "INVOKE", target = "Lnet/minecraft/potion/PotionEffect;getIsPotionDurationMax()Z"))
    private static boolean utPotionDuration(PotionEffect effect)
    {
        return effect.getIsPotionDurationMax() && !UTConfigTweaks.MISC.utPotionDurationToggle;
    }
}