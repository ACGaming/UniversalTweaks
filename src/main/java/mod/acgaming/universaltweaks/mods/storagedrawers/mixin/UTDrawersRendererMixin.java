package mod.acgaming.universaltweaks.mods.storagedrawers.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import com.jaquadro.minecraft.storagedrawers.block.tile.TileEntityDrawers;
import com.jaquadro.minecraft.storagedrawers.client.renderer.TileEntityDrawersRenderer;
import mod.acgaming.universaltweaks.config.UTConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TileEntityDrawersRenderer.class, remap = false)
public class UTDrawersRendererMixin
{
    @Inject(method = "render(Lcom/jaquadro/minecraft/storagedrawers/block/tile/TileEntityDrawers;DDDFIF)V", at = @At(value = "HEAD"), cancellable = true)
    public void utDrawersRenderer(TileEntityDrawers tile, double x, double y, double z, float partialTickTime, int destroyStage, float par7, CallbackInfo ci)
    {
        if (UTConfig.mods.utSDRenderRange < 1) return;
        EntityPlayer player = Minecraft.getMinecraft().player;
        if (player != null && player.getPosition().distanceSq(tile.getPos()) > UTConfig.mods.utSDRenderRange * UTConfig.mods.utSDRenderRange) ci.cancel();
    }
}