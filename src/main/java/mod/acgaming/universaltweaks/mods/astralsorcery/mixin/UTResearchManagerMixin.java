package mod.acgaming.universaltweaks.mods.astralsorcery.mixin;

import hellfirepvp.astralsorcery.common.data.research.ResearchManager;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// Courtesy of WaitingIdly
@Mixin(value = ResearchManager.class, remap = false)
public abstract class UTResearchManagerMixin
{
    @Redirect(method = {"informCraftingInfusionCompletion", "informCraftingAltarCompletion"}, at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;)V"))
    private static void utInformCraftingInfusionCompletion(Logger logger, String log)
    {
        if (!UTConfigMods.ASTRAL_SORCERY.utMissingPlayerLogLevelDowngrade) return;
        logger.debug(log);
    }
}
