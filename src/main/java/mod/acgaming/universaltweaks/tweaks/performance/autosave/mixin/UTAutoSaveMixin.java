package mod.acgaming.universaltweaks.tweaks.performance.autosave.mixin;

import net.minecraft.server.MinecraftServer;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class UTAutoSaveMixin
{
    @ModifyConstant(method = "tick", constant = @Constant(intValue = 900))
    public int utAutoSave(int constant)
    {
        return UTConfigTweaks.PERFORMANCE.utAutoSaveInterval;
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/profiler/Profiler;endSection()V", ordinal = 0))
    public void utAutoSaveDebug(CallbackInfo ci)
    {
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTAutoSave ::: World saved, next save in {} ticks", UTConfigTweaks.PERFORMANCE.utAutoSaveInterval);
    }
}