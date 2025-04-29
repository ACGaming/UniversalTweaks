package mod.acgaming.universaltweaks.tweaks.misc.gamewindow.mixin;

import net.minecraft.client.Minecraft;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Minecraft.class)
public abstract class UTDisplayTitleMixin
{
    @ModifyArg(method = "createDisplay", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V", remap = false))
    private String utDisplayTitle(String oldTitle)
    {
        return UTConfigTweaks.MISC.GAME_WINDOW.utGameWindowDisplayTitle;
    }
}
