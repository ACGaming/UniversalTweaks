package mod.acgaming.universaltweaks.mods.tconstruct.toolcustomization.plustic.mixin;

import landmaster.plustic.tools.ToolKatana;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of jchung01
@Mixin(value = ToolKatana.class, remap = false)
public class UTToolKatanaMixin
{
    @Inject(method = "damageCutoff", at = @At(value = "RETURN"), cancellable = true)
    private void utModifyDamageCutoff(CallbackInfoReturnable<Float> cir)
    {
        cir.setReturnValue(UTConfigMods.TINKERS_CONSTRUCT.TOOL_CUSTOMIZATION.utTConToolKatanaDamageCutoff);
    }
}
