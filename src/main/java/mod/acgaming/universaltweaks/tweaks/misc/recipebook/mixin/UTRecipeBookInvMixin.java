package mod.acgaming.universaltweaks.tweaks.misc.recipebook.mixin;

import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.util.ResourceLocation;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.misc.recipebook.compat.UTRecipeBookPatchouliCompat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiInventory.class)
public class UTRecipeBookInvMixin
{
    @Shadow
    private GuiButtonImage recipeButton;

    @Redirect(method = "initGui", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/inventory/GuiInventory;recipeButton:Lnet/minecraft/client/gui/GuiButtonImage;", ordinal = 0))
    public void utHideRecipeBook(GuiInventory instance, GuiButtonImage value)
    {
        if (!UTConfigTweaks.MISC.utRecipeBookToggle || UTRecipeBookPatchouliCompat.patchouliInventoryButtonBook())
        {
            this.recipeButton = value;
            return;
        }
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRecipeBookInv ::: Initialize GUI");
        this.recipeButton = new GuiButtonImage(10, 0, 0, 0, 0, 0, 0, 0, new ResourceLocation("textures/gui/container/inventory.png"));
    }
}