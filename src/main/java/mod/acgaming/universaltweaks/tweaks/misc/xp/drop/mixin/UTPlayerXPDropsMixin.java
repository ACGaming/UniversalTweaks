package mod.acgaming.universaltweaks.tweaks.misc.xp.drop.mixin;

import net.minecraft.entity.player.EntityPlayer;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityPlayer.class)
public abstract class UTPlayerXPDropsMixin
{
    @Shadow
    public int experienceTotal;

    @Inject(method = "getExperiencePoints", at = @At(value = "RETURN", ordinal = 0), cancellable = true)
    public void utPlayerXPDrops(EntityPlayer player, CallbackInfoReturnable<Integer> cir)
    {
        cir.setReturnValue((int) (this.experienceTotal * UTConfigTweaks.MISC.utPlayerXPDropPercentage));
    }
}
