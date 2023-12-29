package mod.acgaming.universaltweaks.tweaks.misc.xp.cap.mixin;

import net.minecraft.entity.player.EntityPlayer;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayer.class)
public class UTXPCapMixin
{
    @Shadow
    public int experienceLevel;

    @Inject(method = "addExperience", at = @At("HEAD"), cancellable = true)
    public void utXPCap(int amount, CallbackInfo ci)
    {
        if (UTConfigTweaks.MISC.utXPLevelCap < 0) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTXPCap ::: Add experience");
        if (this.experienceLevel >= UTConfigTweaks.MISC.utXPLevelCap)
        {
            this.experienceLevel = UTConfigTweaks.MISC.utXPLevelCap;
            if (amount > 0) ci.cancel();
        }
    }

    @Inject(method = "addExperienceLevel", at = @At("HEAD"), cancellable = true)
    public void utXPCapLevel(int levels, CallbackInfo ci)
    {
        if (UTConfigTweaks.MISC.utXPLevelCap < 0) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTXPCap ::: Add experience level");
        if (this.experienceLevel >= UTConfigTweaks.MISC.utXPLevelCap)
        {
            this.experienceLevel = UTConfigTweaks.MISC.utXPLevelCap;
            if (levels > 0) ci.cancel();
        }
    }
}