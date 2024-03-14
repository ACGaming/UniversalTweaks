package mod.acgaming.universaltweaks.tweaks.misc.recipebook.mixin;

import net.minecraft.client.gui.GuiButtonImage;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.util.ResourceLocation;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import mod.acgaming.universaltweaks.tweaks.misc.recipebook.UTRecipeBookPatchouliCompat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiCrafting.class)
public class UTRecipeBookCraftMixin
{
    @Shadow
    private GuiButtonImage recipeButton;

    @Redirect(method = "initGui", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/inventory/GuiCrafting;recipeButton:Lnet/minecraft/client/gui/GuiButtonImage;", ordinal = 0))
    public void utHideRecipeBook(GuiCrafting instance, GuiButtonImage value)
    {
        if (!UTConfigTweaks.MISC.utRecipeBookToggle || UTRecipeBookPatchouliCompat.patchouliInventoryButtonBook())
        {
            this.recipeButton = value;
            return;
        }
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRecipeBookCraft ::: Initialize GUI");
        this.recipeButton = new GuiButtonImage(10, 0, 0, 0, 0, 0, 0, 0, new ResourceLocation("textures/gui/container/crafting_table.png"));
    }
}