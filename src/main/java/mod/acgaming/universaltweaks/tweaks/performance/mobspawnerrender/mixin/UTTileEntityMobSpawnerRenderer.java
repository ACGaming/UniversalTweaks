package mod.acgaming.universaltweaks.tweaks.performance.mobspawnerrender.mixin;

import net.minecraft.client.renderer.tileentity.TileEntityMobSpawnerRenderer;
import net.minecraft.tileentity.TileEntityMobSpawner;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TileEntityMobSpawnerRenderer.class)
public abstract class UTTileEntityMobSpawnerRenderer
{
    @Inject(method = "render(Lnet/minecraft/tileentity/TileEntityMobSpawner;DDDFIF)V", at = @At("HEAD"), cancellable = true)
    private void utDisableEntityRendering(TileEntityMobSpawner te, double x, double y, double z, float partialTicks, int destroyStage, float alpha, CallbackInfo ci)
    {
        if (!UTConfigTweaks.PERFORMANCE.utDisableMobSpawnerRendering) return;
        ci.cancel();
    }
}