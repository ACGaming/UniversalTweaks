package mod.acgaming.universaltweaks.tweaks.performance.advancementcheck.mixin;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import net.minecraftforge.common.ForgeHooks;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.WrapWithCondition;

// Courtesy of fonnymunkey
@Mixin(ForgeHooks.class)
public abstract class UTForgeHooksMixin
{
    @WrapWithCondition(
            method = "lambda$loadAdvancements$0",
            at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Throwable;)V"),
            remap = false
    )
    private static boolean utLoadAdvancements(Logger instance, String s, Throwable throwable)
    {
        return !UTConfigTweaks.PERFORMANCE.utAdvancementCheckToggle;
    }
}