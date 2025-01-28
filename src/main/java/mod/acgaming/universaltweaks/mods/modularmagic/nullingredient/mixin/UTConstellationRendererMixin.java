package mod.acgaming.universaltweaks.mods.modularmagic.nullingredient.mixin;

import net.minecraft.client.Minecraft;

import fr.frinn.modularmagic.common.integration.jei.ingredient.Constellation;
import fr.frinn.modularmagic.common.integration.jei.render.ConstellationRenderer;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// Courtesy of WaitingIdly
@Mixin(value = ConstellationRenderer.class, remap = false)
public abstract class UTConstellationRendererMixin
{
    @Inject(method = "render(Lnet/minecraft/client/Minecraft;IILfr/frinn/modularmagic/common/integration/jei/ingredient/Constellation;)V", at = @At("HEAD"), cancellable = true)
    private void utCheckIngredientNotNull(Minecraft minecraft, int xPosition, int yPosition, Constellation ingredient, CallbackInfo ci)
    {
        if (UTConfigMods.MODULAR_MAGIC.utEnsureIngredientNotNull && ingredient == null) ci.cancel();
    }
}
