package mod.acgaming.universaltweaks.bugfixes.blocks.bed.mixin;

import net.minecraft.world.WorldServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.llamalad7.mixinextras.injector.WrapWithCondition;

import mod.acgaming.universaltweaks.config.UTConfigBugfixes;

// MC-63340
// https://bugs.mojang.com/browse/MC-63340
// Courtesy of fonnymunkey
@Mixin(WorldServer.class)
public abstract class UTSleepResetsWeatherMixin
{
    @WrapWithCondition(method = "wakeAllPlayers", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldServer;resetRainAndThunder()V"))
    public boolean utWakeAllPlayers(WorldServer instance)
    {
        return !UTConfigBugfixes.BLOCKS.utSleepResetsWeatherToggle;
    }
}