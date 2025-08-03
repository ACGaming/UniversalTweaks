package mod.acgaming.universaltweaks.tweaks.performance.textureatlas.mixin;

import org.lwjgl.opengl.GL11;
import net.minecraftforge.fml.client.SplashProgress;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfigGeneral;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

// Courtesy of Desoroxxx
@Mixin(value = SplashProgress.class, remap = false)
public abstract class UTTextureAtlasMixin
{
    @Shadow
    private static int max_texture_size;

    /**
     * @reason Actually get the max texture size the OpenGL implementation supports, potentially allowing for bigger atlases on certain GPUs.
     * @author Desoroxxx
     */
    @Overwrite
    public static int getMaxTextureSize()
    {
        if (max_texture_size != -1) return max_texture_size;
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("Max texture size isn't cached, getting it and caching...");
        max_texture_size = GL11.glGetInteger(GL11.GL_MAX_TEXTURE_SIZE);
        if (UTConfigGeneral.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("Max texture size is {}", max_texture_size);
        return max_texture_size;
    }
}
