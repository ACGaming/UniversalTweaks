package mod.acgaming.universaltweaks.tweaks.misc.recipebook.compat.mixin;

import buildcraft.lib.gui.recipe.GuiRecipeBookPhantom;
import buildcraft.silicon.container.ContainerAdvancedCraftingTable;
import buildcraft.silicon.gui.GuiAdvancedCraftingTable;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiAdvancedCraftingTable.class, remap = false)
public abstract class UTRecipeBookBuildcraftCompatMixin
{
    @Mutable
    @Shadow
    @Final
    private GuiRecipeBookPhantom recipeBook;

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void utHideRecipeBookAdvCraftingTable(ContainerAdvancedCraftingTable container, CallbackInfo ci)
    {
        if (UTConfigTweaks.MISC.utRecipeBookToggle)
        {
            this.recipeBook = null;
        }
    }
}