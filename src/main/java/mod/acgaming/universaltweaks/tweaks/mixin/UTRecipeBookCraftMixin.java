package mod.acgaming.universaltweaks.tweaks.mixin;

import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.recipebook.GuiRecipeBook;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiCrafting.class)
public class UTRecipeBookCraftMixin
{
    @Shadow
    @Final
    private GuiRecipeBook recipeBookGui;

    @Inject(method = "initGui", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"), cancellable = true)
    public void utLoadFallDistance(CallbackInfo ci)
    {
        if (!UTConfig.tweaks.utRecipeBookToggle) return;
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRecipeBookCraftMixin ::: Initialize GUI");
        ci.cancel();
    }
}