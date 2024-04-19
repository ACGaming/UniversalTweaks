package mod.acgaming.universaltweaks.mods.astralsorcery.mixin;

import org.apache.logging.log4j.Logger;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import hellfirepvp.astralsorcery.common.data.research.ResearchManager;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = ResearchManager.class, remap = false)
public abstract class UTResearchManagerMixin
{
    @WrapOperation(method = {"informCraftingInfusionCompletion", "informCraftingAltarCompletion"}, at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;)V"))
    private static void utInformCraftingInfusionCompletion(Logger logger, String log, Operation<Void> original)
    {
        if (!UTConfigMods.ASTRAL_SORCERY.utMissingPlayerLogLevelDowngrade) original.call(logger, log);
        else logger.debug(log);
    }
}
