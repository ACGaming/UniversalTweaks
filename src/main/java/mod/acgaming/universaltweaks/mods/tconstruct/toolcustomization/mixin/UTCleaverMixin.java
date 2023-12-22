package mod.acgaming.universaltweaks.mods.tconstruct.toolcustomization.mixin;

import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.tools.melee.item.Cleaver;

// Courtesy of jchung01
@Mixin(value = Cleaver.class, remap = false)
public class UTCleaverMixin
{
    @Inject(method = "damageCutoff", at = @At(value = "RETURN"), cancellable = true)
    private void utModifyDamageCutoff(CallbackInfoReturnable<Float> cir)
    {
        cir.setReturnValue(UTConfigMods.TINKERS_CONSTRUCT.TOOL_CUSTOMIZATION.utTConToolCleaverDamageCutoff);
    }
}
