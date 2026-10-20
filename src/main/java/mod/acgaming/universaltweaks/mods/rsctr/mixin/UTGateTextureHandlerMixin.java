package mod.acgaming.universaltweaks.mods.rsctr.mixin;

import java.awt.image.BufferedImage;
import java.io.IOException;

import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResource;

import cd4017be.lib.render.RectangularSprite;
import cd4017be.rscpl.gui.GateTextureHandler;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = GateTextureHandler.class, remap = false)
public class UTGateTextureHandlerMixin
{
    @Redirect(method = "registerSprites", remap = true, at = @At(value = "NEW", target = "cd4017be/lib/render/RectangularSprite", remap = false))
    private static RectangularSprite utRectangularSprite(String spriteName)
    {
        return new RectangularSprite(spriteName)
        {
            // Same behavior as vanilla, but no frames
            @Override
            public void loadSpriteFrames(@NotNull IResource resource, int mipmaplevels) throws IOException
            {
                BufferedImage image = TextureUtil.readBufferedImage(resource.getInputStream());

                this.width = image.getWidth();
                this.height = image.getHeight();

                int[][] levels = new int[mipmaplevels][];
                levels[0] = new int[this.width * this.height];
                image.getRGB(0, 0, this.width, this.height, levels[0], 0, this.width);

                this.framesTextureData.clear();
                this.framesTextureData.add(levels);
            }
        };
    }
}