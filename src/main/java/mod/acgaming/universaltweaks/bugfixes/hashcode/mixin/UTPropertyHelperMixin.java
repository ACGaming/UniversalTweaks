package mod.acgaming.universaltweaks.bugfixes.hashcode.mixin;

import net.minecraft.block.properties.PropertyHelper;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// MC-134989
// https://bugs.mojang.com/browse/MC-134989
// Courtesy of mrgrim
@Mixin(PropertyHelper.class)
public abstract class UTPropertyHelperMixin<T extends Comparable<T>>
{
    @Unique
    public int cachedHashCode;
    @Shadow
    @Final
    private Class<T> valueClass;
    @Shadow
    @Final
    private String name;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void utCalculateCachedHashCode(CallbackInfo ci)
    {
        if (!UTConfig.bugfixes.utHashCodeToggle || UniversalTweaks.foamfixLoaded) return;
        //if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTPropertyHelperMixin ::: Initialize");
        this.cachedHashCode = 31 * this.valueClass.hashCode() + this.name.hashCode();
    }

    @Inject(method = "hashCode", at = @At("HEAD"), cancellable = true)
    public void utCachedHashCode(CallbackInfoReturnable<Integer> ci)
    {
        if (!UTConfig.bugfixes.utHashCodeToggle || UniversalTweaks.foamfixLoaded) return;
        //if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTPropertyHelperMixin ::: Hash code");
        ci.setReturnValue(this.cachedHashCode);
    }
}