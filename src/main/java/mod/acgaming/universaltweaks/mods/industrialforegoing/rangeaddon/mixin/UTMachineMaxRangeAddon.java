package mod.acgaming.universaltweaks.mods.industrialforegoing.rangeaddon.mixin;

import com.buuz135.industrial.tile.block.CustomAreaOrientedBlock;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// Courtesy of WaitingIdly
@Mixin(value = CustomAreaOrientedBlock.class, remap = false)
public class UTMachineMaxRangeAddon
{
    @ModifyExpressionValue(method = "getTooltip", at = @At(value = "INVOKE", target = "Lcom/buuz135/industrial/tile/block/CustomAreaOrientedBlock;getMaxWidth()I"))
    private int utFixMaxRangeAddonOfByOne(int original)
    {
        if (!UTConfigMods.INDUSTRIAL_FOREGOING.utRangeAddonNumberFix) return original;
        return original + 1;
    }
}
