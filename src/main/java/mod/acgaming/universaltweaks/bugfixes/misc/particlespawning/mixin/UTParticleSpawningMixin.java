package mod.acgaming.universaltweaks.bugfixes.misc.particlespawning.mixin;

import java.util.Objects;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.ServerWorldEventHandler;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// MC-10369, MC-93826
// https://bugs.mojang.com/browse/MC-10369
// https://bugs.mojang.com/browse/MC-93826
// Courtesy of Fuzs, TheRandomLabs, fonnymunkey
@Mixin(ServerWorldEventHandler.class)
public abstract class UTParticleSpawningMixin
{
    /**
     * Fixes particles not properly being spawned on the client
     * Based on a patch by RandomPatches
     * https://github.com/TheRandomLabs/RandomPatches/blob/1.12/src/main/java/com/therandomlabs/randompatches/patch/ServerWorldEventHandlerPatch.java
     */
    @Inject(method = "spawnParticle(IZDDDDDD[I)V", at = @At("HEAD"))
    public void utSpawnParticle(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int[] parameters, CallbackInfo ci)
    {
        EnumParticleTypes particle = Objects.requireNonNull(EnumParticleTypes.getParticleFromId(particleID));
        if (particle == EnumParticleTypes.SPELL_MOB || particle == EnumParticleTypes.SPELL_MOB_AMBIENT || !UTConfigBugfixes.MISC.utParticleSpawningToggle) return;
        if (parameters.length == particle.getArgumentCount()) ((UTServerWorldEventHandlerAccessor) this).getWorld().spawnParticle(particle, xCoord, yCoord, zCoord, 0, xSpeed, ySpeed, zSpeed, 1.0, parameters);
    }
}