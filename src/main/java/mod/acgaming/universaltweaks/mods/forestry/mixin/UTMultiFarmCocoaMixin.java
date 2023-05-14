package mod.acgaming.universaltweaks.mods.forestry.mixin;

import forestry.api.farming.IFarmable;
import forestry.farming.logic.FarmLogicCocoa;
import mod.acgaming.universaltweaks.mods.forestry.UTFarmableCocoa;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FarmLogicCocoa.class)
public class UTMultiFarmCocoaMixin
{
    @Mutable
    @Shadow
    @Final
    private IFarmable cocoa;

    @Redirect(method = "<init>", at = @At(value = "FIELD", target = "Lforestry/farming/logic/FarmLogicCocoa;cocoa:Lforestry/api/farming/IFarmable;"))
    public void utMultiFarmCocoa(FarmLogicCocoa instance, IFarmable value)
    {
        cocoa = new UTFarmableCocoa();
    }
}