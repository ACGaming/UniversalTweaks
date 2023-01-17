package mod.acgaming.universaltweaks.tweaks.recipebook.mixin;

import net.minecraft.client.gui.inventory.GuiInventory;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiInventory.class)
public class UTRecipeBookInvMixin
{
    @Inject(method = "initGui", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"), cancellable = true)
    public void utHideRecipeBook(CallbackInfo ci)
    {
        if (!UTConfig.TWEAKS_MISC.utRecipeBookToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTRecipeBookInvMixin ::: Initialize GUI");
        ci.cancel();
    }
}