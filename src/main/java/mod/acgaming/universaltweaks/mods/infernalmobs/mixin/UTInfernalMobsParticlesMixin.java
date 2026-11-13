package mod.acgaming.universaltweaks.mods.infernalmobs.mixin;

import atomicstryker.infernalmobs.client.RendererBossGlow;

import mod.acgaming.universaltweaks.config.UTConfigMods;

import net.minecraft.client.Minecraft;

import net.minecraftforge.client.event.RenderWorldLastEvent;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RendererBossGlow.class)
public class UTInfernalMobsParticlesMixin
{
    @Inject(method = "onRenderWorldLast", at = @At("HEAD"), cancellable = true, remap = false)
    public void utOnRenderWorldLast(RenderWorldLastEvent event, CallbackInfo ci)
    {
        if (UTConfigMods.INFERNAL_MOBS.utIMParticlePauseFix && Minecraft.getMinecraft().isGamePaused()) ci.cancel();
    }
}