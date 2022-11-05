package mod.acgaming.universaltweaks.bugfixes.hashcode.mixin;

import java.util.Map;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.IStringSerializable;

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
@Mixin(PropertyEnum.class)
public abstract class UTPropertyEnumMixin<T extends Enum<T> & IStringSerializable>
{
    @Unique
    public int cachedHashCode;
    @Shadow
    @Final
    private ImmutableSet<T> allowedValues;
    @Shadow
    @Final
    private Map<String, T> nameToValue;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void utCalculateCachedHashCode(CallbackInfo ci)
    {
        if (!UTConfig.bugfixes.utHashCodeToggle || UniversalTweaks.foamfixLoaded) return;
        //if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTPropertyEnumMixin ::: Initialize");
        int i = super.hashCode();
        i = 31 * i + this.allowedValues.hashCode();
        i = 31 * i + this.nameToValue.hashCode();
        this.cachedHashCode = i;
    }

    @Inject(method = "hashCode", at = @At("HEAD"), cancellable = true)
    public void utCachedHashCode(CallbackInfoReturnable<Integer> ci)
    {
        if (!UTConfig.bugfixes.utHashCodeToggle || UniversalTweaks.foamfixLoaded) return;
        //if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTPropertyEnumMixin ::: Hash code");
        ci.setReturnValue(this.cachedHashCode);
    }
}