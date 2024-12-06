package mod.acgaming.universaltweaks.tweaks.performance.texturemapcheck.mixin;

import org.apache.logging.log4j.Logger;
import net.minecraft.client.renderer.texture.TextureMap;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

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