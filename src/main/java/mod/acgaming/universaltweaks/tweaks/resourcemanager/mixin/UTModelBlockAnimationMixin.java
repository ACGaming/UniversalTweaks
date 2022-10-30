package mod.acgaming.universaltweaks.tweaks.resourcemanager.mixin;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.animation.ModelBlockAnimation;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
import mod.acgaming.universaltweaks.tweaks.resourcemanager.ICheckableResourceManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of Darkhax
@Mixin(ModelBlockAnimation.class)
public class UTModelBlockAnimationMixin
{
    @Final
    @Shadow(remap = false)
    private static ModelBlockAnimation defaultModelBlockAnimation;

    @Inject(method = "loadVanillaAnimation(Lnet/minecraft/client/resources/IResourceManager;Lnet/minecraft/util/ResourceLocation;)Lnet/minecraftforge/client/model/animation/ModelBlockAnimation;", at = @At("HEAD"), cancellable = true, remap = false)
    private static void utLoadVanillaAnimation(IResourceManager manager, ResourceLocation location, CallbackInfoReturnable<ModelBlockAnimation> info)
    {
        if (UTConfig.debug.utDebugToggle) UniversalTweaks.LOGGER.debug("UTModelBlockAnimationMixin ::: Load vanilla animation");
        if (manager instanceof ICheckableResourceManager && !((ICheckableResourceManager) manager).hasResource(location))
        {
            info.setReturnValue(defaultModelBlockAnimation);
        }
    }
}