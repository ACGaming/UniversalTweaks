package mod.acgaming.universaltweaks.tweaks.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// Courtesy of Aroma1997
@Mixin(EntityPlayerSP.class)
public class UTAutoJumpMixin
{
    @Inject(method = "isAutoJumpEnabled", at = @At("RETURN"), cancellable = true)
    public void utAutoJump(CallbackInfoReturnable<Boolean> cir)
    {
        if (Minecraft.getMinecraft().gameSettings.autoJump && !Minecraft.getMinecraft().player.isSneaking()) Minecraft.getMinecraft().player.stepHeight = 1.2f;
        else Minecraft.getMinecraft().player.stepHeight = 0.6f;
        cir.setReturnValue(false);
    }
}