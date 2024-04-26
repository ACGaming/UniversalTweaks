package mod.acgaming.universaltweaks.bugfixes.entities.sleeping.mixin;

import net.minecraft.profiler.Profiler;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import mod.acgaming.universaltweaks.config.UTConfigBugfixes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// MC-63340
// https://bugs.mojang.com/browse/MC-63340
// Courtesy of fonnymunkey
@Mixin(WorldServer.class)
public abstract class UTSleepResetsWeatherMixin extends World
{
    protected UTSleepResetsWeatherMixin(ISaveHandler saveHandlerIn, WorldInfo info, WorldProvider providerIn, Profiler profilerIn, boolean client)
    {
        super(saveHandlerIn, info, providerIn, profilerIn, client);
    }

    @WrapWithCondition(method = "wakeAllPlayers", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldServer;resetRainAndThunder()V"))
    public boolean utWakeAllPlayers(WorldServer worldServer)
    {
        return !UTConfigBugfixes.BLOCKS.utSleepResetsWeatherToggle || this.isRaining() || this.isThundering();
    }
}