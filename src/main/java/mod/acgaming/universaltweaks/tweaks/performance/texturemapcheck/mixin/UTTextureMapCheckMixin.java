package mod.acgaming.universaltweaks.tweaks.performance.texturemapcheck.mixin;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import net.minecraft.client.renderer.texture.TextureMap;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.llamalad7.mixinextras.injector.WrapWithCondition;

// Courtesy of Elephant_1214
@Mixin(value = TextureMap.class, priority = 500)
public class UTTextureMapCheckMixin
{
    @WrapWithCondition(
            method = "*", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V", remap = false),
            require = 0
    )
    private boolean utCheckTexMap(Logger instance, String message, Object p0, Object p1)
    {
        return !UTConfigTweaks.PERFORMANCE.utTextureMapCheckToggle;
    }
}