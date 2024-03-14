package mod.acgaming.universaltweaks.tweaks.misc.recipebook.mixin;

import net.minecraft.client.gui.recipebook.GuiRecipeBook;
import net.minecraft.inventory.InventoryCrafting;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.misc.recipebook.UTRecipeBookPatchouliCompat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiRecipeBook.class)
public abstract class UTRecipeBookGUIMixin
{
    @Inject(method = "initVisuals", at = @At(value = "TAIL"))
    public void utHideRecipeBook(boolean p_193014_1_, InventoryCrafting p_193014_2_, CallbackInfo ci)
    {
        if (!UTConfigTweaks.MISC.utRecipeBookToggle || UTRecipeBookPatchouliCompat.patchouliInventoryButtonBook()) return;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRecipeBookGUI ::: Initialize visuals");
        setVisible(false);
    }

    @Shadow
    protected abstract void setVisible(boolean p_193006_1_);
}