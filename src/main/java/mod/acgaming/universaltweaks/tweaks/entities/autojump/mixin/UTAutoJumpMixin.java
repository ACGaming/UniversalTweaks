package mod.acgaming.universaltweaks.tweaks.entities.autojump.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of Aroma1997
@Mixin(EntityPlayerSP.class)
public class UTAutoJumpMixin
{
    @Unique
    private boolean ut$stepAssisted;

    @Inject(method = "isAutoJumpEnabled", at = @At("RETURN"), cancellable = true)
    public void utAutoJump(CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfigTweaks.ENTITIES.utAutoJumpToggle || !Minecraft.getMinecraft().isSingleplayer()) return;
        if (Minecraft.getMinecraft().gameSettings.autoJump && Minecraft.getMinecraft().player.stepHeight < 1.0f)
        {
            ut$stepAssisted = true;
            if (!Minecraft.getMinecraft().player.isSneaking()) Minecraft.getMinecraft().player.stepHeight = 1.25f;
        }
        else if (ut$stepAssisted)
        {
            ut$stepAssisted = false;
            Minecraft.getMinecraft().player.stepHeight = Math.max(Minecraft.getMinecraft().player.stepHeight - 0.65f, 0.6f);
        }
        cir.setReturnValue(false);
    }
}