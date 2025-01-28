package mod.acgaming.universaltweaks.mods.astralsorcery.mixin;

import java.util.List;

import hellfirepvp.astralsorcery.common.item.crystal.CrystalProperties;
import hellfirepvp.astralsorcery.common.item.crystal.ToolCrystalProperties;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of WaitingIdly
@Mixin(value = ToolCrystalProperties.class, remap = false)
public abstract class UTToolCrystalPropertiesMixin
{
    @Inject(method = "merge(Ljava/util/List;)Lhellfirepvp/astralsorcery/common/item/crystal/ToolCrystalProperties;", at = @At("HEAD"), cancellable = true)
    private static void utEmptySkip(List<CrystalProperties> properties, CallbackInfoReturnable<ToolCrystalProperties> cir)
    {
        if (UTConfigMods.ASTRAL_SORCERY.utEmptyPropertiesZero && properties.isEmpty())
        {
            cir.setReturnValue(new ToolCrystalProperties(0, 0, 0, 0, -1));
        }
    }
}
