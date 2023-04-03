package mod.acgaming.universaltweaks.tweaks.entities.autojump.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

import mod.acgaming.universaltweaks.UniversalTweaks;
import mod.acgaming.universaltweaks.config.UTConfig;
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
        if (!UTConfig.TWEAKS_ENTITIES.utAutoJumpToggle) return;
        if (UTConfig.DEBUG.utDebugToggle) UniversalTweaks.LOGGER.debug("UTAutoJump ::: Auto jump check");
        if (Minecraft.getMinecraft().gameSettings.autoJump && !Minecraft.getMinecraft().player.isSneaking()) Minecraft.getMinecraft().player.stepHeight = 1.2f;
        else Minecraft.getMinecraft().player.stepHeight = 0.6f;
        cir.setReturnValue(false);
    }
}