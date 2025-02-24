package mod.acgaming.universaltweaks.tweaks.items.repairing.mixin;

import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingManager.class)
public class UTCraftingRepairMixin
{
    @Inject(method = "register(Ljava/lang/String;Lnet/minecraft/item/crafting/IRecipe;)V", at = @At("HEAD"), cancellable = true)
    private static void utCraftingRepair(String name, IRecipe recipe, CallbackInfo ci)
    {
        if (name.equals("repairitem"))
        {
            if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTCraftingRepair ::: Register 'repairitem'");
            ci.cancel();
        }
    }
}
