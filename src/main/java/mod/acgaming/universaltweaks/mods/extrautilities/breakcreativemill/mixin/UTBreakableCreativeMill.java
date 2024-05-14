package mod.acgaming.universaltweaks.mods.extrautilities.breakcreativemill.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.rwtema.extrautils2.ExtraUtils2;
import com.rwtema.extrautils2.backend.XUBlockStatic;
import com.rwtema.extrautils2.blocks.BlockPassiveGenerator;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = BlockPassiveGenerator.class)
public abstract class UTBreakableCreativeMill extends XUBlockStatic
{
    @ModifyExpressionValue(method = "getBlockHardness", at = @At(value = "FIELD", target = "Lcom/rwtema/extrautils2/ExtraUtils2;allowCreativeBlocksToBeBroken:Z", remap = false))
    private boolean utBreakCreativeMill(boolean original)
    {
        if (!UTConfigMods.EXTRA_UTILITIES.utFixCreativeMillHarvestability) return original;
        return ExtraUtils2.allowNonCreativeHarvest;
    }
}
