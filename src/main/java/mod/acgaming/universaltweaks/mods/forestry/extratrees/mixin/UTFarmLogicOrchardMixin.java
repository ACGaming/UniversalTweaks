package mod.acgaming.universaltweaks.mods.forestry.extratrees.mixin;

import net.minecraft.item.ItemStack;

import binnie.extratrees.items.ItemETFood;
import forestry.api.farming.IFarmProperties;
import forestry.farming.logic.FarmLogic;
import forestry.farming.logic.FarmLogicOrchard;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FarmLogicOrchard.class, remap = false)
public abstract class UTFarmLogicOrchardMixin extends FarmLogic
{
    public UTFarmLogicOrchardMixin(IFarmProperties properties, boolean isManual)
    {
        super(properties, isManual);
    }

    @Inject(method = "isAcceptedWindfall", at = @At("RETURN"), cancellable = true)
    public void utExtraTreesWindfall(ItemStack stack, CallbackInfoReturnable<Boolean> cir)
    {
        if (UTConfigMods.FORESTRY.utFOGatherWindfallToggle && stack.getItem() instanceof ItemETFood) cir.setReturnValue(true);
    }
}