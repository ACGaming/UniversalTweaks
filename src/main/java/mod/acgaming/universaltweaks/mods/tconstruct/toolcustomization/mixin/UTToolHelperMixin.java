package mod.acgaming.universaltweaks.mods.tconstruct.toolcustomization.mixin;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import slimeknights.tconstruct.library.utils.ToolHelper;

// Courtesy of jchung01
@Mixin(value = ToolHelper.class, remap = false)
public class UTToolHelperMixin
{
    @ModifyConstant(method = "calcCutoffDamage", constant = @Constant(floatValue = 0.9f))
    private static float utModifyDecayRate(float original)
    {
        try
        {
            float newDecayRate = Float.parseFloat(UTConfigMods.TINKERS_CONSTRUCT.TOOL_CUSTOMIZATION.utTConToolDamageDecayRate);
            if (newDecayRate < 0.0f || newDecayRate > 1.0f)
            {
                if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.info("UTToolHelperMixin ::: Config's 'Attack Damage Decay Rate' is not in the valid range, must be a value from 0.0 to 1.0. Falling back to default value.");
                return original;
            }
            return newDecayRate;
        }
        catch (NumberFormatException e)
        {
            UniversalTweaks.LOGGER.error("UTToolHelperMixin ::: Could not parse a float from config's 'Attack Damage Decay Rate', must be a value from 0.0 to 1.0. Falling back to default value.");
            return original;
        }
    }
}
