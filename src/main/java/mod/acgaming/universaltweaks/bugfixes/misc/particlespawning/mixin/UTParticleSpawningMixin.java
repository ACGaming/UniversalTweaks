package mod.acgaming.universaltweaks.bugfixes.misc.particlespawning.mixin;

import java.util.Objects;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.ServerWorldEventHandler;
import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

// MC-10369, MC-93826
// https://bugs.mojang.com/browse/MC-10369
// https://bugs.mojang.com/browse/MC-93826
// Courtesy of Fuzs, TheRandomLabs, fonnymunkey
@Mixin(ServerWorldEventHandler.class)
public abstract class UTParticleSpawningMixin
{
    @Final
    @Shadow
    private WorldServer world;

    /**
     * @author IcarussOne
     * @reason Fixes particles not properly being spawned on the client
     * Based on a patch by RandomPatches
     * https://github.com/TheRandomLabs/RandomPatches/blob/1.12/src/main/java/com/therandomlabs/randompatches/patch/ServerWorldEventHandlerPatch.java
     */
    @SuppressWarnings("OverwriteModifiers")
    @Overwrite
    public void spawnParticle(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters)
    {
        EnumParticleTypes particle = Objects.requireNonNull(EnumParticleTypes.getParticleFromId(particleID));
        if (particle == EnumParticleTypes.SPELL_MOB || particle == EnumParticleTypes.SPELL_MOB_AMBIENT || !UTConfigBugfixes.MISC.utParticleSpawningToggle) return;
        if (parameters.length == particle.getArgumentCount()) world.spawnParticle(particle, xCoord, yCoord, zCoord, 0, xSpeed, ySpeed, zSpeed, 1.0, parameters);
    }
}