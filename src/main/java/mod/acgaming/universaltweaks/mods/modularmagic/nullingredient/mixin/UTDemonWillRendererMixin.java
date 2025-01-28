package mod.acgaming.universaltweaks.mods.modularmagic.nullingredient.mixin;

import net.minecraft.client.Minecraft;

import fr.frinn.modularmagic.common.integration.jei.ingredient.DemonWill;
import fr.frinn.modularmagic.common.integration.jei.render.DemonWillRenderer;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of WaitingIdly
@Mixin(value = DemonWillRenderer.class, remap = false)
public abstract class UTDemonWillRendererMixin
{
    // This method actually has a null check, the issue is that it makes changes to the GLState prior to that null check.
    @Inject(method = "render(Lnet/minecraft/client/Minecraft;IILfr/frinn/modularmagic/common/integration/jei/ingredient/DemonWill;)V", at = @At("HEAD"), cancellable = true)
    private void utCheckIngredientNotNull(Minecraft minecraft, int xPosition, int yPosition, DemonWill ingredient, CallbackInfo ci)
    {
        if (UTConfigMods.MODULAR_MAGIC.utEnsureIngredientNotNull && ingredient == null) ci.cancel();
    }
}
