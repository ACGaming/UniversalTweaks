package mod.acgaming.universaltweaks.tweaks.blocks.barrier.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.GameType;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import mod.acgaming.universaltweaks.config.UTConfigTweaks;

// Courtesy of WaitingIdly
@Mixin(WorldClient.class)
public class UTBarrierParticleMixin
{
    @Shadow
    @Final
    private Minecraft mc;

    @ModifyVariable(method = "doVoidFogParticles", at = @At(value = "STORE", ordinal = 0))
    private boolean utAlwaysDisplayBarrier(boolean original)
    {
        if (!UTConfigTweaks.BLOCKS.utBarrierParticleDisplay) return original;
        return this.mc.playerController.getCurrentGameType() == GameType.CREATIVE;
    }
}