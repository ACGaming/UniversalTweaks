package mod.acgaming.universaltweaks.tweaks.entities.autojump.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of jchung01
@Mixin(EntityPlayerSP.class)
public class UTAutoJumpMixin
{
    @Unique
    private float ut$stepHeightOverride;
    @Unique
    private boolean ut$stepAssisted;

    @Inject(method = "isAutoJumpEnabled", at = @At("RETURN"), cancellable = true)
    public void utAutoJump(CallbackInfoReturnable<Boolean> cir)
    {
        if (!UTConfigTweaks.ENTITIES.utAutoJumpToggle || !Minecraft.getMinecraft().isSingleplayer()) return;
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        // Magic numbers to identify as this tweak's step heights.
        final float assistedStepHeight = Float.intBitsToFloat(0b00111111101000000000001000000000); // About 1.25
        final float baseStepHeight = Float.intBitsToFloat(0b00111111000110011001101001000011); // About 0.6
        if (Minecraft.getMinecraft().gameSettings.autoJump)
        {
            if (!player.isSneaking() && player.onGround)
            {
                // Capture changed step height.
                if (player.stepHeight >= 1.0f && player.stepHeight != assistedStepHeight && ut$stepHeightOverride == 0.0f)
                {
                    if (ut$stepAssisted) player.stepHeight -= 0.65f;
                    ut$stepHeightOverride = player.stepHeight;
                }
                else if (player.stepHeight < 1.0f)
                {
                    ut$stepHeightOverride = 0.0f;
                }
                player.stepHeight = ut$stepHeightOverride > 0.0f ? ut$stepHeightOverride : assistedStepHeight;
                ut$stepAssisted = true;
            }
            else if (player.isSneaking())
            {
                player.stepHeight = baseStepHeight;
            }
        }
        else if (ut$stepAssisted)
        {
            player.stepHeight = ut$stepHeightOverride > 0.0f ? ut$stepHeightOverride : baseStepHeight;
            ut$stepAssisted = false;
        }
        cir.setReturnValue(false);
    }
}