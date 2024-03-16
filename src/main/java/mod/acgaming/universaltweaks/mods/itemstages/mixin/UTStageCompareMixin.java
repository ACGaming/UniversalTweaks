package mod.acgaming.universaltweaks.mods.itemstages.mixin;

import net.minecraft.item.ItemStack;

import crafttweaker.api.minecraft.CraftTweakerMC;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import net.darkhax.itemstages.StageCompare;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = StageCompare.class, remap = false)
public class UTStageCompareMixin
{
    @Inject(method = "isValid", at = @At("HEAD"), cancellable = true)
    public void utChangeToCrTIngredient(ItemStack entryStack, Object second, CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfigMods.ITEM_STAGES.utIngredientMatching) return;
        if (second instanceof ItemStack)
        {
            cir.setReturnValue(CraftTweakerMC.getIItemStackForMatching(((ItemStack) second)).matches(CraftTweakerMC.getIItemStackForMatching(entryStack)));
        }
    }
}
