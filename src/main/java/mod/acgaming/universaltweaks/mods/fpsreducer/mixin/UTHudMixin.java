package mod.acgaming.universaltweaks.mods.fpsreducer.mixin;

import net.minecraft.client.Minecraft;

import bre.fpsreducer.gui.Hud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Hud.class, remap = false)
public class UTHudMixin
{
    @Shadow
    private int curFPS;

    @Inject(method = "setFPSCPUData", at = @At("TAIL"))
    private void utCorrectFpsDisplay(int fps, double cpuload, CallbackInfo ci)
    {
        this.curFPS = Minecraft.getDebugFPS();
    }
}