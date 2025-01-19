package mod.acgaming.universaltweaks.mods.modularmagic.nullingredient.mixin;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;

import fr.frinn.modularmagic.common.integration.jei.render.AspectRenderer;
import mod.acgaming.universaltweaks.config.UTConfigMods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import thaumcraft.api.aspects.AspectList;

// Courtesy of WaitingIdly
@Mixin(value = AspectRenderer.class, remap = false)
public abstract class UTAspectRendererMixin
{
    @Inject(method = "render(Lnet/minecraft/client/Minecraft;IILthaumcraft/api/aspects/AspectList;)V", at = @At("HEAD"), cancellable = true)
    private void utCheckIngredientNotNull(Minecraft minecraft, int xPosition, int yPosition, @Nullable AspectList ingredient, CallbackInfo ci)
    {
        if (UTConfigMods.MODULAR_MAGIC.utEnsureIngredientNotNull && ingredient == null) ci.cancel();
    }
}
