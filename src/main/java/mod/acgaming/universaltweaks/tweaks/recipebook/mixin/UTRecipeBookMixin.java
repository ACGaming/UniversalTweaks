package mod.acgaming.universaltweaks.tweaks.recipebook.mixin;

import net.minecraft.client.gui.recipebook.GuiRecipeBook;
import net.minecraft.inventory.InventoryCrafting;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiRecipeBook.class)
public abstract class UTRecipeBookMixin
{
    @Inject(method = "initVisuals", at = @At(value = "TAIL"))
    public void utHideRecipeBook(boolean p_193014_1_, InventoryCrafting p_193014_2_, CallbackInfo ci)
    {
        if (!UTConfig.tweaks.utRecipeBookToggle) return;
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRecipeBookMixin ::: Initialize visuals");
        setVisible(false);
    }

    @Shadow
    protected abstract void setVisible(boolean p_193006_1_);
}