package mod.acgaming.universaltweaks.tweaks.performance.advancements.logging.mixin;

import org.apache.logging.log4j.Logger;
import net.minecraft.advancements.AdvancementManager;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of fonnymunkey
@Mixin(AdvancementManager.class)
public abstract class UTAdvancementManagerMixin
{
    @WrapWithCondition(
        method = {"loadCustomAdvancements", "loadBuiltInAdvancements"},
        at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;)V",
            remap = false)
    )
    public boolean utLogErrorsIfAllowed0(Logger instance, String s)
    {
        return !UTConfigTweaks.PERFORMANCE.utAdvancementCheckToggle;
    }

    @WrapWithCondition(
        method = {"loadCustomAdvancements", "loadBuiltInAdvancements"},
        at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Throwable;)V",
            remap = false)
    )
    public boolean utLogErrorsIfAllowed1(Logger instance, String s, Throwable throwable)
    {
        return !UTConfigTweaks.PERFORMANCE.utAdvancementCheckToggle;
    }
}