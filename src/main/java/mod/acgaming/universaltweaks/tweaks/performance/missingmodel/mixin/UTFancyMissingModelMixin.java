package mod.acgaming.universaltweaks.tweaks.performance.missingmodel.mixin;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ModelLoaderRegistry.class, remap = false)
public abstract class UTFancyMissingModelMixin
{
    @Shadow
    public static IModel getMissingModel()
    {
        return null;
    }

    @Inject(method = "getMissingModel(Lnet/minecraft/util/ResourceLocation;Ljava/lang/Throwable;)Lnet/minecraftforge/client/model/IModel;", at = @At("HEAD"), cancellable = true)
    private static void utFancyMissingModel(ResourceLocation location, Throwable cause, CallbackInfoReturnable<IModel> cir)
    {
        if (!UTConfigTweaks.PERFORMANCE.utDisableFancyMissingModelToggle) return;
        cir.setReturnValue(getMissingModel());
    }
}