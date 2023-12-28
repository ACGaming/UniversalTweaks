package mod.acgaming.universaltweaks.mods.tconstruct.toolcustomization.mixin;

import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.tools.ToolCore;

// Courtesy of jchung01
@Mixin(value = ToolCore.class, remap = false)
public class UTToolCoreMixin
{
    @Inject(method = "damageCutoff", at = @At(value = "RETURN"), cancellable = true)
    private void utModifyDamageCutoff(CallbackInfoReturnable<Float> cir)
    {
        cir.setReturnValue(UTConfigMods.TINKERS_CONSTRUCT.TOOL_CUSTOMIZATION.utTConToolGeneralDamageCutoff);
    }
}
