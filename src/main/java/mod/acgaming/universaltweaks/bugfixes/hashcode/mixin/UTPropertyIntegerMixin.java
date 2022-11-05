package mod.acgaming.universaltweaks.bugfixes.hashcode.mixin;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.properties.PropertyInteger;

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
@Mixin(PropertyInteger.class)
public abstract class UTPropertyIntegerMixin
{
    @Unique
    public int cachedHashCode;
    @Shadow
    @Final
    private ImmutableSet<Integer> allowedValues;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void utCalculateCachedHashCode(CallbackInfo ci)
    {
        if (!UTConfig.bugfixes.utHashCodeToggle) return;
        //if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTPropertyIntegerMixin ::: Initialize");
        this.cachedHashCode = 31 * super.hashCode() + this.allowedValues.hashCode();
    }

    @Inject(method = "hashCode", at = @At("HEAD"), cancellable = true)
    public void utCachedHashCode(CallbackInfoReturnable<Integer> ci)
    {
        if (!UTConfig.bugfixes.utHashCodeToggle) return;
        //if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTPropertyIntegerMixin ::: Hash code");
        ci.setReturnValue(this.cachedHashCode);
    }
}